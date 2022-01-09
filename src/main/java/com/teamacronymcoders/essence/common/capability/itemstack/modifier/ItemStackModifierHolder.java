package com.teamacronymcoders.essence.common.capability.itemstack.modifier;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.holder.ModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemStackModifierHolder extends ModifierHolder<ItemStack> {

    private ItemStack stack;

    public ItemStackModifierHolder() {
        super();
    }

    public ItemStackModifierHolder(ItemStack stack) {
        super();
        this.stack = stack;
    }

    @Override
    public boolean addModifierInstance(boolean simulate, ItemStack object, ModifierInstance... instances) {
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : instances) {
                if (!sim.contains(instance)) {
                    sim.add(instance);
                }
            }
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
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

    @Override
    public boolean removeModifierInstance(boolean simulate, ItemStack object, Modifier... modifiers) {
        Set<Modifier> mods = Sets.newHashSet(modifiers);
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            sim.removeIf(instance -> mods.contains(instance.getModifier()));
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
            if (modifiers.length > 0) {
                List<ModifierInstance> instances = this.getModifierInstances();
                int[] value = new int[]{0};
                instances.removeIf(instance -> {
                    if (!mods.contains(instance.getModifier())) return false;
                    value[0] += instance.getModifier().getModifierCountValue(instance.getLevel());
                    return true;
                });
                modified.addModifierWithoutIncreasingAdditional(value[0]);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean levelUpModifier(boolean simulate, int increase, ItemStack object, Modifier... modifiers) {
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : sim) {
                boolean matched = false;
                for (Modifier modifier : modifiers) {
                    if (instance.getModifier() == modifier) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    Modifier stackCoreModifier = instance.getModifier();
                    instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                }
            }
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
            for (ModifierInstance instance : getModifierInstances()) {
                boolean matched = false;
                for (Modifier modifier : modifiers) {
                    if (instance.getModifier() == modifier) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    Modifier stackCoreModifier = instance.getModifier();
                    int x = stackCoreModifier.getModifierCountValue(instance.getLevel());
                    int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                    instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                    if (x < y) {
                        modified.decreaseFreeModifiers(y - x);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean levelUpModifier(boolean simulate, int increase, ItemStack object, ModifierInstance... modifiersWithData) {
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : sim) {
                boolean matched = false;
                for (ModifierInstance modifier : modifiersWithData) {
                    if (instance.getModifier().equals(modifier.getModifier()) && instance.getModifierData().equals(modifier.getModifierData())) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    Modifier stackCoreModifier = instance.getModifier();
                    instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                }
            }
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
            for (ModifierInstance instance : getModifierInstances()) {
                boolean matched = false;
                for (ModifierInstance modifier : modifiersWithData) {
                    if (instance.getModifier().equals(modifier.getModifier()) && instance.getModifierData().equals(modifier.getModifierData())) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    Modifier stackCoreModifier = instance.getModifier();
                    int x = stackCoreModifier.getModifierCountValue(instance.getLevel());
                    int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                    instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                    if (x < y) {
                        modified.decreaseFreeModifiers(y - x);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean levelDownModifier(boolean simulate, int decrease, ItemStack object, Modifier... modifiers) {
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : sim) {
                boolean matched = false;
                for (Modifier modifier : modifiers) {
                    if (instance.getModifier() == modifier) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    int level = instance.getLevel();
                    if (level - decrease < instance.getModifier().getMinLevel()) {
                        sim.remove(instance);
                        continue;
                    }
                    instance.setLevel(instance.getLevel() - decrease);
                }
            }
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
            for (ModifierInstance instance : getModifierInstances()) {
                boolean matched = false;
                for (Modifier modifier : modifiers) {
                    if (modifier == instance.getModifier()) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    int level = instance.getLevel();
                    if (level - decrease < instance.getModifier().getMinLevel()) {
                        removeModifierInstance(false, object, instance.getModifier());
                        continue;
                    }
                    int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease);
                    instance.setLevel(instance.getLevel() - decrease);
                    if (x > 0) {
                        modified.addModifierWithoutIncreasingAdditional(x);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean levelDownModifier(boolean simulate, int decrease, ItemStack object, ModifierInstance... modifiersWithData) {
        boolean result = false;
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : sim) {
                boolean matched = false;
                for (ModifierInstance modifier : modifiersWithData) {
                    if (instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    int level = instance.getLevel();
                    if (level - decrease < instance.getModifier().getMinLevel()) {
                        sim.remove(instance);
                        continue;
                    }
                    instance.setLevel(instance.getLevel() - decrease);
                }
            }
            result = modified.recheck(sim);
        } else if (object.getItem() instanceof IModified modified) {
            for (ModifierInstance instance : getModifierInstances()) {
                boolean matched = false;
                for (ModifierInstance modifier : modifiersWithData) {
                    if (instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    int level = instance.getLevel();
                    if (level - decrease < instance.getModifier().getMinLevel()) {
                        removeModifierInstance(false, object, instance.getModifier());
                        continue;
                    }
                    int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease);
                    instance.setLevel(instance.getLevel() - decrease);
                    if (x > 0) {
                        modified.addModifierWithoutIncreasingAdditional(x);
                    }
                }
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean levelSetModifier(boolean simulate, int level, ItemStack object, Modifier... modifiers) {
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : sim) {
                for (Modifier modifier : modifiers) {
                    if (instance.getModifier() == modifier) {
                        instance.setLevel(level);
                        break;
                    }
                }
            }
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
            for (ModifierInstance instance : getModifierInstances()) {
                boolean matched = false;
                for (Modifier modifier : modifiers) {
                    if (instance.getModifier() == modifier) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(level);
                    instance.setLevel(level);
                    if (x > 0) {
                        modified.addModifierWithoutIncreasingAdditional(x);
                    }
                    if (x < 0) {
                        modified.decreaseFreeModifiers(x);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean levelSetModifier(boolean simulate, int level, ItemStack object, ModifierInstance... modifiersWithData) {
        if (simulate && object.getItem() instanceof IModified modified) {
            List<ModifierInstance> sim = new ArrayList<>(getModifierInstances());
            for (ModifierInstance instance : sim) {
                for (ModifierInstance modifier : modifiersWithData) {
                    if (instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()) {
                        instance.setLevel(level);
                        break;
                    }
                }
            }
            return modified.recheck(sim);
        }
        if (object.getItem() instanceof IModified modified) {
            for (ModifierInstance instance : getModifierInstances()) {
                boolean matched = false;
                for (ModifierInstance modifier : modifiersWithData) {
                    if (instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()) {
                        matched = true;
                        break;
                    }
                }
                if (matched) {
                    int x = instance.getModifier().getModifierCountValue(instance.getLevel()) - instance.getModifier().getModifierCountValue(level);
                    instance.setLevel(level);
                    if (x > 0) {
                        modified.addModifierWithoutIncreasingAdditional(x);
                    }
                    if (x < 0) {
                        modified.decreaseFreeModifiers(x);
                    }
                }
            }
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
