package com.teamacronymcoders.essence.serializable.advancement.criterion;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;

public class EssenceCriterionTrigger<T extends EssenceCriterionListener<U>, U extends CriterionTriggerInstance> implements CriterionTrigger<U> {
  private final ResourceLocation id;
  private final Function<PlayerAdvancements, T> createNew;
  private final Map<PlayerAdvancements, T> listeners = Maps.newHashMap();

  protected EssenceCriterionTrigger(ResourceLocation id, Function<PlayerAdvancements, T> createNew) {
    this.id = id;
    this.createNew = createNew;
  }

  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public void addPlayerListener(PlayerAdvancements playerAdvancements, Listener<U> listener) {
    T listeners = this.listeners.get(playerAdvancements);
    if (listeners == null) {
      listeners = createNew.apply(playerAdvancements);
      this.listeners.put(playerAdvancements, listeners);
    }
    listeners.add(listener);
  }

  @Override
  public void removePlayerListener(PlayerAdvancements playerAdvancements, Listener<U> listener) {
    T listeners = this.listeners.get(playerAdvancements);
    if (listeners != null) {
      listeners.remove(listener);
      if (listeners.isEmpty()) {
        this.listeners.remove(playerAdvancements);
      }
    }
  }

  @Override
  public void removePlayerListeners(PlayerAdvancements playerAdvancements) {
    this.listeners.remove(playerAdvancements);
  }

  @Override
  public U createInstance(JsonObject json, DeserializationContext context) {
    return null;
  }

  @Nullable
  public T getListeners(PlayerAdvancements playerAdvancements) {
    return this.listeners.get(playerAdvancements);
  }
}
