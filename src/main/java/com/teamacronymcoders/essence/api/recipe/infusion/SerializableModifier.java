package com.teamacronymcoders.essence.api.recipe.infusion;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
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

    public static SerializableModifier[] getNewArray(SerializableModifier... modifiers) {
        SerializableModifier[] modifierArray = new SerializableModifier[modifiers.length];
        int counter = 0;
        for (SerializableModifier modifier : modifiers) {
            modifierArray[counter] = modifier;
            counter++;
        }
        return modifierArray;
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
