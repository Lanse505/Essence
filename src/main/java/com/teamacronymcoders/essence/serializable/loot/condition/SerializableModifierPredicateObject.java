package com.teamacronymcoders.essence.serializable.loot.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.registration.EssenceRegistries;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class SerializableModifierPredicateObject {
    public static final SerializableModifierPredicateObject ANY = new SerializableModifierPredicateObject();
    private final Modifier modifier;
    @Nullable
    private final MinMaxBounds.IntBound level;

    public SerializableModifierPredicateObject() {
        this.modifier = null;
        this.level = MinMaxBounds.IntBound.UNBOUNDED;
    }

    public SerializableModifierPredicateObject(Modifier modifier, @Nullable MinMaxBounds.IntBound level) {
        this.modifier = modifier;
        if (level == null) {
            this.level = MinMaxBounds.IntBound.UNBOUNDED;
        } else {
            this.level = level;
        }
    }

    public static SerializableModifierPredicateObject[] getNewArray(SerializableModifierPredicateObject... modifiers) {
        SerializableModifierPredicateObject[] modifierArray = new SerializableModifierPredicateObject[modifiers.length];
        int counter = 0;
        for (SerializableModifierPredicateObject modifier : modifiers) {
            modifierArray[counter] = modifier;
            counter++;
        }
        return modifierArray;
    }

    public static SerializableModifierPredicateObject deserializer(@Nullable JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            JsonObject object = element.getAsJsonObject();
            Modifier modifier = EssenceRegistries.MODIFIER_REGISTRY.getValue(new ResourceLocation(object.get("modifier").getAsString()));
            return new SerializableModifierPredicateObject(modifier, MinMaxBounds.IntBound.fromJson(object.get("level")));
        }
        return ANY;
    }

    public Modifier getModifier() {
        return modifier;
    }

    @Nullable
    public MinMaxBounds.IntBound getLevel() {
        return level;
    }

    public boolean test(ItemStack stack) {
        final List<ModifierInstance> instances = EssenceModifierHelpers.getModifiers(stack);
        final int level = instances.stream().filter(instance -> instance.getModifier() == this.modifier).findFirst().map(ModifierInstance::getLevel).orElse(0);
        return !(this.level == null || this.level.isUnbounded()) && this.level.test(level);
    }

    public JsonElement serializer() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("modifier", this.modifier.getRegistryName().toString());
            if (level != null && !level.isUnbounded()) {
                object.add("level", this.level.serialize());
            }
            return object;
        }
    }
}
