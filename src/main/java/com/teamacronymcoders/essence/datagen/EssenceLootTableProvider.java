package com.teamacronymcoders.essence.datagen;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;

public class EssenceLootTableProvider extends LootTableProvider {

  public EssenceLootTableProvider (DataGenerator generator) {
    super(generator);
  }

  @Override
  public String getName () {
    return "Essence Loot-Table Provider";
  }

  @Override
  protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables () {
    return Lists.newArrayList(Pair.of(EssenceEntityLootTables::new, LootParameterSets.ENTITY));
  }

  @Override
  protected void validate (Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
  }
}
