package com.teamacronymcoders.essence.util;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.Essence;
import java.util.List;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

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
    return this.registerStat(key, IStatFormatter.DEFAULT);
  }

  public ResourceLocation registerStat(String key, IStatFormatter formatter) {
    final ResourceLocation statIdentifier = new ResourceLocation(Essence.MOD_ID, key);
    Registry.register(Registry.CUSTOM_STAT, key, statIdentifier);
    Stats.CUSTOM.get(statIdentifier, formatter);
    this.stats.add(statIdentifier);
    return statIdentifier;
  }

  public List<ResourceLocation> getStatIdentifiers() {
    return ImmutableList.copyOf(this.stats);
  }

}
