package com.teamacronymcoders.essence.api.recipe.infusion;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import net.minecraft.nbt.CompoundTag;

public class SerializableModifier {
    public IModifier modifier;
    public int level;
    public CompoundTag modifierData;
    public InfusionOperation operation;

    public SerializableModifier(IModifier modifier, int level, CompoundTag modifierData, InfusionOperation operation) {
        this.modifier = modifier;
        this.level = level;
        this.modifierData = modifierData;
        this.operation = operation;
    }

    public static SerializableModifier[] getSerializableModifiers(SerializableModifier... modifiers) {
        return modifiers;
    }

    public IModifier getModifier() {
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
