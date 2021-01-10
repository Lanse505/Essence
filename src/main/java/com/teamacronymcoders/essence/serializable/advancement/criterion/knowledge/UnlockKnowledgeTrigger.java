package com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceCriterionTrigger;
import com.teamacronymcoders.essence.util.registration.EssenceRegistries;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public class UnlockKnowledgeTrigger extends EssenceCriterionTrigger<UnlockKnowledgeListerners, UnlockKnowledgeCriterionInstance> {

    public UnlockKnowledgeTrigger() {
        super(new ResourceLocation(Essence.MOD_ID, "knowledge"), UnlockKnowledgeListerners::new);
    }

    public void trigger(ServerPlayerEntity playerEntity, Knowledge<?> knowledge) {
        UnlockKnowledgeListerners listerners = this.getListeners(playerEntity.getAdvancements());
        if (listerners != null) {
            listerners.trigger(knowledge);
        }
    }

    @Override
    public UnlockKnowledgeCriterionInstance deserialize(JsonObject json, ConditionArrayParser conditions) {
        if (json.has("knowledge_id")) {
            String knowledgeID = json.get("knowledge_id").getAsString();
            Knowledge<?> knowledge = EssenceRegistries.KNOWLEDGE.getValue(new ResourceLocation(knowledgeID));
            if (knowledge != null) {
                return new UnlockKnowledgeCriterionInstance(knowledge, AndPredicate.ANY_AND);
            }
            throw new JsonParseException("No Knowledge found for id " + knowledgeID);
        }
        throw new JsonParseException("Field 'knowledge_id' not found");
    }
}
