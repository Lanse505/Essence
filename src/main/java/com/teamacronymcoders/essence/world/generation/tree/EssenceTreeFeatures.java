package com.teamacronymcoders.essence.world.generation.tree;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.OptionalInt;

public class EssenceTreeFeatures {
    public static final TreeConfiguration SAPLING_ESSENCE_TREE_CONFIG = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(EssenceBlockRegistrate.ESSENCE_WOOD_LOG.getDefaultState()),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(EssenceBlockRegistrate.ESSENCE_WOOD_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .ignoreVines()
            .build();
    public static final ConfiguredFeature<TreeConfiguration, ?> SAPLING_ESSENCE_TREE_FEATURE = FeatureUtils.register("essence_tree_sapling", Feature.TREE.configured(SAPLING_ESSENCE_TREE_CONFIG));
    public static final PlacedFeature SAPLING_ESSENCE_TREE_FEATURE_PLACED = PlacementUtils.register("essence_tree_sapling", SAPLING_ESSENCE_TREE_FEATURE.placed());

    public static final TreeConfiguration NORMAL_WORLD_ESSENCE_TREE_CONFIG = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(EssenceBlockRegistrate.ESSENCE_WOOD_LOG.getDefaultState()),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(EssenceBlockRegistrate.ESSENCE_WOOD_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .decorators(ImmutableList.of(new BeehiveDecorator(0.0755f)))
            .forceDirt()
            .ignoreVines()
            .build();
    public static final ConfiguredFeature<TreeConfiguration, ?> NORMAL_ESSENCE_TREE_FEATURE = FeatureUtils.register("essence_tree_normal", Feature.TREE.configured(NORMAL_WORLD_ESSENCE_TREE_CONFIG));
    public static final PlacedFeature NORMAL_ESSENCE_TREE_FEATURE_PLACED = PlacementUtils.register("essence_tree_normal", NORMAL_ESSENCE_TREE_FEATURE.placed());

    public static final TreeConfiguration FANCY_WORLD_ESSENCE_TREE_CONFIG = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(EssenceBlockRegistrate.ESSENCE_WOOD_LOG.getDefaultState()),
            new FancyTrunkPlacer(3, 11, 0),
            BlockStateProvider.simple(EssenceBlockRegistrate.ESSENCE_WOOD_LEAVES.getDefaultState()),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
            .ignoreVines()
            .decorators(ImmutableList.of(new BeehiveDecorator(0.0755f)))
            .forceDirt()
            .build();
    public static final ConfiguredFeature<TreeConfiguration, ?> FANCY_ESSENCE_TREE_FEATURE = FeatureUtils.register("essence_tree_fancy", Feature.TREE.configured(FANCY_WORLD_ESSENCE_TREE_CONFIG));
    public static final PlacedFeature FANCY_ESSENCE_TREE_FEATURE_PLACED = PlacementUtils.register("essence_tree_fancy", FANCY_ESSENCE_TREE_FEATURE.placed());

}
