package com.teamacronymcoders.essence.serializable.advancement.criterion;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;

public class EssenceCriterionTrigger<T extends EssenceCriterionListener<U>, U extends AbstractCriterionTriggerInstance> extends SimpleCriterionTrigger<U> {
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
  protected U createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
    return null;
  }

  @Nullable
  public T getListeners(PlayerAdvancements playerAdvancements) {
    return this.listeners.get(playerAdvancements);
  }
}
