package lanse505.essence.core.generation.trees.generator;

import lanse505.essence.core.generation.trees.EssenceTree;
import lanse505.essence.core.items.essence.EssenceSapling;
import lanse505.essence.utils.generator.Generator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;

import java.util.Random;

public class EssenceTreeGenerator extends Generator {

    public EssenceTreeGenerator() {
        super(() -> true);
    }

    @Override
    public void generateChunk(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos) {
        BlockPos placePos = pos.add(rand.nextInt(16), 0, rand.nextInt(16));
        placePos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, placePos).down();
        BlockState state = worldIn.getBlockState(placePos);
        if(state.getBlock().canSustainPlant(state, worldIn, pos, Direction.UP, (SaplingBlock) Blocks.OAK_SAPLING)) {
            EssenceTree.feature.place(worldIn, generator, rand, placePos.up(), DefaultBiomeFeatures.field_226739_a_);
        }
    }
}
