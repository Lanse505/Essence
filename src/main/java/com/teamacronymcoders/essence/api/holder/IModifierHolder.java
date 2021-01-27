package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IModifierHolder<T> extends INBTSerializable<ListNBT> {

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
