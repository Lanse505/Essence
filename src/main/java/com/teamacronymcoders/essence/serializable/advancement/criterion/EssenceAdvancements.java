package com.teamacronymcoders.essence.serializable.advancement.criterion;

import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class EssenceAdvancements {
  public static final UnlockKnowledgeTrigger UNLOCK_KNOWLEDGE = new UnlockKnowledgeTrigger();

  public static void setup() {
    CriteriaTriggers.register(UNLOCK_KNOWLEDGE);
  }
}
