package com.teamacronymcoders.essence.api.recipe.infusion;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.CompoundNBT;

public class SerializableModifier {
  public Modifier modifier;
  public int level;
  public CompoundNBT modifierData;
  public InfusionOperation operation;

  public SerializableModifier(Modifier modifier, int level, CompoundNBT modifierData, InfusionOperation operation) {
    this.modifier = modifier;
    this.level = level;
    this.modifierData = modifierData;
    this.operation = operation;
  }

  public static List<SerializableModifier> getSerializableModifiers(SerializableModifier... modifiers) {
    return new ArrayList<>(Arrays.asList(modifiers));
  }

  public Modifier getModifier() {
    return modifier;
  }

  public int getLevel() {
    return level;
  }

  public CompoundNBT getModifierData() {
    return modifierData;
  }

  public InfusionOperation getOperation() {
    return operation;
  }

}
