package com.teamacronymcoders.essence.api.tool.modifierholder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.utils.tiers.IEssenceBaseTier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifierHolder implements IModifierHolder, INBTSerializable<CompoundNBT> {

    private List<ModifierInstance> modifiers;
    private final int baseModifiers;
    private int freeModifiers;
    private int additionalModifiers;

    public ModifierHolder() {
        modifiers = new ArrayList<>();
        baseModifiers = 0;
        freeModifiers = 0;
    }

    public ModifierHolder(IEssenceBaseTier tier) {
        modifiers = new ArrayList<>();
        baseModifiers = tier.getFreeModifiers();
        freeModifiers = tier.getFreeModifiers();
    }

    @Override
    public boolean addModifierInstance(boolean simulate, ItemStack stack, ModifierInstance... instances) {
        if (simulate) {
            List<ModifierInstance> sim = modifiers;
            for (ModifierInstance instance : instances) {
                if (!sim.contains(instance)) {
                    sim.add(instance);
                }
            }
            return recheck(stack, sim);
        }
        for (ModifierInstance instance : instances) {
            if (!modifiers.contains(instance)) {
                modifiers.add(instance);
                freeModifiers -= instance.getModifier().getModifierCountValue(stack, instance.getLevel());
            }
        }
        return true;
    }

    @Override
    public boolean removeModifierInstance(boolean simulate, ItemStack stack, ModifierInstance... instances) {
        int cmc = 0;
        for (ModifierInstance instance : instances) {
            modifiers.remove(instance);
            cmc += instance.getModifier().getModifierCountValue(stack, instance.getLevel());
        }
        addModifierWithoutIncreasingAdditional(cmc);
        return true;
    }

    @Override
    public boolean levelUpModifier(boolean simulate, ItemStack stack, int increase, Modifier... modifiers) {
        if (simulate) {
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
            return recheck(stack, sim);
        }
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
            .forEach(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
        return true;
    }

    @Override
    public boolean levelUpModifier(boolean simulate, ItemStack stack, int increase, ModifierInstance... modifiersWithData) {
        if (simulate) {
            List<ModifierInstance> sim = modifiers;
            sim.stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
            return recheck(stack, sim);
        }
        modifiers.stream()
            .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
            .forEach(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
        return true;
    }

    @Override
    public boolean levelDownModifier(boolean simulate, ItemStack stack, int decrease, Modifier... modifiers) {
        if (simulate) {
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> {
                    int level = instance.getLevel();
                    if (level - decrease < instance.getModifier().getMinLevel(stack)) {
                        removeModifierInstance(true, stack, instance);
                        return;
                    }
                    instance.setLevel(instance.getLevel() - decrease);
                });
            return recheck(stack, sim);
        }
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
            .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel(stack)) {
                    removeModifierInstance(false, stack, instance);
                    return;
                }
                int x = instance.getModifier().getModifierCountValue(stack, instance.getLevel()) - instance.getModifier().getModifierCountValue(stack, instance.getLevel() - decrease);
                instance.setLevel(instance.getLevel() - decrease);
                if (x > 0) {
                    addModifierWithoutIncreasingAdditional(x);
                }
            });
        return true;
    }

    @Override
    public final boolean levelDownModifier(boolean simulate, ItemStack stack, int decrease, ModifierInstance... modifiersWithData) {
        if (simulate) {
            List<ModifierInstance> sim = modifiers;
            sim.stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    int level = instance.getLevel();
                    if (level - decrease < instance.getModifier().getMinLevel(stack)) {
                        removeModifierInstance(true, stack, instance);
                        return;
                    }
                    instance.setLevel(instance.getLevel() - decrease);
                });
            return recheck(stack, sim);
        }
        modifiers.stream()
            .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
            .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel(stack)) {
                    removeModifierInstance(false, stack, instance);
                    return;
                }
                int x = instance.getModifier().getModifierCountValue(stack, instance.getLevel()) - instance.getModifier().getModifierCountValue(stack, instance.getLevel() - decrease);
                instance.setLevel(instance.getLevel() - decrease);
                if (x > 0) {
                    addModifierWithoutIncreasingAdditional(x);
                }
            });
        return true;
    }

    @Override
    public boolean levelSetModifier(boolean simulate, ItemStack stack, int level, Modifier... modifiers) {
        if (simulate) {
            List<ModifierInstance> sim = this.modifiers;
            sim.stream()
                .filter(instance ->  Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> instance.setLevel(level));
            return recheck(stack, sim);
        }
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
            .forEach(instance -> {
                int x = instance.getModifier().getModifierCountValue(stack, instance.getLevel()) - instance.getModifier().getModifierCountValue(stack, level);
                instance.setLevel(level);
                if (x > 0) {
                    addModifierWithoutIncreasingAdditional(x);
                }
                if (x < 0) {
                    decreaseFreeModifiers(x);
                }
            });
        return true;
    }

    @Override
    public boolean levelSetModifier(boolean simulate, ItemStack stack, int level, ModifierInstance... modifiersWithData) {
        if (simulate) {
            List<ModifierInstance> sim = modifiers;
            sim.stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> instance.setLevel(level));
            return recheck(stack, sim);
        }
        modifiers.stream()
            .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
            .forEach(instance -> {
                int x = instance.getModifier().getModifierCountValue(stack, instance.getLevel()) - instance.getModifier().getModifierCountValue(stack, level);
                instance.setLevel(level);
                if (x > 0) {
                    addModifierWithoutIncreasingAdditional(x);
                }
                if (x < 0) {
                    decreaseFreeModifiers(x);
                }
            });
        return true;
    }

    public boolean recheck(ItemStack stack, List<ModifierInstance> instances) {
        int cmc = 0;
        for (ModifierInstance instance : instances) {
            cmc += instance.getModifier().getModifierCountValue(stack, instance.getLevel());
        }
        return cmc <= baseModifiers + additionalModifiers;
    }

    public void addModifierWithoutIncreasingAdditional(int increase) {
        freeModifiers += increase;
    }

    @Override
    public void increaseFreeModifiers(int increase) {
        freeModifiers += increase;
        additionalModifiers += increase;
    }

    @Override
    public boolean decreaseFreeModifiers(int decrease) {
        if (freeModifiers - decrease < 0) {
            return false;
        }
        freeModifiers = freeModifiers - decrease;
        return true;
    }

    @Override
    public int getFreeModifiers() {
        return freeModifiers;
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
    public CompoundNBT serializeNBT() {
        final CompoundNBT nbt = new CompoundNBT();
        final ListNBT listNBT = new ListNBT();
        nbt.putInt("FreeModifiers", freeModifiers);
        nbt.putInt("AdditionalModifiers", additionalModifiers);
            for (ModifierInstance instance : modifiers) {
                listNBT.add(instance.serializeNBT());
            }
        nbt.put("ModifierInstances", listNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        final int freeModifiers = nbt.getInt("FreeModifiers");
        final int additionalModifiers = nbt.getInt("AdditionalModifiers");
        final ListNBT instances = nbt.getList("ModifierInstances", Constants.NBT.TAG_COMPOUND);
        this.freeModifiers = freeModifiers;
        this.additionalModifiers = additionalModifiers;
        for (int i = 0; i < nbt.size(); i++) {
            final CompoundNBT compoundNBT = instances.getCompound(i);
            final ModifierInstance instance = new ModifierInstance();
            instance.deserializeNBT(compoundNBT);
            if (this.modifiers.stream().noneMatch(tracked -> tracked == instance)) {
                this.modifiers.add(instance);
            }
        }
    }
}
