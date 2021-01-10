package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.generation.EssenceFeatureConfig;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class EssenceFeatures {
  // Feature RegistryObjects
  // Essence Tree
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SAPLING_ESSENCE_TREE_FEATURE = Feature.TREE.withConfiguration(EssenceFeatureConfig.SAPLING_ESSENCE_TREE_CONFIG);
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> NORMAL_ESSENCE_TREE_FEATURE = Feature.TREE.withConfiguration(EssenceFeatureConfig.NORMAL_WORLD_ESSENCE_TREE_CONFIG);
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_ESSENCE_TREE_FEATURE = Feature.TREE.withConfiguration(EssenceFeatureConfig.FANCY_WORLD_ESSENCE_TREE_CONFIG);
  // Essence Ore

}
