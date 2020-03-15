package com.teamacronymcoders.essence.generation;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

import javax.annotation.Nullable;

public class EssenceFeatureConfigs {
    public static final TreeFeatureConfig SAPLING_ESSENCE_TREE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(5, 0)).setSapling(EssenceObjectHolders.ESSENCE_WOOD_SAPLING).build();
    public static final TreeFeatureConfig WORLD_ESSENCE_TREE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(5, 0)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.0755f))).setSapling(EssenceObjectHolders.ESSENCE_WOOD_SAPLING).build();

    private static ConfiguredFeature<?, ?> ESSENCE_ORE_FEATURE;
    private static ConfiguredFeature<?, ?> ESSENCE_CRYSTAL_ORE_FEATURE;

}

