package com.teamacronymcoders.essence.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionOperation;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceJsonHelper;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EssenceSerializableObjectHandler {

    // SerializableModifier
    public static SerializableModifier readSerializableModifier(FriendlyByteBuf buffer) {
        final Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(buffer.readUtf(0)));
        final int level = buffer.readInt();
        final CompoundTag compound = buffer.readNbt();
        final String operator = buffer.readUtf(1);
        return new SerializableModifier(modifier, level, compound, InfusionOperation.valueOf(operator));
    }

    public static void writeSerializableModifier(FriendlyByteBuf buffer, SerializableModifier serializableModifier) {
        buffer.writeUtf(serializableModifier.getModifier().getRegistryName().toString(), 0);
        buffer.writeInt(serializableModifier.getLevel());
        buffer.writeNbt(serializableModifier.getModifierData());
        buffer.writeUtf(serializableModifier.getOperation().getName(), 1);
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
        CompoundTag compound = null;
        try {
            compound = TagParser.parseTag(object.get("compound").getAsString());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        InfusionOperation operation = InfusionOperation.byName(object.get("operation").getAsString());
        return new SerializableModifier(modifier, level, compound, operation);
    }

    // SerializableModifier[]
    public static SerializableModifier[] readSerializableModifierArray(FriendlyByteBuf buffer) {
        SerializableModifier[] serializableModifiers = new SerializableModifier[buffer.readInt()];
        for (int i = 0; i < serializableModifiers.length; i++) {
            final Modifier modifier = EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(buffer.readUtf(0)));
            final int level = buffer.readInt();
            final CompoundTag compound = buffer.readNbt();
            final String operator = buffer.readUtf(1);
            serializableModifiers[i] = new SerializableModifier(modifier, level, compound, InfusionOperation.valueOf(operator));
        }
        return serializableModifiers;
    }

    public static void writeSerializableModifierArray(FriendlyByteBuf buffer, SerializableModifier[] serializableModifiers) {
        buffer.writeInt(serializableModifiers.length);
        for (SerializableModifier serializableModifier : serializableModifiers) {
            buffer.writeUtf(serializableModifier.getModifier().getRegistryName().toString(), 0);
            buffer.writeInt(serializableModifier.getLevel());
            buffer.writeNbt(serializableModifier.getModifierData());
            buffer.writeUtf(serializableModifier.getOperation().getName(), 1);
        }
    }

    public static JsonArray writeSerializableModifierArray(SerializableModifier[] serializableModifiers) {
        JsonArray array = new JsonArray();
        for (SerializableModifier serializableModifier : serializableModifiers) {
            JsonObject object = new JsonObject();
            object.addProperty("modifier", serializableModifier.getModifier().getRegistryName().toString());
            object.addProperty("level", serializableModifier.getLevel());
            object.addProperty("compound", serializableModifier.getModifierData() != null ? serializableModifier.modifierData.toString() : new CompoundTag().toString());
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
            CompoundTag compound = null;
            try {
                compound = TagParser.parseTag(object.get("compound").getAsString());
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            InfusionOperation operation = InfusionOperation.byName(object.get("operation").getAsString());
            serializableModifiers[i] = new SerializableModifier(modifier, level, compound, operation);
        }
        return serializableModifiers;
    }

    public static void writeBlockState(FriendlyByteBuf buffer, BlockState state) {
        buffer.writeNbt(NbtUtils.writeBlockState(state));
    }

    public static BlockState readBlockState(FriendlyByteBuf buffer) {
        return NbtUtils.readBlockState(buffer.readNbt());
    }

    public static JsonElement writeBlockState(BlockState state) {
        return EssenceJsonHelper.serializeBlockState(state);
    }

    public static BlockState readBlockState(JsonElement element) {
        return EssenceJsonHelper.deserializeBlockState(element.getAsJsonObject());
    }

    public static JsonElement writeEntityType(EntityType<?> type) {
        return EssenceJsonHelper.serializeEntityType(type);
    }

    public static void writeEntityType(FriendlyByteBuf buffer, EntityType<?> type) {
        buffer.writeUtf(type.getRegistryName().toString());
    }

    public static EntityType<?> readEntityType(JsonElement element) {
        return EssenceJsonHelper.deserializeEntityType(element.getAsJsonObject());
    }

    public static EntityType<?> readEntityType(FriendlyByteBuf buffer) {
        String id = buffer.readUtf();
        ResourceLocation entityId = new ResourceLocation(id);
        RegistryObject<EntityType<?>> entityType = RegistryObject.of(entityId, ForgeRegistries.ENTITIES);
        return entityType.get();
    }

    public static JsonElement writeResourceLocation(ResourceLocation resourceLocation) {
        return EssenceJsonHelper.serializeResourceLocation(resourceLocation);
    }

    public static void writeResourceLocation(FriendlyByteBuf buffer, ResourceLocation resourceLocation) {
        buffer.writeUtf(resourceLocation.toString());
    }

    public static ResourceLocation readResourceLocation(JsonElement element) {
        return EssenceJsonHelper.deserializeResourceLocation(element.getAsJsonObject());
    }

    public static ResourceLocation readResourceLocation(FriendlyByteBuf buffer) {
        String id = buffer.readUtf();
        ResourceLocation rl = new ResourceLocation(id);
        return rl;
    }
}
