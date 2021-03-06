package com.teamacronymcoders.essence.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionOperation;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class EssenceSerializableObjectHandler {

  // SerializableModifier
  public static SerializableModifier readSerializableModifier(PacketBuffer buffer) {
    final Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(buffer.readString(0)));
    final int level = buffer.readInt();
    final CompoundNBT compound = buffer.readCompoundTag();
    final String operator = buffer.readString(1);
    return new SerializableModifier(modifier, level, compound, InfusionOperation.valueOf(operator));
  }

  public static void writeSerializableModifier(PacketBuffer buffer, SerializableModifier serializableModifier) {
    buffer.writeString(serializableModifier.getModifier().getRegistryName().toString(), 0);
    buffer.writeInt(serializableModifier.getLevel());
    buffer.writeCompoundTag(serializableModifier.getModifierData());
    buffer.writeString(serializableModifier.getOperation().getName(), 1);
  }

  public static JsonObject writeSerializableModifier(SerializableModifier serializableModifier) {
    JsonObject object = new JsonObject();
    object.addProperty("modifier", serializableModifier.getModifier().getRegistryName().toString());
    object.addProperty("level", serializableModifier.getLevel());
    object.addProperty("compound", serializableModifier.getModifierData().toString());
    object.addProperty("operation", serializableModifier.getOperation().getName());
    return object;
  }

  public static SerializableModifier readSerializableModifier(JsonElement element) {
    JsonObject object = element.getAsJsonObject();
    Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(object.get("modifier").getAsString()));
    int level = object.get("level").getAsInt();
    CompoundNBT compound = null;
    try {
      compound = JsonToNBT.getTagFromJson(object.get("compound").getAsString());
    } catch (CommandSyntaxException e) {
      e.printStackTrace();
    }
    InfusionOperation operation = InfusionOperation.valueOf(object.get("operation").getAsString());
    return new SerializableModifier(modifier, level, compound, operation);
  }

  // SerializableModifier[]
  public static SerializableModifier[] readSerializableModifierArray(PacketBuffer buffer) {
    SerializableModifier[] serializableModifiers = new SerializableModifier[buffer.readInt()];
    for (int i = 0; i < serializableModifiers.length; i++) {
      final Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(buffer.readString(0)));
      final int level = buffer.readInt();
      final CompoundNBT compound = buffer.readCompoundTag();
      final String operator = buffer.readString(1);
      serializableModifiers[i] = new SerializableModifier(modifier, level, compound, InfusionOperation.valueOf(operator));
    }
    return serializableModifiers;
  }

  public static void writeSerializableModifierArray(PacketBuffer buffer, SerializableModifier[] serializableModifiers) {
    buffer.writeInt(serializableModifiers.length);
    for (SerializableModifier serializableModifier : serializableModifiers) {
      buffer.writeString(serializableModifier.getModifier().getRegistryName().toString(), 0);
      buffer.writeInt(serializableModifier.getLevel());
      buffer.writeCompoundTag(serializableModifier.getModifierData());
      buffer.writeString(serializableModifier.getOperation().getName(), 1);
    }
  }

  public static JsonArray writeSerializableModifierArray(SerializableModifier[] serializableModifiers) {
    JsonArray array = new JsonArray();
    for (SerializableModifier serializableModifier : serializableModifiers) {
      JsonObject object = new JsonObject();
      object.addProperty("modifier", serializableModifier.getModifier().getRegistryName().toString());
      object.addProperty("level", serializableModifier.getLevel());
      object.addProperty("compound", serializableModifier.getModifierData() != null ? serializableModifier.modifierData.toString() : new CompoundNBT().toString());
      object.addProperty("operation", serializableModifier.getOperation().getName());
      array.add(object);
    }
    return array;
  }

  public static SerializableModifier[] readSerializableModifierArray(JsonElement element) {
    JsonArray array = element.getAsJsonArray();
    SerializableModifier[] serializableModifiers = new SerializableModifier[array.size()];
    for (int i = 0; i < array.size(); i++) {
      JsonObject object = array.get(i).getAsJsonObject();
      Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(object.get("modifier").getAsString()));
      int level = object.get("level").getAsInt();
      CompoundNBT compound = null;
      try {
        compound = JsonToNBT.getTagFromJson(object.get("compound").getAsString());
      } catch (CommandSyntaxException e) {
        e.printStackTrace();
      }
      InfusionOperation operation = InfusionOperation.valueOf(object.get("operation").getAsString());
      serializableModifiers[i] = new SerializableModifier(modifier, level, compound, operation);
    }
    return serializableModifiers;
  }

  public static void writeBlockState(PacketBuffer buffer, BlockState state) {
    buffer.writeCompoundTag(NBTUtil.writeBlockState(state));
  }

  public static BlockState readBlockState(PacketBuffer buffer) {
    return NBTUtil.readBlockState(buffer.readCompoundTag());
  }

  public static JsonElement writeBlockState(BlockState state) {
    return EssenceJsonHelper.serializeBlockState(state);
  }

  public static BlockState readBlockState(JsonElement element) {
    return EssenceJsonHelper.deserializeBlockState(element.getAsJsonObject());
  }
}
