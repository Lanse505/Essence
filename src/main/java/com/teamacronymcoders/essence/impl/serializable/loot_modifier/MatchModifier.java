package com.teamacronymcoders.essence.impl.serializable.loot_modifier;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

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

    public static class Serializer extends ILootCondition.AbstractSerializer<MatchModifier> {

        public Serializer() {
            super(new ResourceLocation(Essence.MODID,"modifier_matcher"), MatchModifier.class);
        }

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