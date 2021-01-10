package com.teamacronymcoders.essence.serializable.advancement.criterion;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;

public class EssenceCriterionListener<T extends ICriterionInstance> {
  private final PlayerAdvancements advancements;
  private final Set<ICriterionTrigger.Listener<T>> listeners = Sets.newHashSet();

  public EssenceCriterionListener (PlayerAdvancements advancements) {
    this.advancements = advancements;
  }

  public boolean isEmpty () {
    return this.listeners.isEmpty();
  }

  public void add (ICriterionTrigger.Listener<T> listener) {
    this.listeners.add(listener);
  }

  public void remove (ICriterionTrigger.Listener<T> listener) {
    this.listeners.remove(listener);
  }

  public void trigger (Predicate<T> predicate) {
    List<ICriterionTrigger.Listener<T>> toGrant = new ArrayList<>();
    for (ICriterionTrigger.Listener<T> listener : this.listeners) {
      if (predicate.test(listener.getCriterionInstance())) {
        toGrant.add(listener);
      }
    }
    toGrant.forEach(listener -> listener.grantCriterion(this.advancements));
  }
}
