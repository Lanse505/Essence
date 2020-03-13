package com.teamacronymcoders.essence.serializable.criterion.knowledge;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.util.ResourceLocation;

public class UnlockKnowledgeCriterionInstance extends CriterionInstance {
    private final Knowledge knowledge;

    public UnlockKnowledgeCriterionInstance(Knowledge knowledge) {
        super(new ResourceLocation(Essence.MODID, "knowledge"));
        this.knowledge = knowledge;
    }

    public boolean test(Knowledge other) {
        return knowledge == other;
    }
}
