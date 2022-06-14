package com.teamacronymcoders.essenceapi.advancement.criterion.knowledge;

import com.google.gson.JsonObject;
import com.teamacronymcoders.essenceapi.EssenceAPI;
import com.teamacronymcoders.essenceapi.EssenceRegistries;
import com.teamacronymcoders.essenceapi.knowledge.Knowledge;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class EssenceKnowledgeTrigger extends SimpleCriterionTrigger<EssenceKnowledgeTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(EssenceAPI.MOD_ID, "knowledge");

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        JsonObject object = GsonHelper.getAsJsonObject(pJson, "knowledge", new JsonObject());
        String knowledge_id = GsonHelper.getAsString(object, "knowledge_id");
        return new TriggerInstance(pPlayer, EssenceRegistries.KNOWLEDGE.get().getValue(new ResourceLocation(knowledge_id)));
    }

    public void trigger(ServerPlayer player, Knowledge knowledge) {
        this.trigger(player, k -> k.matches(k.knowledge, knowledge));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public Knowledge knowledge;

        public TriggerInstance(EntityPredicate.Composite pPlayer, Knowledge knowledge) {
            super(EssenceKnowledgeTrigger.ID, pPlayer);
            this.knowledge = knowledge;
        }

        public static TriggerInstance knowledge(Knowledge knowledge) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, knowledge);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject object = super.serializeToJson(pConditions);
            if (knowledge != null) {
                JsonObject object1 = new JsonObject();
                object1.addProperty("knowledge_id", EssenceRegistries.KNOWLEDGE.get().getKey(knowledge).toString());
                object.add("knowledge", object1);
            }
            return object;
        }

        public boolean matches(Knowledge knowledge, Knowledge other) {
            return knowledge == other;
        }

        public Knowledge getKnowledge() {
            return knowledge;
        }

    }
}
