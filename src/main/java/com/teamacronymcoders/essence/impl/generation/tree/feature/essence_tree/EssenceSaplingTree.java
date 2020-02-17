package com.teamacronymcoders.essence.impl.generation.tree.feature.essence_tree;

import com.teamacronymcoders.essence.impl.generation.EssenceFeatureConfigs;
import com.teamacronymcoders.essence.utils.EssenceRegistration;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class EssenceSaplingTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean b) {
        return random.nextInt(10) >= 8 ? EssenceRegistration.NORMAL_ESSENCE_TREE_FEATURE.get().configure(EssenceFeatureConfigs.SAPLING_ESSENCE_TREE_CONFIG) : EssenceRegistration.FANCY_ESSENCE_TREE_FEATURE.get().configure(EssenceFeatureConfigs.SAPLING_ESSENCE_TREE_CONFIG);
    }
}