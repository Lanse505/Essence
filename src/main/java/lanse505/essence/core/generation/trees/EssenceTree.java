package lanse505.essence.core.generation.trees;

import lanse505.essence.core.generation.trees.feature.EssenceTreeFeature;
import net.minecraft.block.trees.OakTree;

public class EssenceTree extends OakTree {
    public static EssenceTreeFeature feature = null;

    public EssenceTree() {
        this.feature = new EssenceTreeFeature();
    }

    public static EssenceTreeFeature getFeature() {
        return feature;
    }
}
