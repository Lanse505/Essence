package com.teamacronymcoders.essence.api.modifier.block;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public abstract class BlockCoreModifier extends Modifier<Block> {

    public BlockCoreModifier() {
        super(Block.class);
    }

    public BlockCoreModifier(int maxLevel) {
        super(Block.class, maxLevel);
    }

    public BlockCoreModifier(int maxLevel, int minLevel) {
        super(Block.class, maxLevel, minLevel);
    }

    public float getModifiedBlockHardness(BlockState state, IBlockReader world, BlockPos pos) {
        return 0f;
    }

    public float getModifiedExplosionResistance(BlockState state, IBlockReader reader, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return 0f;
    }

    public int getModifiedLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 0;
    }

}
