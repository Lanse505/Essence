package com.teamacronymcoders.essence.serializable.criterion.knowledge;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.serializable.criterion.EssenceCriterionTrigger;
import com.teamacronymcoders.essence.utils.registration.EssenceRegistries;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class UnlockKnowledgeTrigger extends EssenceCriterionTrigger<UnlockKnowledgeListerners, UnlockKnowledgeCriterionInstance> {

    public UnlockKnowledgeTrigger() {
        super(new ResourceLocation(Essence.MODID, "knowledge"), UnlockKnowledgeListerners::new);
    }

    public void trigger(ServerPlayerEntity playerEntity, Knowledge knowledge) {
        UnlockKnowledgeListerners listerners = this.getListeners(playerEntity.getAdvancements());
        if (listerners != null) {
            listerners.trigger(knowledge);
        }
    }

    @Override
    public UnlockKnowledgeCriterionInstance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        if (json.has("knowledge_id")) {
            String knowledgeID = json.get("knowledge_id").getAsString();
            Knowledge knowledge = EssenceRegistries.KNOWLEDGE_REGISTRY.getValue(new ResourceLocation(knowledgeID));
            if (knowledge != null) {
                return new UnlockKnowledgeCriterionInstance(knowledge);
            }
            throw new JsonParseException("No Knowledge found for id " + knowledgeID);
        }
        throw new JsonParseException("Field 'knowledge_id' not found");
    }
}
