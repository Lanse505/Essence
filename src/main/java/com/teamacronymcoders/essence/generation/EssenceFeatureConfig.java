package com.teamacronymcoders.essence.generation;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import java.util.OptionalInt;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class EssenceFeatureConfig {
  public static final BaseTreeFeatureConfig SAPLING_ESSENCE_TREE_CONFIG = new BaseTreeFeatureConfig.Builder(
          new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()),
          new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()),
          new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
          new StraightTrunkPlacer(4, 2, 0),
          new TwoLayerFeature(1, 0, 1))
          .setIgnoreVines()
          .build();

  public static final BaseTreeFeatureConfig NORMAL_WORLD_ESSENCE_TREE_CONFIG = new BaseTreeFeatureConfig.Builder(
          new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()),
          new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()),
          new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
          new StraightTrunkPlacer(4, 2, 0),
          new TwoLayerFeature(1, 0, 1))
          .setDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.0755f)))
          .setMaxWaterDepth(0)
          .setIgnoreVines()
          .build();

  public static final BaseTreeFeatureConfig FANCY_WORLD_ESSENCE_TREE_CONFIG = new BaseTreeFeatureConfig.Builder(
          new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LOG.getDefaultState()),
          new SimpleBlockStateProvider(EssenceObjectHolders.ESSENCE_WOOD_LEAVES.getDefaultState()),
          new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4),
          new FancyTrunkPlacer(3, 11, 0),
          new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))
          .setIgnoreVines()
          .func_236702_a_(Type.MOTION_BLOCKING)
          .setDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.0755f)))
          .setMaxWaterDepth(0)
          .build();
}

