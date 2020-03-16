package com.teamacronymcoders.essence.generation;

import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.utils.config.subconfigs.EssenceOreGenConfig;
import com.teamacronymcoders.essence.utils.config.subconfigs.EssenceTreeGenConfig;
import com.teamacronymcoders.essence.utils.helpers.EssenceWorldHelper;
import com.teamacronymcoders.essence.utils.registration.EssenceFeatureRegistration;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Arrays;

public class EssenceGeneration {

    public static void addFeaturesToWorldGen() {
        EssenceOreGenConfig oreGenConfig = EssenceWorldGenConfig.getOreGenConfig();
        EssenceTreeGenConfig treeGenConfig = EssenceWorldGenConfig.getTreeGenConfig();
        if (oreGenConfig.getEssence_ore().getShouldGenerate().get()) {
            addOreGeneration(BiomeDictionary.Type.OVERWORLD, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, EssenceObjectHolders.ESSENCE_ORE.getDefaultState(), oreGenConfig.getEssence_ore().getMaxVeinSize().get()),
                new CountRangeConfig(
                    oreGenConfig.getEssence_ore().getPerChunk().get(),
                    oreGenConfig.getEssence_ore().getBottomOffset().get(),
                    oreGenConfig.getEssence_ore().getTopOffset().get(),
                    oreGenConfig.getEssence_ore().getMaxHeight().get()));
        }
        if (oreGenConfig.getEssence_crystal_ore().getShouldGenerate().get()) {
            addOreGeneration(BiomeDictionary.Type.OVERWORLD, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, EssenceObjectHolders.ESSENCE_CRYSTAL_ORE.getDefaultState(), oreGenConfig.getEssence_crystal_ore().getMaxVeinSize().get()),
                new CountRangeConfig(
                    oreGenConfig.getEssence_crystal_ore().getPerChunk().get(),
                    oreGenConfig.getEssence_crystal_ore().getBottomOffset().get(),
                    oreGenConfig.getEssence_crystal_ore().getTopOffset().get(),
                    oreGenConfig.getEssence_crystal_ore().getMaxHeight().get()));
        }
        if (treeGenConfig.getNormalVariant().getShouldGenerate().get()) {
            addTreeGeneration(BiomeDictionary.Type.OVERWORLD, EssenceFeatureRegistration.NORMAL_ESSENCE_TREE_FEATURE.get(), EssenceFeatureConfigs.NORMAL_WORLD_ESSENCE_TREE_CONFIG,
                new AtSurfaceWithExtraConfig(
                    0,
                    treeGenConfig.getNormalVariant().getExtraChance().get().floatValue(),
                    treeGenConfig.getNormalVariant().getExtraCount().get()));
        }
       if (treeGenConfig.getFancyVariant().getShouldGenerate().get()) {
           addTreeGeneration(BiomeDictionary.Type.OVERWORLD, EssenceFeatureRegistration.FANCY_ESSENCE_TREE_FEATURE.get(), EssenceFeatureConfigs.FANCY_WORLD_ESSENCE_TREE_CONFIG,
               new AtSurfaceWithExtraConfig(
                   0,
                   treeGenConfig.getFancyVariant().getExtraChance().get().floatValue(),
                   treeGenConfig.getFancyVariant().getExtraCount().get()));
       }
    }

    private static void addTreeGeneration(BiomeDictionary.Type type, Feature<TreeFeatureConfig> feature, TreeFeatureConfig config, AtSurfaceWithExtraConfig treeConfig, BiomeDictionary.Type... filteringTypes) {
        EssenceWorldHelper.getBiomes(type, Arrays.asList(filteringTypes)).forEach(biome -> biome.addFeature(
            GenerationStage.Decoration.VEGETAL_DECORATION,
            feature.withConfiguration(config).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(treeConfig))
        ));
    }

    private static void addOreGeneration(BiomeDictionary.Type type, OreFeatureConfig config, CountRangeConfig oreConfig, BiomeDictionary.Type... filteringTypes) {
        EssenceWorldHelper.getBiomes(type, Arrays.asList(filteringTypes)).forEach(biome -> biome.addFeature(
            GenerationStage.Decoration.UNDERGROUND_ORES,
            Feature.ORE.withConfiguration(config).withPlacement(Placement.COUNT_RANGE.configure(oreConfig))
        ));
    }
}
