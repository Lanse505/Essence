package com.teamacronymcoders.essence.common.world.generation.tree.essence_tree;

import com.teamacronymcoders.essence.common.world.generation.tree.EssenceTreeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class EssenceTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredFeature(Random random, boolean b) {
        return random.nextInt(10) >= 8 ? EssenceTreeFeatures.NORMAL_ESSENCE_TREE_FEATURE : EssenceTreeFeatures.FANCY_ESSENCE_TREE_FEATURE;
    }
}
