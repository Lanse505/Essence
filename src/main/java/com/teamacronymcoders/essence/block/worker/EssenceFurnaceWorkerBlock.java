package com.teamacronymcoders.essence.block.worker;

import com.hrznstudio.titanium.api.IFactory;
import com.teamacronymcoders.essence.block.worker.tile.EssenceFurnaceWorkerTile;
import com.teamacronymcoders.essence.util.tier.IEssenceBaseTier;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EssenceFurnaceWorkerBlock extends EssenceWorkerBlock<EssenceFurnaceWorkerTile> {

  public EssenceFurnaceWorkerBlock (IEssenceBaseTier tier) {
    super(tier, EssenceFurnaceWorkerTile.class);
  }

  @Override
  public void onBlockHarvested (World world, BlockPos pos, BlockState state, PlayerEntity player) {
    Optional<EssenceFurnaceWorkerTile> tile = getTile(world, pos);
    if (tile.isPresent()) {

    }
    super.onBlockHarvested(world, pos, state, player);
  }

  @Override
  public void onBlockPlacedBy (World world, BlockPos pos, BlockState state, @Nullable LivingEntity living, ItemStack stack) {

    super.onBlockPlacedBy(world, pos, state, living, stack);
  }

  @Override
  public IFactory<EssenceFurnaceWorkerTile> getTileEntityFactory () {
    return null;
  }

}
