package com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.resources.ResourceLocation;

public class UnlockKnowledgeCriterionInstance extends Criterion implements CriterionTriggerInstance {
  private final Knowledge knowledge;

  public UnlockKnowledgeCriterionInstance(Knowledge knowledge) {
    super();
    this.knowledge = knowledge;
  }

  public boolean test(Knowledge other) {
    return knowledge == other;
  }

  @Override
  public JsonElement serializeToJson() {
    JsonObject object = new JsonObject();
    object.addProperty("knowledge_id", knowledge.getRegistryName().toString());
    return object;
  }

  @Override
  public ResourceLocation getCriterion() {
    return null;
  }

  @Override
  public JsonObject serializeToJson(SerializationContext context) {
    return null;
  }
}
