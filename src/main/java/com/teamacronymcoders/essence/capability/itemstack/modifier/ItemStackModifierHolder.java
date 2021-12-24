package com.teamacronymcoders.essence.capability.itemstack.modifier;

import com.google.common.collect.Lists;
import com.teamacronymcoders.essence.api.holder.ModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemStackModifierHolder extends ModifierHolder<ItemStack> {

  private ItemStack stack;

  public ItemStackModifierHolder() {
    super();
  }

  public ItemStackModifierHolder(ItemStack stack) {
    super();
    this.stack = stack;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean addModifierInstance(boolean simulate, ItemStack object, ModifierInstance... instances) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      for (ModifierInstance instance : instances) {
        if (!sim.contains(instance)) {
          sim.add(instance);
        }
      }
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      for (ModifierInstance instance : instances) {
        if (!getModifierInstances().contains(instance)) {
          getModifierInstances().add(instance);
          modified.decreaseFreeModifiers(instance.getModifier().getModifierCountValue(instance.getLevel()));
        }
      }
      return true;
    }
    return false;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean removeModifierInstance(boolean simulate, ItemStack object, Modifier... modifiers) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = Lists.newArrayList(getModifierInstances());
      Arrays.stream(modifiers).forEach(modifier -> sim.stream().filter(instance -> instance.getModifier() == modifier).forEach(sim::remove));
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      if (modifiers != null && modifiers.length > 0) {
        int cmc = Arrays.stream(modifiers).map(modifier -> {
          if (modifier != null) {
            return this.getModifierInstances().stream().filter(instance -> instance != null && instance.getModifier() == modifier)
                    .map(instance -> {
                      int v = instance.getModifier().getModifierCountValue(instance.getLevel());
                      this.getModifierInstances().remove(instance);
                      return v;
                    }).reduce(0, Integer::sum);
          }
          return 0;
        }).reduce(0, Integer::sum);
        modified.addModifierWithoutIncreasingAdditional(cmc);
        return true;
      }
      return false;
    }
    return false;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean levelUpModifier(boolean simulate, int increase, ItemStack object, Modifier... modifiers) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      sim.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                Modifier stackCoreModifier = instance.getModifier();
                instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
              });
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      getModifierInstances().stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                Modifier stackCoreModifier = instance.getModifier();
                int x = stackCoreModifier.getModifierCountValue(instance.getLevel());
                int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                if (x < y) {
                  modified.decreaseFreeModifiers(y - x);
                }
              });
      return true;
    }
    return false;
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  public boolean levelUpModifier(boolean simulate, int increase, ItemStack object, ModifierInstance... modifiersWithData) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      sim.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                Modifier stackCoreModifier = instance.getModifier();
                instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
              });
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      getModifierInstances().stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                Modifier stackCoreModifier = instance.getModifier();
                int x = stackCoreModifier.getModifierCountValue(instance.getLevel());
                int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                if (x < y) {
                  modified.decreaseFreeModifiers(y - x);
                }
              });
      return true;
    }
    return false;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean levelDownModifier(boolean simulate, int decrease, ItemStack object, Modifier... modifiers) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      sim.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel()) {
                  sim.remove(instance);
                  return;
                }
                instance.setLevel(instance.getLevel() - decrease);
              });
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      getModifierInstances().stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> modifier == instance.getModifier()))
              .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel()) {
                  removeModifierInstance(false, object, instance.getModifier());
                  return;
                }
                int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease);
                instance.setLevel(instance.getLevel() - decrease);
                if (x > 0) {
                  modified.addModifierWithoutIncreasingAdditional(x);
                }
              });
      return true;
    }
    return false;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean levelDownModifier(boolean simulate, int decrease, ItemStack object, ModifierInstance... modifiersWithData) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      sim.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel()) {
                  sim.remove(instance);
                  return;
                }
                instance.setLevel(instance.getLevel() - decrease);
              });
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      getModifierInstances().stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel()) {
                  removeModifierInstance(false, object, instance.getModifier());
                  return;
                }
                int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease);
                instance.setLevel(instance.getLevel() - decrease);
                if (x > 0) {
                  modified.addModifierWithoutIncreasingAdditional(x);
                }
              });
      return true;
    }
    return false;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean levelSetModifier(boolean simulate, int level, ItemStack object, Modifier... modifiers) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      sim.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                instance.setLevel(level);
              });
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      getModifierInstances().stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(level);
                instance.setLevel(level);
                if (x > 0) {
                  modified.addModifierWithoutIncreasingAdditional(x);
                }
                if (x < 0) {
                  modified.decreaseFreeModifiers(x);
                }
              });
      return true;
    }
    return false;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public boolean levelSetModifier(boolean simulate, int level, ItemStack object, ModifierInstance... modifiersWithData) {
    if (simulate && object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      List<ModifierInstance> sim = getModifierInstances();
      sim.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                instance.setLevel(level);
              });
      return modified.recheck(sim);
    }
    if (object.getItem() instanceof IModified) {
      IModified modified = (IModified) object.getItem();
      getModifierInstances().stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(level);
                instance.setLevel(level);
                if (x > 0) {
                  modified.addModifierWithoutIncreasingAdditional(x);
                }
                if (x < 0) {
                  modified.decreaseFreeModifiers(x);
                }
              });
      return true;
    }
    return false;
  }

  @Override
  public ListTag serializeNBT() {
    final ListTag listNBT = new ListTag();
    for (ModifierInstance instance : getModifierInstances()) {
      listNBT.add(instance.serializeNBT());
    }
    stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, listNBT);
    return listNBT;
  }

  @Override
  public void deserializeNBT(ListTag nbt) {
    super.deserializeNBT(nbt.size() > 0 ? nbt : stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND));
  }
}
