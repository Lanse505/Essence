package com.teamacronymcoders.essence.world.generation.tree.essence_tree;

import com.teamacronymcoders.essence.world.generation.tree.EssenceTreeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

public class EssenceSaplingTree extends AbstractTreeGrower {

  @org.jetbrains.annotations.Nullable
  @Override
  protected ConfiguredFeature<?, ?> getConfiguredFeature(Random random, boolean largeHive) {
    return random.nextInt(10) >= 8 ? EssenceTreeFeatures.SAPLING_ESSENCE_TREE_FEATURE : EssenceTreeFeatures.FANCY_ESSENCE_TREE_FEATURE;
  }
}
