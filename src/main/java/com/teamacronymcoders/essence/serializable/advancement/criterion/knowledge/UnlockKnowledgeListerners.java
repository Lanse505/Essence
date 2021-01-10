package com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge;

import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceCriterionListener;
import net.minecraft.advancements.PlayerAdvancements;

public class UnlockKnowledgeListerners extends EssenceCriterionListener<UnlockKnowledgeCriterionInstance> {

  public UnlockKnowledgeListerners (PlayerAdvancements advancements) {
    super(advancements);
  }

  public void trigger (final Knowledge<?> knowledge) {
    trigger(instance -> instance.test(knowledge));
  }
}
