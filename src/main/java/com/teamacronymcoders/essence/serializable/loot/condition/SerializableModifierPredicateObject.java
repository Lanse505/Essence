package com.teamacronymcoders.essence.serializable.loot.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SerializableModifierPredicateObject {
  public static final SerializableModifierPredicateObject ANY = new SerializableModifierPredicateObject();
  private final Modifier modifier;
  @Nullable
  private final MinMaxBounds.Ints level;

  public SerializableModifierPredicateObject() {
    this.modifier = null;
    this.level = MinMaxBounds.Ints.ANY;
  }

  public SerializableModifierPredicateObject(Modifier modifier, @Nullable MinMaxBounds.Ints level) {
    this.modifier = modifier;
    if (level == null) {
      this.level = MinMaxBounds.Ints.ANY;
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
      Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(object.get("modifier").getAsString()));
      return new SerializableModifierPredicateObject(modifier, MinMaxBounds.Ints.fromJson(object.get("level")));
    }
    return ANY;
  }

  public Modifier getModifier() {
    return modifier;
  }

  @Nullable
  public MinMaxBounds.Ints getLevel() {
    return level;
  }

  public boolean test(ItemStack stack) {
    final List<ModifierInstance> instances = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());
    final int level = instances.stream().filter(instance -> instance.getModifier() == this.modifier).findFirst().map(ModifierInstance::getLevel).orElse(0);
    return !(this.level == null || this.level.isAny()) && this.level.matches(level);
  }

  public JsonElement serializer() {
    if (this == ANY) {
      return JsonNull.INSTANCE;
    } else {
      JsonObject object = new JsonObject();
      object.addProperty("modifier", this.modifier.getRegistryName().toString());
      if (level != null && !level.isAny()) {
        object.add("level", this.level.serializeToJson());
      }
      return object;
    }
  }
}
