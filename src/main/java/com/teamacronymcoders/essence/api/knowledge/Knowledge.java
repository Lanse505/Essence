package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Knowledge extends ForgeRegistryEntry<Knowledge> implements INBTSerializable<CompoundNBT> {
  private static final String TAG_KNOWLEDGE = "Knowledge";
  private static final String TAG_MODIFIER_INSTANCES = "ModifierInstances";

  private List<ModifierInstance> modifierInstances;

  public Knowledge() {
    this.modifierInstances = new ArrayList<>();
  }

  public Knowledge(ModifierInstance... modifiers) {
    this.modifierInstances = new ArrayList<>();
    Collections.addAll(this.modifierInstances, modifiers);
  }

  public String getTranslationString() {
    return "knowledge." + getRegistryName().getNamespace() + "." + getRegistryName().getPath();
  }

  @Override
  public CompoundNBT serializeNBT() {
    final CompoundNBT nbt = new CompoundNBT();
    nbt.putString(TAG_KNOWLEDGE, getRegistryName().toString());
    final ListNBT modifierInstances = new ListNBT();
    for (ModifierInstance instance : this.modifierInstances) {
      modifierInstances.add(instance.serializeNBT());
    }
    nbt.put(TAG_MODIFIER_INSTANCES, modifierInstances);
    return nbt;
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    final List<ModifierInstance> modifierInstances = new ArrayList<>();
    final ListNBT listNBT = nbt.getList(TAG_MODIFIER_INSTANCES, Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < listNBT.size(); i++) {
      final CompoundNBT subCNBT = listNBT.getCompound(i);
      final ModifierInstance instance = new ModifierInstance();
      instance.deserializeNBT(subCNBT);
      modifierInstances.add(instance);
    }
    this.modifierInstances = modifierInstances;
  }

}
