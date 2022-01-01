package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModifierHolder<T> extends INBTSerializable<ListTag> {

    boolean addModifierInstance(boolean simulate, T object, ModifierInstance... instance);

    boolean removeModifierInstance(boolean simulate, T object, Modifier... modifiers);

    boolean levelUpModifier(boolean simulate, int increase, T object, Modifier... modifiers);

    boolean levelUpModifier(boolean simulate, int increase, T object, ModifierInstance... modifiersWithData);

    boolean levelDownModifier(boolean simulate, int decrease, T object, Modifier... modifiers);

    boolean levelDownModifier(boolean simulate, int decrease, T object, ModifierInstance... modifiersWithData);

    boolean levelSetModifier(boolean simulate, int level, T object, Modifier... modifiers);

    boolean levelSetModifier(boolean simulate, int level, T object, ModifierInstance... modifiersWithData);

    @Nonnull
    List<ModifierInstance> getModifierInstances();

    void clearModifiers();
}
