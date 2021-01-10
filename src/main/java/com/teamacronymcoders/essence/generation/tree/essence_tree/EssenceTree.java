package com.teamacronymcoders.essence.generation.tree.essence_tree;

import com.teamacronymcoders.essence.util.registration.EssenceFeatures;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class EssenceTree extends Tree {
  @Nullable
  @Override
  protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature (Random random, boolean b) {
    return random.nextInt(10) >= 8 ? EssenceFeatures.NORMAL_ESSENCE_TREE_FEATURE : EssenceFeatures.FANCY_ESSENCE_TREE_FEATURE;
  }
}
