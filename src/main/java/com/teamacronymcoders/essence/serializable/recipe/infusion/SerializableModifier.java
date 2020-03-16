package com.teamacronymcoders.essence.serializable.recipe.infusion;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.nbt.CompoundNBT;

public class SerializableModifier {
    public Modifier modifier;
    public int level;
    public CompoundNBT modifierData;
    public Operation operation;

    public SerializableModifier(Modifier modifier, int level, CompoundNBT modifierData, Operation operation) {
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

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        ADD(0, "add"),
        REMOVE(1, "remove"),
        INCREMENT(2, "increment"),
        DECREMENT(3, "decrement");

        private static final Operation[] VALUES = new Operation[]{ADD, REMOVE, INCREMENT, DECREMENT};
        private final int id;
        private final String name;

        Operation(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Operation byID(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            } else {
                throw new IllegalArgumentException("No operation with value " + id);
            }
        }

        public int getID() {
            return this.id;
        }

        public String getName() {
            return name;
        }
    }
}
