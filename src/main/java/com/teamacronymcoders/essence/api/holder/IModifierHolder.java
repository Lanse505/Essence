package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModifierHolder<T> extends INBTSerializable<ListNBT> {

    boolean addModifierInstance(boolean simulate, T object, ModifierInstance<T>... instance);

    boolean removeModifierInstance(boolean simulate, T object, ModifierInstance<T>... instance);

    boolean levelUpModifier(boolean simulate, T object, int increase, Modifier<T>... modifiers);

    boolean levelUpModifier(boolean simulate, T object, int increase, ModifierInstance<T>... modifiersWithData);

    boolean levelDownModifier(boolean simulate, T object, int decrease, Modifier<T>... modifiers);

    boolean levelDownModifier(boolean simulate, T object, int decrease, ModifierInstance<T>... modifiersWithData);

    boolean levelSetModifier(boolean simulate, T object, int level, Modifier<T>... modifiers);

    boolean levelSetModifier(boolean simulate, T object, int level, ModifierInstance<T>... modifiersWithData);

    @Nonnull
    List<ModifierInstance<T>> getModifierInstances();

    void clearModifiers();
}
