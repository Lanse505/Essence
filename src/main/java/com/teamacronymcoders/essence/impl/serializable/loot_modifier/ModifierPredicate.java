package com.teamacronymcoders.essence.impl.serializable.loot_modifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.ItemStack;
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
    public static final ModifierPredicate ANY = new ModifierPredicate();

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
        return (this.item != null && this.item.test(stack)) && Arrays.stream(this.serializable_modifiers).allMatch(object -> object.test(stack));
    }

    public static ModifierPredicate deserializer(@Nullable JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            JsonObject object = element.getAsJsonObject();
            ItemPredicate itemPredicate = ItemPredicate.deserialize(object.get("item"));
            JsonArray modifierArray = object.getAsJsonArray("modifiers");
            SerializableModifierPredicateObject[] trueArray = new SerializableModifierPredicateObject[modifierArray.size()];
            for (int i = 0; i < modifierArray.size(); i++) {
                trueArray[i] = SerializableModifierPredicateObject.deserializer(modifierArray.get(i));
            }
            return new ModifierPredicate(itemPredicate, trueArray);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject predicate = new JsonObject();
            if (this.item != null) {
                predicate.add("item", item.serialize());
            }
            if (serializable_modifiers != null && serializable_modifiers.length > 0) {
                JsonArray modifiers = new JsonArray();
                for (SerializableModifierPredicateObject object : serializable_modifiers) {
                    modifiers.add(object.serializer());
                }
                predicate.add("modifiers", modifiers);
            }
            return predicate;
        }
    }
}
