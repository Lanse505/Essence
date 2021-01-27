package com.teamacronymcoders.essence.api.modifier.block;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;

public abstract class BlockCoreModifier extends Modifier {

  public BlockCoreModifier() {
    super();
  }

  public BlockCoreModifier(int maxLevel) {
    super(maxLevel);
  }

  public BlockCoreModifier(int minLevel, int maxLevel) {
    super(minLevel, maxLevel);
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
