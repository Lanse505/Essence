package com.teamacronymcoders.essence.serializable.advancement.criterion;

import com.google.common.collect.Sets;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.server.PlayerAdvancements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class EssenceCriterionListener<T extends CriterionTriggerInstance> {
  private final PlayerAdvancements advancements;
  private final Set<CriterionTrigger.Listener<T>> listeners = Sets.newHashSet();

  public EssenceCriterionListener(PlayerAdvancements advancements) {
    this.advancements = advancements;
  }

  public boolean isEmpty() {
    return this.listeners.isEmpty();
  }

  public void add(CriterionTrigger.Listener<T> listener) {
    this.listeners.add(listener);
  }

  public void remove(CriterionTrigger.Listener<T> listener) {
    this.listeners.remove(listener);
  }

  public void trigger(Predicate<T> predicate) {
    List<CriterionTrigger.Listener<T>> toGrant = new ArrayList<>();
    for (CriterionTrigger.Listener<T> listener : this.listeners) {
      if (predicate.test(listener.getTriggerInstance())) {
        toGrant.add(listener);
      }
    }
    toGrant.forEach(listener -> listener.run(this.advancements));
  }
}
