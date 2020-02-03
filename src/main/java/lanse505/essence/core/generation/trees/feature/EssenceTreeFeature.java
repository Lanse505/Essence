package lanse505.essence.core.generation.trees.feature;

import com.mojang.datafixers.Dynamic;
import lanse505.essence.utils.EssenceReferences;
import lanse505.essence.utils.module.ModuleObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;

public class EssenceTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    public static final TreeFeatureConfig CONFIG = new TreeFeatureConfig.Builder(null, null, null).build();

    public EssenceTreeFeature() {
        super(dynamic -> CONFIG);
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_tree_feature"));
    }

    @Override
    protected boolean func_225557_a_(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> cb1, Set<BlockPos> cb2, MutableBoundingBox box, TreeFeatureConfig config) {
        int height = random.nextInt(3) + 5;
        BlockPos trunkTop = pos.up(height);

        this.setBlockState(world, pos, Blocks.AIR.getDefaultState());

        //Trunk
        for (int x = 0; x <= 1; x++) {
            for (int z = 0; z <= 1; z++) {
                for (int i = height - (x + z) * (random.nextInt(2) + 2); i >= 0; i--) {
                    BlockPos goal = pos.add(x, i, z);
                    if (func_214587_a(world, goal)) {
                        this.setBlockState(world, goal, ModuleObjects.ESSENCE_LOG.getDefaultState().with(LogBlock.AXIS, Direction.Axis.Y));
                        cb1.add(goal);
                    }
                }
            }
        }
        this.makeLeaves(cb2, world, trunkTop.up(random.nextInt(2) - 1), ModuleObjects.ESSENCE_LEAVES.getDefaultState(), random.nextInt(2) + 3, random);

        //Branches
        int branchAmount = random.nextInt(3) + 4;
        for (int i = 0; i < branchAmount; i++) {
            int length = random.nextInt(2) + 3;
            float angle = 2F * (float) Math.PI * (i / (float) branchAmount);
            float x = (float) Math.sin(angle) * length;
            float z = (float) Math.cos(angle) * length;

            BlockPos goal = trunkTop.add(x, random.nextInt(3) + 1, z);
            this.makeBranch(cb1, world, trunkTop, goal, ModuleObjects.ESSENCE_LOG.getDefaultState(), true);
            this.makeLeaves(cb2, world, goal, ModuleObjects.ESSENCE_LOG.getDefaultState(), random.nextInt(2) + 2, random);
        }

        return true;
    }

    private void makeBranch(Set changedBlocks, IWorldGenerationReader world, BlockPos first, BlockPos second, BlockState state, boolean hasAxis) {
        BlockPos pos = second.add(-first.getX(), -first.getY(), -first.getZ());
        int length = this.getHighestCoord(pos);
        float stepX = (float) pos.getX() / (float) length;
        float stepY = (float) pos.getY() / (float) length;
        float stepZ = (float) pos.getZ() / (float) length;

        for (int i = 0; i <= length; i++) {
            BlockPos goal = first.add(0.5F + i * stepX, 0.5F + i * stepY, 0.5F + i * stepZ);
            if (func_214587_a(world, goal)) {
                if (hasAxis) {
                    Direction.Axis axis = this.getLogAxis(first, goal);
                    this.setBlockState(world, goal, state.with(LogBlock.AXIS, axis));
                } else {
                    this.setBlockState(world, goal, state);
                }
                changedBlocks.add(goal);
            }
        }
    }

    private void makeLeaves(Set changedBlocks, IWorldGenerationReader world, BlockPos pos, BlockState state, int radius, Random rand) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos goal = pos.add(x, y, z);
                    if (pos.distanceSq(goal) <= radius * radius + rand.nextInt(3) - 1) {
                        if (isAirOrLeaves(world, goal)) {
                            if (world.hasBlockState(goal, st -> {
                                Block block = st.getBlock();
                                return !(block instanceof LogBlock) && block != Blocks.DIRT && block != Blocks.GRASS;
                            })) {
                                this.setBlockState(world, goal, state);
                                changedBlocks.add(goal);
                            }
                        }
                    }
                }
            }
        }
    }

    private int getHighestCoord(BlockPos pos) {
        return Math.max(MathHelper.abs(pos.getX()), Math.max(MathHelper.abs(pos.getY()), MathHelper.abs(pos.getZ())));
    }

    private Direction.Axis getLogAxis(BlockPos pos, BlockPos goal) {
        Direction.Axis axis = Direction.Axis.Y;
        int x = Math.abs(goal.getX() - pos.getX());
        int y = Math.abs(goal.getZ() - pos.getZ());
        int highest = Math.max(x, y);
        if (highest > 0) {
            if (x == highest) {
                axis = Direction.Axis.X;
            } else if (y == highest) {
                axis = Direction.Axis.Z;
            }
        }
        return axis;
    }
}
