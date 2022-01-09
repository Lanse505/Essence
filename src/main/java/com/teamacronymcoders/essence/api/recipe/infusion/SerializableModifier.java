package com.teamacronymcoders.essence.api.recipe.infusion;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.nbt.CompoundTag;

public class SerializableModifier {
    public Modifier modifier;
    public int level;
    public CompoundTag modifierData;
    public InfusionOperation operation;

    public SerializableModifier(Modifier modifier, int level, CompoundTag modifierData, InfusionOperation operation) {
        this.modifier = modifier;
        this.level = level;
        this.modifierData = modifierData;
        this.operation = operation;
    }

    public static SerializableModifier[] getSerializableModifiers(SerializableModifier... modifiers) {
        return modifiers;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public int getLevel() {
        return level;
    }

    public CompoundTag getModifierData() {
        return modifierData;
    }

    public InfusionOperation getOperation() {
        return operation;
    }

}
