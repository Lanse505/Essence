package com.teamacronymcoders.essence.serializable.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class MatchModifier implements ILootCondition {
    private ModifierPredicate predicate;

    public MatchModifier(ModifierPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(LootContext context) {
        ItemStack stack = context.get(LootParameters.TOOL);
        return stack != null && this.predicate.test(stack);
    }

    @Override
    public LootConditionType func_230419_b_() {
        return null;
    }

    public static class Serializer implements ILootSerializer<MatchModifier> {
        @Override
        public void serialize(JsonObject json, MatchModifier condition, JsonSerializationContext context) {
            json.add("predicate", condition.predicate.serialize());
        }

        @Override
        public MatchModifier deserialize(JsonObject json, JsonDeserializationContext context) {
            ModifierPredicate modifierPredicate = ModifierPredicate.deserializer(json.get("predicate"));
            return new MatchModifier(modifierPredicate);
        }
    }
}