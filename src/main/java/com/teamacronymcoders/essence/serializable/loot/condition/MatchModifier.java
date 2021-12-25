package com.teamacronymcoders.essence.serializable.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class MatchModifier implements LootItemCondition {
  private final ModifierPredicate predicate;

  public MatchModifier(ModifierPredicate predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean test(LootContext context) {
    ItemStack stack = context.hasParam(LootContextParams.TOOL) ? context.getParam(LootContextParams.TOOL) : ItemStack.EMPTY;
    return this.predicate.test(stack);
  }

  @Override
  public LootItemConditionType getType() {
    return null;
  }

  public static class ModifierSerializer implements Serializer<MatchModifier> {
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