package com.teamacronymcoders.essence.api.holder;

import com.google.common.collect.Lists;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ModifierHolder<T> implements IModifierHolder<T>, INBTSerializable<ListTag> {

    private final List<ModifierInstance> modifiers;

    public ModifierHolder() {
        modifiers = new ArrayList<>();
    }

    @Override
    public boolean addModifierInstance(boolean simulate, T object, ModifierInstance... instances) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = modifiers;
            for (ModifierInstance instance : instances) {
                if (!sim.contains(instance)) {
                    sim.add(instance);
                }
            }
            return modified.recheck(sim);
        }
        if (object instanceof IModified modified) {
            for (ModifierInstance instance : instances) {
                if (!modifiers.contains(instance)) {
                    modifiers.add(instance);
                    modified.decreaseFreeModifiers(instance.getModifier().getModifierCountValue(instance.getLevel()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeModifierInstance(boolean simulate, T object, Modifier... modifiers) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = Lists.newArrayList(this.modifiers);
            Arrays.stream(modifiers).forEach(modifier -> sim.stream().filter(instance -> instance.getModifier() == modifier).forEach(sim::remove));
            return modified.recheck(sim);
        }
        if (object instanceof IModified modified) {
            int cmc = Arrays.stream(modifiers).map(modifier -> this.modifiers.stream().filter(instance -> instance.getModifier() == modifier)
                    .map(instance -> {
                        this.modifiers.remove(instance);
                        return instance.getModifier().getModifierCountValue(instance.getLevel());
                    }).reduce(0, Integer::sum)).reduce(0, Integer::sum);
            modified.addModifierWithoutIncreasingAdditional(cmc);
            return true;
        }
        return false;
    }

    @Override
    public boolean levelUpModifier(boolean simulate, int increase, T object, Modifier... modifiers) {
        if (simulate && object instanceof IModified) {
            IModified modified = (IModified) object;
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                    .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                    .forEach(instance -> {
                        Modifier stackCoreModifier = instance.getModifier();
                        instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                    });
            return modified.recheck(sim);
        }
        if (object instanceof IModified modified) {
            this.modifiers.stream()
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

    @Override
    public boolean levelUpModifier(boolean simulate, int increase, T object, ModifierInstance... modifiersWithData) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                    .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                    .forEach(instance -> {
                        Modifier stackCoreModifier = instance.getModifier();
                        instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel()));
                    });
            return modified.recheck(sim);
        }
        if (object instanceof IModified modified) {
            this.modifiers.stream()
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

    @Override
    public boolean levelDownModifier(boolean simulate, int decrease, T object, Modifier... modifiers) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = this.modifiers;
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
        if (object instanceof IModified modified) {
            this.modifiers.stream()
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

    @Override
    public boolean levelDownModifier(boolean simulate, int decrease, T object, ModifierInstance... modifiersWithData) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = this.modifiers;
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
        if (object instanceof IModified modified) {
            this.modifiers.stream()
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

    @Override
    public boolean levelSetModifier(boolean simulate, int level, T object, Modifier... modifiers) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                    .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                    .forEach(instance -> {
                        instance.setLevel(level);
                    });
            return modified.recheck(sim);
        }
        if (object instanceof IModified modified) {
            this.modifiers.stream()
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

    @Override
    public boolean levelSetModifier(boolean simulate, int level, T object, ModifierInstance... modifiersWithData) {
        if (simulate && object instanceof IModified modified) {
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                    .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                    .forEach(instance -> {
                        instance.setLevel(level);
                    });
            return modified.recheck(sim);
        }
        if (object instanceof IModified modified) {
            this.modifiers.stream()
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

    @Nonnull
    @Override
    public List<ModifierInstance> getModifierInstances() {
        return modifiers;
    }

    @Override
    public void clearModifiers() {
        modifiers.clear();
    }

    @Override
    public ListTag serializeNBT() {
        final ListTag listNBT = new ListTag();
        for (ModifierInstance instance : modifiers) {
            listNBT.add(instance.serializeNBT());
        }
        return listNBT;
    }

    @Override
    public void deserializeNBT(ListTag tags) {
        this.modifiers.clear();
        for (int i = 0; i < tags.size(); i++) {
            final CompoundTag compoundTag = tags.getCompound(i);
            final ModifierInstance instance = new ModifierInstance();
            instance.deserializeNBT(compoundTag);
            this.modifiers.add(instance);
        }
    }
}
