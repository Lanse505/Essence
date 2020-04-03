package com.teamacronymcoders.essence.datagen;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.essence.datagen.loottable.EssenceBlockLootTables;
import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.ValidationTracker;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EssenceLootTableProvider extends LootTableProvider {

    public EssenceLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Essence Loot-Table Provider";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return Lists.newArrayList(
            Pair.of(EssenceEntityLootTables::new, LootParameterSets.ENTITY),
            Pair.of(EssenceBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {}
}
