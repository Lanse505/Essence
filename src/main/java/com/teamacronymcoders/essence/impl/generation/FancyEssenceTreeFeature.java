package com.teamacronymcoders.essence.impl.generation;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.gen.feature.FancyTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.function.Function;

public class FancyEssenceTreeFeature extends FancyTreeFeature {

    public FancyEssenceTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> dynamicFunction) {
        super(dynamicFunction);
    }
}
