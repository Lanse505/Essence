package lanse505.essence.core.generation;

import lanse505.essence.Essence;
import lanse505.essence.utils.generator.ChunkCornerPlacement;
import lanse505.essence.utils.generator.DeferedFeature;
import lanse505.essence.utils.generator.IGenerator;
import lanse505.essence.utils.generator.WeightedGenerator;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class WorldGenHandler {
    private static Map<GenerationStage.Decoration, SortedSet<WeightedGenerator>> generators = new HashMap<>();

    public static void addGenerator(IGenerator generator, GenerationStage.Decoration stage, int weight) {
        WeightedGenerator weighted = new WeightedGenerator(generator, weight);
        if(!generators.containsKey(stage)) {
            generators.put(stage, new TreeSet<>());
        }
        generators.get(stage).add(weighted);
    }

    public static void loadComplete() {
        for(GenerationStage.Decoration stage : GenerationStage.Decoration.values()) {
            ConfiguredFeature<?, ?> feature = new DeferedFeature(stage).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(new ChunkCornerPlacement().func_227446_a_(NoPlacementConfig.NO_PLACEMENT_CONFIG));
            ForgeRegistries.BIOMES.forEach(biome -> {
                biome.addFeature(stage, feature);
            });

        }
    }

    public static void generateChunk(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, BlockPos pos, GenerationStage.Decoration stage) {
        if(!(worldIn instanceof WorldGenRegion))
            return;

        WorldGenRegion region = (WorldGenRegion) worldIn;
        SharedSeedRandom random = new SharedSeedRandom();
        long seed = random.setDecorationSeed(region.getSeed(), region.getMainChunkX() * 16, region.getMainChunkZ() * 16);
        int stageNum = stage.ordinal() * 10000;

        if(generators.containsKey(stage)) {
            SortedSet<WeightedGenerator> set = generators.get(stage);

            for(WeightedGenerator wgen : set) {
                IGenerator gen = wgen.generator;
                stageNum = gen.generate(stageNum, seed, stage, worldIn, generator, random, pos);
            }
        }
    }
}
