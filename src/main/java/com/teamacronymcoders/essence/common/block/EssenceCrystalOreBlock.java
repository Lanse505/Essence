package com.teamacronymcoders.essence.common.block;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EssenceCrystalOreBlock extends Block {

    public EssenceCrystalOreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader world, RandomSource randomSource, BlockPos pos, int fortune, int silktouch) {
        return silktouch <= 0 ? Mth.m_216271_(randomSource, 1, 5) : 0;
    }

}
