package com.teamacronymcoders.essence.block.infusion;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionPedestalBlockEntity;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.util.EssenceBlockModels;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class InfusionPedestalBlock extends BasicTileBlock<InfusionPedestalBlockEntity> {

  public InfusionPedestalBlock(BlockBehaviour.Properties properties) {
    super("infusion_pedestal", properties, InfusionPedestalBlockEntity.class);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    }
    InfusionPedestalBlockEntity te = level.getBlockEntity(pos) instanceof InfusionPedestalBlockEntity ? (InfusionPedestalBlockEntity) level.getBlockEntity(pos) : null;
    if (te != null) {
      ItemStack stack = player.getItemInHand(hand);
      if (!stack.isEmpty()) {
        if (te.getStack().isEmpty()) {
          ItemStack copy = stack.copy();
          stack.shrink(1);
          copy.setCount(1);
          te.addItem(copy);
        } else {
          ItemStack copy = te.getStack().copy();
          te.getStack().shrink(1);
          player.getInventory().add(copy);
        }
        te.markComponentForUpdate(false);
      } else if (!te.getStack().isEmpty()) {
        ItemStack copy = te.getStack().copy();
        te.getStack().shrink(1);
        player.getInventory().add(copy);
        te.markComponentForUpdate(false);
      }
      return InteractionResult.SUCCESS;
    }
    return InteractionResult.PASS;
  }

  @Override
  public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
    return InfusionPedestalBlockEntity::new;
  };

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
    return EssenceBlockModels.INFUSION_PEDESTAL[0];
  }

  @Nonnull
  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext selectionContext) {
    return EssenceBlockModels.INFUSION_PEDESTAL[0];
  }

  @Override
  public List<VoxelShape> getBoundingBoxes(BlockState state, BlockGetter getter, BlockPos pos) {
    return Collections.singletonList(EssenceBlockModels.INFUSION_PEDESTAL[0]);
  }

  @Override
  public BlockEntityType getTileEntityType() {
    return EssenceBlockRegistrate.INFUSION_TABLE_TILE.get();
  }
}
