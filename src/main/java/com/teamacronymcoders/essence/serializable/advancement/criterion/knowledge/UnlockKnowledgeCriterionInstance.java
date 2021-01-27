package com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge;

import com.google.gson.JsonObject;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class UnlockKnowledgeCriterionInstance extends CriterionInstance {
  private final Knowledge knowledge;

  public UnlockKnowledgeCriterionInstance(Knowledge knowledge, EntityPredicate.AndPredicate predicate) {
    super(new ResourceLocation(Essence.MOD_ID, "knowledge"), predicate);
    this.knowledge = knowledge;
  }

  public boolean test(Knowledge other) {
    return knowledge == other;
  }

  @Override
  public JsonObject serialize(ConditionArraySerializer conditions) {
    JsonObject object = new JsonObject();
    object.addProperty("knowledge_id", knowledge.getRegistryName().toString());
    return object;
  }
}
