package com.teamacronymcoders.essence.world.generation.ore;

import com.google.common.collect.Lists;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;


import java.util.List;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

public class EssenceOreFeatures {
    public static final List<OreConfiguration.TargetBlockState> ORE_ESSENCE_TARGET_LIST =
            Lists.newArrayList(OreConfiguration.target(STONE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_ORE.getDefaultState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_ORE_DEEP_SLATE.getDefaultState()));
    public static final ConfiguredFeature<?, ?> ESSENCE_ORE = FeatureUtils.register("ore_essence", Feature.ORE.configured(new OreConfiguration(ORE_ESSENCE_TARGET_LIST, 9)));
    public static final ConfiguredFeature<?, ?> ESSENCE_ORE_SMALL = FeatureUtils.register("ore_essence_small", Feature.ORE.configured(new OreConfiguration(ORE_ESSENCE_TARGET_LIST, 4)));
    public static final PlacedFeature ORE_ESSENCE_UPPER = PlacementUtils.register("ore_essence_upper", ESSENCE_ORE.placed(OrePlacements.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)))));
    public static final PlacedFeature ORE_ESSENCE_MIDDLE = PlacementUtils.register("ore_essence_middle", ESSENCE_ORE.placed(OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature ORE_ESSENCE_SMALL = PlacementUtils.register("ore_essence_small", ESSENCE_ORE_SMALL.placed(OrePlacements.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)))));

    public static final List<OreConfiguration.TargetBlockState> ORE_ESSENCE_CRYSTAL_TARGET_LIST =
            Lists.newArrayList(OreConfiguration.target(STONE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_CRYSTAL_ORE.getDefaultState()));
    public static final ConfiguredFeature<?, ?> ESSENCE_CRYSTAL_ORE = FeatureUtils.register("ore_essence_crystal", Feature.ORE.configured(new OreConfiguration(ORE_ESSENCE_CRYSTAL_TARGET_LIST, 8)));
    public static final ConfiguredFeature<?, ?> ESSENCE_CRYSTAL_ORE_SMALL = FeatureUtils.register("ore_essence_crystal_small", Feature.ORE.configured(new OreConfiguration(ORE_ESSENCE_CRYSTAL_TARGET_LIST, 4)));
    public static final PlacedFeature ORE_ESSENCE_CRYSTAL_UPPER = PlacementUtils.register("ore_essence_crystal_upper", ESSENCE_CRYSTAL_ORE.placed(OrePlacements.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)))));
    public static final PlacedFeature ORE_ESSENCE_CRYSTAL_MIDDLE = PlacementUtils.register("ore_essence_crystal_middle", ESSENCE_CRYSTAL_ORE.placed(OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature ORE_ESSENCE_CRYSTAL_SMALL = PlacementUtils.register("ore_essence_crystal_small", ESSENCE_CRYSTAL_ORE_SMALL.placed(OrePlacements.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)))));
}
