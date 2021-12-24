package com.teamacronymcoders.essence.block;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EssenceCrystalOreBlock extends OreBlock {

  public EssenceCrystalOreBlock(Properties properties) {
    super(properties);
  }

  @Override
  public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
    return silktouch <= 0 ? Mth.nextInt(Essence.RANDOM, 1, 5) : 0;
  }

}
