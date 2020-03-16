package com.teamacronymcoders.essence.api.tool;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifierHolder implements IModifierHolder, INBTSerializable<ListNBT> {

    private List<ModifierInstance> modifiers;

    public ModifierHolder() {
        this.modifiers = new ArrayList<>();
    }

    @Override
    public void addModifierInstance(ModifierInstance... instances) {
        for (ModifierInstance instance : instances) {
            if (!this.modifiers.contains(instance)) {
                this.modifiers.add(instance);
            }
        }
    }

    @Override
    public void removeModifierInstance(ModifierInstance... instances) {
        for (ModifierInstance instance : instances) {
            modifiers.remove(instance);
        }
    }

    @Override
    public void levelUpModifier(ItemStack stack, int increase, Modifier... modifiers) {
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
            .forEach(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
    }

    @SafeVarargs
    @Override
    public final void levelUpModifier(ItemStack stack, int increase, Pair<Modifier, CompoundNBT>... modifiersWithData) {
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getKey() && instance.getModifierData() == modifier.getValue()))
            .forEach(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
    }

    @Override
    public void levelDownModifier(ItemStack stack, int decrease, Modifier... modifiers) {
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
            .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel(stack)) {
                    this.modifiers.remove(instance);
                    return;
                }
                instance.setLevel(instance.getLevel() - decrease);
            });
    }

    @SafeVarargs
    @Override
    public final void levelDownModifier(ItemStack stack, int decrease, Pair<Modifier, CompoundNBT>... modifiersWithData) {
        this.modifiers.stream()
            .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getKey() && instance.getModifierData() == modifier.getValue()))
            .forEach(instance -> {
                int level = instance.getLevel();
                if (level - decrease < instance.getModifier().getMinLevel(stack)) {
                    modifiers.remove(instance);
                    return;
                }
                instance.setLevel(instance.getLevel() - decrease);
            });
    }

    @Override
    public List<ModifierInstance> getModifierInstances() {
        return modifiers;
    }

    @Override
    public void clearModifiers() {
        modifiers.clear();
    }

    @Override
    public ListNBT serializeNBT() {
        final ListNBT listNBT = new ListNBT();
        for (ModifierInstance instance : modifiers) {
            listNBT.add(instance.serializeNBT());
        }
        return listNBT;
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        for (int i = 0; i < nbt.size(); i++) {
            final CompoundNBT compoundNBT = nbt.getCompound(i);
            final ModifierInstance instance = new ModifierInstance();
            instance.deserializeNBT(compoundNBT);
            if (this.modifiers.stream().noneMatch(tracked -> tracked == instance)) {
                this.modifiers.add(instance);
            }
        }
    }
}
