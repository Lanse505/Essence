package lanse505.essence.core.generation.trees;

import lanse505.essence.core.generation.trees.feature.EssenceFeatureConfigs;
import lanse505.essence.utils.EssenceRegistration;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class EssenceTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean b) {
        return random.nextInt(10) >= 8 ? EssenceRegistration.NORMAL_ESSENCE_TREE_FEATURE.get().configure(EssenceFeatureConfigs.SAPLING_ESSENCE_TREE_CONFIG) : EssenceRegistration.FANCY_ESSENCE_TREE_FEATURE.get().configure(EssenceFeatureConfigs.SAPLING_ESSENCE_TREE_CONFIG);
    }
}
