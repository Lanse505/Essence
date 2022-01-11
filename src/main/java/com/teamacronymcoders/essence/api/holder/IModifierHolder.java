package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModifierHolder<T> extends INBTSerializable<CompoundTag> {

    boolean addModifierInstance(boolean simulate, T object, ModifierInstance... instance);

    boolean removeModifierInstance(boolean simulate, T object, IModifier<T>... modifiers);

    boolean levelUpModifier(boolean simulate, int increase, T object, IModifier<T>... modifiers);

    boolean levelUpModifier(boolean simulate, int increase, T object, ModifierInstance... modifiersWithData);

    boolean levelDownModifier(boolean simulate, int decrease, T object, IModifier<T>... modifiers);

    boolean levelDownModifier(boolean simulate, int decrease, T object, ModifierInstance... modifiersWithData);

    boolean levelSetModifier(boolean simulate, int level, T object, IModifier<T>... modifiers);

    boolean levelSetModifier(boolean simulate, int level, T object, ModifierInstance... modifiersWithData);

    @Nonnull
    List<ModifierInstance> getModifierInstances();

    void clearModifiers();
}
