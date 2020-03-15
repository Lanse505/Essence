package com.teamacronymcoders.essence.generation;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class EssenceFeatureConfigs {
    public static final TreeFeatureConfig SAPLING_ESSENCE_TREE_CONFIG = new TreeFeatureConfig.Builder(
        new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()),
        new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()),
        new BlobFoliagePlacer(5, 0)).setSapling(EssenceObjectHolders.ESSENCE_WOOD_SAPLING)
        .build();
    public static final TreeFeatureConfig NORMAL_WORLD_ESSENCE_TREE_CONFIG = new TreeFeatureConfig.Builder(
        new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()),
        new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()),
        new BlobFoliagePlacer(2, 0)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.0755f))).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().maxWaterDepth(0).setSapling(EssenceObjectHolders.ESSENCE_WOOD_SAPLING)
        .build();
    public static final TreeFeatureConfig FANCY_WORLD_ESSENCE_TREE_CONFIG = new TreeFeatureConfig.Builder(
        new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()),
        new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()),
        new BlobFoliagePlacer(0, 0)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.0755f))).setSapling(EssenceObjectHolders.ESSENCE_WOOD_SAPLING)
        .build();
}

