package com.teamacronymcoders.essenceapi.advancement.criterion;

import com.teamacronymcoders.essenceapi.advancement.criterion.knowledge.EssenceKnowledgeTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class EssenceAdvancements {
    public static final EssenceKnowledgeTrigger KNOWLEDGE_TRIGGER = new EssenceKnowledgeTrigger();

    public static void setup() {
        CriteriaTriggers.register(KNOWLEDGE_TRIGGER);
    }
}
