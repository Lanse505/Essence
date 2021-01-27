package com.teamacronymcoders.essence.block.wood;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EssenceSlabBlock extends SlabBlock {

  public EssenceSlabBlock(Properties properties) {
    super(properties);
  }

  @Override
  public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    return true;
  }

  @Override
  public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    return 75;
  }

}
