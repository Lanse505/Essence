package com.teamacronymcoders.essence.api.tool.modifierholder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModifierHolder extends INBTSerializable<CompoundNBT> {

    boolean addModifierInstance(boolean simulate, ItemStack stack, ModifierInstance... instance);
    boolean removeModifierInstance(boolean simulate, ItemStack stack, ModifierInstance... instance);
    boolean levelUpModifier(boolean simulate, ItemStack stack, int increase, Modifier... modifiers);
    boolean levelUpModifier(boolean simulate, ItemStack stack, int increase, ModifierInstance... modifiersWithData);
    boolean levelDownModifier(boolean simulate, ItemStack stack, int decrease, Modifier... modifiers);
    boolean levelDownModifier(boolean simulate, ItemStack stack, int decrease, ModifierInstance... modifiersWithData);
    boolean levelSetModifier(boolean simulate, ItemStack stack, int level, Modifier... modifiers);
    boolean levelSetModifier(boolean simulate, ItemStack stack, int level, ModifierInstance... modifiersWithData);

    void increaseFreeModifiers(int increase);
    boolean decreaseFreeModifiers(int decrease);
    int getFreeModifiers();

    @Nonnull
    List<ModifierInstance> getModifierInstances();

    void clearModifiers();
}
