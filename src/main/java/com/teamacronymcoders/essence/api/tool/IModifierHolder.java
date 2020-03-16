package com.teamacronymcoders.essence.api.tool;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IModifierHolder extends INBTSerializable<ListNBT> {
    void addModifierInstance(ModifierInstance... instance);
    void removeModifierInstance(ModifierInstance... instance);
    void levelUpModifier(ItemStack stack, int increase, Modifier... modifiers);
    void levelUpModifier(ItemStack stack, int increase, Pair<Modifier, CompoundNBT>... modifiersWithData);
    void levelDownModifier(ItemStack stack, int decrease, Modifier... modifiers);
    void levelDownModifier(ItemStack stack, int decrease, Pair<Modifier, CompoundNBT>... modifiersWithData);
    List<ModifierInstance> getModifierInstances();
    void clearModifiers();
}
