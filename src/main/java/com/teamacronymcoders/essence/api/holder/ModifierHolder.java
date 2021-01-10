package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class ModifierHolder<T> implements IModifierHolder<T>, INBTSerializable<ListNBT> {

  private final List<ModifierInstance<T>> modifiers;
  private final Class<T> type;

  public ModifierHolder (Class<T> type) {
    modifiers = new ArrayList<>();
    this.type = type;
  }

  @Override
  public boolean addModifierInstance (boolean simulate, T object, ModifierInstance<T>... instances) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = modifiers;
      for (ModifierInstance<T> instance : instances) {
        if (instance.getModifier().getType() == type) {
          if (!sim.contains(instance)) {
            sim.add(instance);
          }
        }
      }
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      for (ModifierInstance<T> instance : instances) {
        if (instance.getModifier().getType() == type) {
          if (!modifiers.contains(instance)) {
            modifiers.add(instance);
            modified.decreaseFreeModifiers(instance.getModifier().getModifierCountValue(instance.getLevel(), object));
          }
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean removeModifierInstance (boolean simulate, T object, ModifierInstance<T>... instances) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      Arrays.stream(instances).forEach(sim::remove);
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      int cmc = Arrays.stream(instances).map(instance -> modifiers.stream()
              .filter(trueInstance -> trueInstance.getModifier().equals(instance.getModifier()) && trueInstance.getModifierData() == instance.getModifierData())
              .map(trueInstance -> {
                if (instance.getModifier().getType() == type) {
                  modifiers.remove(trueInstance);
                  return trueInstance.getModifier().getModifierCountValue(trueInstance.getLevel(), object);
                }
                return 0;
              }).reduce(0, Integer::sum)).reduce(0, Integer::sum);
      modified.addModifierWithoutIncreasingAdditional(cmc);
      return true;
    }
    return false;
  }

  @Override
  public boolean levelUpModifier (boolean simulate, T object, int increase, Modifier<T>... modifiers) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      sim.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  Modifier<T> stackCoreModifier = instance.getModifier();
                  instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                }
              });
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      this.modifiers.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  Modifier<T> stackCoreModifier = instance.getModifier();
                  int x = stackCoreModifier.getModifierCountValue(instance.getLevel(), object);
                  int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)), object);
                  instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                  if (x < y) {
                    modified.decreaseFreeModifiers(y - x);
                  }
                }
              });
      return true;
    }
    return false;
  }

  @Override
  public boolean levelUpModifier (boolean simulate, T object, int increase, ModifierInstance<T>... modifiersWithData) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      sim.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  Modifier<T> stackCoreModifier = instance.getModifier();
                  instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                }
              });
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      this.modifiers.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  Modifier<T> stackCoreModifier = instance.getModifier();
                  int x = stackCoreModifier.getModifierCountValue(instance.getLevel(), object);
                  int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)), object);
                  instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                  if (x < y) {
                    modified.decreaseFreeModifiers(y - x);
                  }
                }
              });
      return true;
    }
    return false;
  }

  @Override
  public boolean levelDownModifier (boolean simulate, T object, int decrease, Modifier<T>... modifiers) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      sim.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  int level = instance.getLevel();
                  if (level - decrease < instance.getModifier().getMinLevel(object)) {
                    sim.remove(instance);
                    return;
                  }
                  instance.setLevel(instance.getLevel() - decrease);
                }
              });
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      this.modifiers.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> modifier == instance.getModifier()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  int level = instance.getLevel();
                  if (level - decrease < instance.getModifier().getMinLevel(object)) {
                    removeModifierInstance(false, object, instance);
                    return;
                  }
                  int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease, object);
                  instance.setLevel(instance.getLevel() - decrease);
                  if (x > 0) {
                    modified.addModifierWithoutIncreasingAdditional(x);
                  }
                }
              });
      return true;
    }
    return false;
  }

  @Override
  public boolean levelDownModifier (boolean simulate, T object, int decrease, ModifierInstance<T>... modifiersWithData) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      sim.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  int level = instance.getLevel();
                  if (level - decrease < instance.getModifier().getMinLevel(object)) {
                    sim.remove(instance);
                    return;
                  }
                  instance.setLevel(instance.getLevel() - decrease);
                }
              });
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      this.modifiers.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  int level = instance.getLevel();
                  if (level - decrease < instance.getModifier().getMinLevel(object)) {
                    removeModifierInstance(false, object, instance);
                    return;
                  }
                  int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease, object);
                  instance.setLevel(instance.getLevel() - decrease);
                  if (x > 0) {
                    modified.addModifierWithoutIncreasingAdditional(x);
                  }
                }
              });
      return true;
    }
    return false;
  }

  @Override
  public boolean levelSetModifier (boolean simulate, T object, int level, Modifier<T>... modifiers) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      sim.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  instance.setLevel(level);
                }
              });
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      this.modifiers.stream()
              .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(level, object);
                  instance.setLevel(level);
                  if (x > 0) {
                    modified.addModifierWithoutIncreasingAdditional(x);
                  }
                  if (x < 0) {
                    modified.decreaseFreeModifiers(x);
                  }
                }
              });
      return true;
    }
    return false;
  }

  @Override
  public boolean levelSetModifier (boolean simulate, T object, int level, ModifierInstance<T>... modifiersWithData) {
    if (simulate && object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      List<ModifierInstance<T>> sim = this.modifiers;
      sim.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  instance.setLevel(level);
                }
              });
      return modified.recheck(object, sim);
    }
    if (object instanceof IModified && ((IModified) object).getType() == type) {
      IModified<T> modified = (IModified<T>) object;
      this.modifiers.stream()
              .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
              .forEach(instance -> {
                if (instance.getModifier().getType() == type) {
                  int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(level, object);
                  instance.setLevel(level);
                  if (x > 0) {
                    modified.addModifierWithoutIncreasingAdditional(x);
                  }
                  if (x < 0) {
                    modified.decreaseFreeModifiers(x);
                  }
                }
              });
      return true;
    }
    return false;
  }

  @Nonnull
  @Override
  public List<ModifierInstance<T>> getModifierInstances () {
    return modifiers;
  }

  @Override
  public void clearModifiers () {
    modifiers.clear();
  }

  public Class<T> getType () {
    return type;
  }

  @Override
  public ListNBT serializeNBT () {
    final ListNBT listNBT = new ListNBT();
    for (ModifierInstance<T> instance : modifiers) {
      listNBT.add(instance.serializeNBT());
    }
    return listNBT;
  }

  @Override
  public void deserializeNBT (ListNBT nbt) {
    for (int i = 0; i < nbt.size(); i++) {
      final CompoundNBT compoundNBT = nbt.getCompound(i);
      final ModifierInstance<T> instance = new ModifierInstance<>(type);
      instance.deserializeNBT(compoundNBT);
      if (this.modifiers.stream().noneMatch(tracked -> tracked == instance)) {
        this.modifiers.add(instance);
      }
    }
  }
}
