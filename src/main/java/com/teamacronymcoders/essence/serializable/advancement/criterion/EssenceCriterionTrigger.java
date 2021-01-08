package com.teamacronymcoders.essence.serializable.advancement.criterion;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;

public class EssenceCriterionTrigger<T extends EssenceCriterionListener<U>, U extends ICriterionInstance> implements ICriterionTrigger<U> {
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
    public void addListener(PlayerAdvancements playerAdvancements, Listener<U> listener) {
        T listeners = this.listeners.get(playerAdvancements);
        if (listeners == null) {
            listeners = createNew.apply(playerAdvancements);
            this.listeners.put(playerAdvancements, listeners);
        }
        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancements, Listener<U> listener) {
        T listeners = this.listeners.get(playerAdvancements);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(playerAdvancements);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancements) {
        this.listeners.remove(playerAdvancements);
    }

    @Override
    public U deserialize(JsonObject object, ConditionArrayParser conditions) {
        return null;
    }

    @Nullable
    public T getListeners(PlayerAdvancements playerAdvancements) {
        return this.listeners.get(playerAdvancements);
    }
}
