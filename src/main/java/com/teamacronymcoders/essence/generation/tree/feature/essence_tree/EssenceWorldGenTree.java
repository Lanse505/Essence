package com.teamacronymcoders.essence.generation.tree.feature.essence_tree;

import com.teamacronymcoders.essence.generation.EssenceFeatureConfigs;
import com.teamacronymcoders.essence.utils.registration.EssenceFeatureRegistration;
import com.teamacronymcoders.essence.utils.registration.EssenceModifierRegistration;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class EssenceWorldGenTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return random.nextInt(10) >= 8 ? EssenceFeatureRegistration.NORMAL_ESSENCE_TREE_FEATURE.get().withConfiguration(EssenceFeatureConfigs.WORLD_ESSENCE_TREE_CONFIG) : EssenceFeatureRegistration.FANCY_ESSENCE_TREE_FEATURE.get().withConfiguration(EssenceFeatureConfigs.WORLD_ESSENCE_TREE_CONFIG);
    }
}
