package com.teamacronymcoders.essence.impl.generation;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.api.misc.IBlockProvider;
import com.teamacronymcoders.essence.api.misc.IOreGenConfig;
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

    public static void setupWorldGeneration() {
        //ESSENCE_ORE_FEATURE = getOreFeature(EssenceObjectHolders.ESSENCE_ORE, EssenceOreGenConfig.essenceOre, Feature.ORE);
    }

    @Nullable
    private static ConfiguredFeature<?, ?> getOreFeature(IBlockProvider blockProvider, IOreGenConfig oreConfig, Feature<OreFeatureConfig> feature) {
        if (oreConfig.getShouldGenerate()) {
            return feature.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                blockProvider.getBlock().getDefaultState(), oreConfig.getMaxVeinSize())).withPlacement(Placement.COUNT_RANGE.configure(
                new CountRangeConfig(oreConfig.getChanceToGenerate(), oreConfig.getBottomOffset(), oreConfig.getTopOffset(), oreConfig.getMaxHeight())));
        }
        return null;
    }
}

