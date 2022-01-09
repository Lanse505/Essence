package com.teamacronymcoders.essence.common.util.helper;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Map;
import java.util.Optional;

public class EssenceJsonHelper {

    /**
     * Credit for the following Methods goes to Darkhax and his library mod Bookshelf <3
     */
    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(JsonObject json, String memberName, IForgeRegistry<T> registry) {
        if (json.has(memberName)) {
            return getRegistryEntry(json.get(memberName), memberName, registry);
        } else {
            throw new JsonSyntaxException("Missing required value " + memberName);
        }
    }

    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(JsonElement json, String memberName, IForgeRegistry<T> registry) {
        if (json == null) {
            throw new JsonSyntaxException("The property " + memberName + " is missing.");
        }
        if (json.isJsonPrimitive()) {
            final String rawId = json.getAsString();
            final ResourceLocation registryId = ResourceLocation.tryParse(rawId);
            if (registryId != null) {
                final T registryEntry = registry.getValue(registryId);
                if (registryEntry != null) {
                    return registryEntry;
                } else {
                    throw new JsonSyntaxException("No entry found for id " + rawId);
                }
            } else {
                throw new JsonSyntaxException("Registry id " + rawId + " for property " + memberName + " was not a valid format.");
            }
        } else {
            throw new JsonSyntaxException("Expected " + memberName + " to be a JSON primitive. was " + GsonHelper.getType(json));
        }
    }

    public static Block getBlock(JsonObject json, String memberName) {
        return getRegistryEntry(json.get(memberName), memberName, ForgeRegistries.BLOCKS);
    }

    public static Potion getPotion(JsonObject json, String memberName) {
        return getRegistryEntry(json.get(memberName), memberName, ForgeRegistries.POTIONS);
    }

    public static MobEffect getMobEffect(JsonObject json, String memberName) {
        return getRegistryEntry(json.get(memberName), memberName, ForgeRegistries.MOB_EFFECTS);
    }

    public static JsonElement serializeBlockState(BlockState state) {
        final JsonObject object = new JsonObject();
        object.addProperty("block", state.getBlock().getRegistryName().toString());
        final JsonObject propertiesElement = new JsonObject();
        ImmutableMap<Property<?>, Comparable<?>> map = state.getValues();
        for (ImmutableMap.Entry<Property<?>, Comparable<?>> entry : map.entrySet()) {
            propertiesElement.addProperty(entry.getKey().getName(), entry.getValue().toString());
        }
        object.add("properties", propertiesElement);
        return object;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static BlockState deserializeBlockState(JsonObject json) {
        // Read the block from the forge registry.
        final Block block = getBlock(json, "block");
        // Start off with the default state.
        BlockState state = block.defaultBlockState();
        // If the properties member exists, attempt to assign properties to the block state.
        if (json.has("properties")) {
            final JsonElement propertiesElement = json.get("properties");
            if (propertiesElement.isJsonObject()) {
                final JsonObject props = propertiesElement.getAsJsonObject();
                // Iterate each member of the properties object. Expecting a simple key to
                // primitive string structure.
                for (final Map.Entry<String, JsonElement> property : props.entrySet()) {
                    // Check the block for the property. Keys = property names.
                    final Property blockProperty = block.getStateDefinition().getProperty(property.getKey());
                    if (blockProperty != null) {
                        if (property.getValue().isJsonPrimitive()) {
                            // Attempt to parse the value with the the property.
                            final String valueString = property.getValue().getAsString();
                            final Optional<Comparable> propValue = blockProperty.getValue(valueString);
                            if (propValue.isPresent()) {
                                // Update the state with the new property.
                                try {
                                    state = state.setValue(blockProperty, propValue.get());
                                } catch (final Exception e) {
                                    Essence.LOGGER.error("Failed to update state for block {}. The mod that adds this block has issues.", block.getRegistryName());
                                    Essence.LOGGER.catching(e);
                                }
                            } else {
                                throw new JsonSyntaxException("The property " + property.getKey() + " with value " + valueString + " coul not be parsed!");
                            }
                        } else {
                            throw new JsonSyntaxException("Expected property value for " + property.getKey() + " to be primitive string. Got " + GsonHelper.getType(property.getValue()));
                        }
                    } else {
                        throw new JsonSyntaxException("The property " + property.getKey() + " is not valid for block " + block.getRegistryName());
                    }
                }
            } else {
                throw new JsonSyntaxException("Expected properties to be an object. Got " + GsonHelper.getType(propertiesElement));
            }
        }
        return state;
    }

    public static JsonElement serializeEffectInstance(MobEffectInstance instance) {
        final JsonObject object = new JsonObject();
        object.addProperty("effect", instance.getEffect().getRegistryName().toString());
        object.addProperty("duration", instance.getDuration());
        final JsonObject propertiesElement = new JsonObject();
        if (instance.getAmplifier() > 0) {
            propertiesElement.addProperty("amplifier", instance.getAmplifier());
            propertiesElement.addProperty("ambient", instance.isAmbient());
            propertiesElement.addProperty("showParticles", instance.isVisible());
            propertiesElement.addProperty("showIcon", instance.showIcon());
        }
        object.add("properties", propertiesElement);
        return object;
    }

    public static MobEffectInstance deserializeEffectInstance(JsonObject json) {
        // Read the effect from the forge registry.
        final MobEffect effect = getMobEffect(json, "effect");
        final int duration = json.get("duration").getAsInt();
        if (json.has("properties")) {
            final JsonElement propertiesElement = json.get("properties");
            if (propertiesElement.isJsonObject()) {
                final JsonObject properties = propertiesElement.getAsJsonObject();
                if (properties.has("amplifier")) {
                    final int amplifier = properties.getAsJsonObject("amplifier").getAsInt();
                    if (properties.has("ambient")) {
                        final boolean ambient = properties.getAsJsonObject("ambient").getAsBoolean();
                        if (properties.has("showParticles")) {
                            final boolean showParticles = properties.getAsJsonObject("showParticles").getAsBoolean();
                            if (properties.has("showIcon")) {
                                final boolean showIcon = properties.getAsJsonObject("showIcon").getAsBoolean();
                                return new MobEffectInstance(effect, duration, amplifier, ambient, showParticles, showIcon);
                            }
                            return new MobEffectInstance(effect, duration, amplifier, ambient, showParticles);
                        } else {
                            throw new JsonSyntaxException("EffectInstance requires both Value for 'ambient' and Value for 'showParticles'");
                        }
                    }
                    return new MobEffectInstance(effect, duration, amplifier);
                }
            }
        }
        return new MobEffectInstance(effect, duration);
    }

    public static JsonElement serializeEntityType(EntityType<?> type) {
        JsonObject object = new JsonObject();
        object.addProperty("entityType", type.getRegistryName().toString());
        return object;
    }

    public static EntityType<?> deserializeEntityType(JsonObject json) {
        return getRegistryEntry(json.get("entityType"), "entityType", ForgeRegistries.ENTITIES);
    }

    public static JsonElement serializeResourceLocation(ResourceLocation type) {
        JsonObject object = new JsonObject();
        object.addProperty("rl", type.toString());
        return object;
    }

    public static ResourceLocation deserializeResourceLocation(JsonObject json) {
        return new ResourceLocation(json.get("rl").getAsString());
    }

    public static NBTIngredient getNBTIngredient(ItemStack stack) {
        return new NBTIngredient(stack) {
        };
    }
}
