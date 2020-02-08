package lanse505.essence.core.generation.trees.feature;

import com.mojang.datafixers.Dynamic;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.function.Function;

public class NormalEssenceTreeFeature extends TreeFeature {
    public NormalEssenceTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> dynamicFunction) {
        super(dynamicFunction);
    }
}
