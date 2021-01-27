package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ModifierInstance implements INBTSerializable<CompoundNBT> {
  public static final String TAG_MODIFIER = "Modifier";
  public static final String TAG_INFO = "ModifierInfo";
  public static final String TAG_LEVEL = "ModifierLevel";
  public static final String TAG_COMPOUND = "ModifierCompound";

  private Supplier<Modifier> modifier;
  private int level;
  private CompoundNBT modifierData;

  public ModifierInstance() {}

  public ModifierInstance(Supplier<Modifier> modifier, int level, CompoundNBT modifierData) {
    this.modifier = modifier;
    this.level = level;
    this.modifierData = modifierData;
  }

  @Override
  public CompoundNBT serializeNBT() {
    final CompoundNBT compoundNBT = new CompoundNBT();
    compoundNBT.putString(TAG_MODIFIER, modifier.get().getRegistryName().toString());
    final CompoundNBT info = new CompoundNBT();
    info.putInt(TAG_LEVEL, level);
    if (modifierData == null) {
      info.put(TAG_COMPOUND, new CompoundNBT());
    } else {
      info.put(TAG_COMPOUND, modifierData);
    }
    compoundNBT.put(TAG_INFO, info);
    return compoundNBT;
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    final Modifier modifier = EssenceItemstackModifierHelpers.getModifierByRegistryName(nbt.getString(TAG_MODIFIER));
    final CompoundNBT info = nbt.getCompound(TAG_INFO);
    final int level = info.getInt(TAG_LEVEL);
    final CompoundNBT modifierData = info.getCompound(TAG_COMPOUND);

    this.modifier = () -> modifier;
    this.level = level;
    this.modifierData = modifierData;
    this.modifier.get().update(this.modifierData);
  }

  public Modifier getModifier() {
    return modifier.get();
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public CompoundNBT getModifierData() {
    return modifierData;
  }

  public void setModifierData(CompoundNBT modifierData) {
    this.modifierData = modifierData;
  }
}
