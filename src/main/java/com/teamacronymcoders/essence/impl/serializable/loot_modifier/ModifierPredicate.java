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
    public static final ModifierPredicate ANY = new ModifierPredicate();
    private static final Map<ResourceLocation, Function<JsonObject, ModifierPredicate>> custom_predicates = new HashMap<>();
    private static final Map<ResourceLocation, Function<JsonObject, ModifierPredicate>> unmod_predicates = Collections.unmodifiableMap(custom_predicates);
    @Nullable
    private final ItemPredicate itemPredicate;
    private final SerializableModifierPredicateObject[] modifiers;

    public ModifierPredicate() {
        this.itemPredicate = null;
        this.modifiers = null;
    }

    public ModifierPredicate(@Nullable ItemPredicate itemPredicate, SerializableModifierPredicateObject... modifiers) {
        this.itemPredicate = itemPredicate;
        this.modifiers = SerializableModifierPredicateObject.getNewArray(modifiers);
    }

    public static ModifierPredicate deserializer(@Nullable JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            JsonObject object = element.getAsJsonObject();
            ItemPredicate itemPredicate = ItemPredicate.deserialize(object.get("itemPredicate"));
            JsonArray modifierArray = object.getAsJsonArray("modifiers");
            SerializableModifierPredicateObject[] trueArray = new SerializableModifierPredicateObject[modifierArray.size()];
            for (int i = 0; i < modifierArray.size(); i++) {
                trueArray[i] = SerializableModifierPredicateObject.deserializer(modifierArray.get(i));
            }
            return new ModifierPredicate(itemPredicate, trueArray);
        }
        return ANY;
    }

    public boolean test(ItemStack stack) {
        return (this.itemPredicate != null && this.itemPredicate.test(stack)) && Arrays.stream(this.modifiers).allMatch(object -> object.test(stack));
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject predicate = new JsonObject();
            if (this.itemPredicate != null) {
                predicate.add("itemPredicate", itemPredicate.serialize());
            }
            if (modifiers != null && modifiers.length > 0) {
                JsonArray serializable_modifiers = new JsonArray();
                for (SerializableModifierPredicateObject object : modifiers) {
                    serializable_modifiers.add(object.serializer());
                }
                predicate.add("modifiers", serializable_modifiers);
            }
            return predicate;
        }
    }
}
