package com.teamacronymcoders.essence.generation.tree.essence_tree;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.function.Function;

public class NormalEssenceTreeFeature extends TreeFeature {
    public NormalEssenceTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> dynamicFunction) {
        super(dynamicFunction);
    }
}
