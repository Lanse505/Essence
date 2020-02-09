package lanse505.essence.utils.generator;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;

/**
 *
 */
public interface IGenerator {
    int generate(int seedIncrement, long seed, GenerationStage.Decoration stage, IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, SharedSeedRandom rand, BlockPos pos);

    boolean canGenerate(IWorld world);
}
