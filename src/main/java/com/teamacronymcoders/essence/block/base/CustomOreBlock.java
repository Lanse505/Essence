package com.teamacronymcoders.essence.block.base;

import com.hrznstudio.titanium.block.BasicBlock;
import com.teamacronymcoders.essence.Essence;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class CustomOreBlock extends BasicBlock {

  public CustomOreBlock(Properties properties) {
    super(properties);
    setItemGroup(Essence.CORE_TAB);
  }

  protected int getExperience(Random random) {
    return 0;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
    super.spawnAdditionalDrops(state, worldIn, pos, stack);
  }

  @Override
  public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
    return silktouch == 0 ? this.getExperience(RANDOM) : 0;
  }
}
