package lanse505.essence.core.generation.trees.feature;

import lanse505.essence.utils.module.ModuleObjects;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

public class EssenceFeatureConfigs {
    public static final TreeFeatureConfig ESSENCE_TREE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModuleObjects.ESSENCE_LOG.getDefaultState()), new SimpleBlockStateProvider(ModuleObjects.ESSENCE_LEAVES.getDefaultState()), new BlobFoliagePlacer(5, 0)).setSapling(ModuleObjects.ESSENCE_SAPLING).build();
}
