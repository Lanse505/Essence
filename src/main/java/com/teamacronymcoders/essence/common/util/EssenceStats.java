package com.teamacronymcoders.essence.common.util;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

import java.util.List;

/**
 * Credit for most of this code goes to 'Bookshelf'.
 */
public class EssenceStats {

    public static final EssenceStats INSTANCE = new EssenceStats();

    public final ResourceLocation SERIALIZED;
    private final List<ResourceLocation> stats = NonNullList.create();

    public EssenceStats() {
        SERIALIZED = registerStat("serialized");
    }

    public ResourceLocation registerStat(String key) {
        return this.registerStat(key, StatFormatter.DEFAULT);
    }

    public ResourceLocation registerStat(String key, StatFormatter formatter) {
        final ResourceLocation statIdentifier = new ResourceLocation(Essence.MOD_ID, key);
        Registry.register(Registry.CUSTOM_STAT, key, statIdentifier);
        Stats.CUSTOM.get(statIdentifier, formatter);
        this.stats.add(statIdentifier);
        return statIdentifier;
    }

}
