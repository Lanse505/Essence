package com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.registrate.EssenceKnowledgeRegistrate;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceCriterionTrigger;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UnlockKnowledgeTrigger extends EssenceCriterionTrigger<UnlockKnowledgeListerners, UnlockKnowledgeCriterionInstance> {

  public UnlockKnowledgeTrigger() {
    super(new ResourceLocation(Essence.MOD_ID, "knowledge"), UnlockKnowledgeListerners::new);
  }

  public void trigger(ServerPlayer playerEntity, Knowledge knowledge) {
    UnlockKnowledgeListerners listerners = this.getListeners(playerEntity.getAdvancements());
    if (listerners != null) {
      listerners.trigger(knowledge);
    }
  }

  @Override
  public UnlockKnowledgeCriterionInstance createInstance(JsonObject json, DeserializationContext context) {
    if (json.has("knowledge_id")) {
      String knowledgeID = json.get("knowledge_id").getAsString();
      Knowledge knowledge = EssenceKnowledgeRegistrate.REGISTRY.get().getValue(new ResourceLocation(knowledgeID));
      if (knowledge != null) {
        return new UnlockKnowledgeCriterionInstance(knowledge);
      }
      throw new JsonParseException("No Knowledge found for id " + knowledgeID);
    }
    throw new JsonParseException("Field 'knowledge_id' not found");
  }
}
