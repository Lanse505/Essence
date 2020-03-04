package com.teamacronymcoders.essence.impl.serializable.recipe;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.nbt.CompoundNBT;
import org.apache.commons.lang3.tuple.Pair;

public class SerializableModifier {
    private Modifier modifier;
    private Pair<Integer, CompoundNBT> info;
    private Operation operation;

    public SerializableModifier(Modifier modifier, Pair<Integer, CompoundNBT> level, Operation operation) {
        this.modifier = modifier;
        this.info = level;
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

    public Pair<Integer, CompoundNBT> getInfo() {
        return info;
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        ADD(0, "add"),
        REMOVE(1, "remove"),
        REPLACE(2, "replace"),
        INCREMENT(3, "increment"),
        DECREMENT(4, "decrement");

        private static final Operation[] VALUES = new Operation[]{ADD, REMOVE, REPLACE, INCREMENT, DECREMENT};
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
