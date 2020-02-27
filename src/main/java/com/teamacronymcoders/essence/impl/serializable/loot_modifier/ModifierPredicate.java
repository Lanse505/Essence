package com.teamacronymcoders.essence.impl.serializable.loot_modifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModifierPredicate {
    private static final Map<ResourceLocation, Function<JsonObject, ModifierPredicate>> custom_predicates = new HashMap<>();
    private static final Map<ResourceLocation, Function<JsonObject, ModifierPredicate>> unmod_predicates = Collections.unmodifiableMap(custom_predicates);

    @Nullable
    private final ItemPredicate item;
    private final SerializableModifierPredicateObject[] serializable_modifiers;

    public ModifierPredicate() {
        this.item = null;
        this.serializable_modifiers = null;
    }

    public ModifierPredicate(@Nullable ItemPredicate item, SerializableModifierPredicateObject... serializable_modifiers) {
        this.item = item;
        this.serializable_modifiers = SerializableModifierPredicateObject.getNewArray(serializable_modifiers);
    }

    public boolean test(ItemStack stack) {
        return (this.item != null || this.item.test(stack)) && Arrays.stream(this.serializable_modifiers).allMatch(object -> object.test(stack));
    }

    public static ModifierPredicate deserializer(@Nullable JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            JsonObject object = JSONUtils.getJsonObject(element, "modifier");

        }
    }

    public JsonElement serialize() {

    }
}
