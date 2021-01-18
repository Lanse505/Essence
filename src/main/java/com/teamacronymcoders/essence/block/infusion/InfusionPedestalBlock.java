package com.teamacronymcoders.essence.block.infusion;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.util.EssenceBlockModels;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class InfusionPedestalBlock extends BasicTileBlock<InfusionPedestalTile> {

  public InfusionPedestalBlock (Properties properties) {
    super(properties, InfusionPedestalTile.class);
  }

  @Override
  public ActionResultType onBlockActivated (BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
    if (worldIn.isRemote) {
      return ActionResultType.SUCCESS;
    }
    InfusionPedestalTile te = worldIn.getTileEntity(pos) instanceof InfusionPedestalTile ? (InfusionPedestalTile) worldIn.getTileEntity(pos) : null;
    if (te != null) {
      ItemStack stack = player.getHeldItem(hand);
      if (!stack.isEmpty()) {
        if (te.getStack().isEmpty()) {
          ItemStack copy = stack.copy();
          stack.shrink(1);
          copy.setCount(1);
          te.addItem(copy);
        } else {
          ItemStack copy = te.getStack().copy();
          te.getStack().shrink(1);
          player.inventory.addItemStackToInventory(copy);
        }
        te.markComponentForUpdate(false);
      } else if (!te.getStack().isEmpty()) {
        ItemStack copy = te.getStack().copy();
        te.getStack().shrink(1);
        player.inventory.addItemStackToInventory(copy);
        te.markComponentForUpdate(false);
      }
      return ActionResultType.SUCCESS;
    }
    return ActionResultType.PASS;
  }

  @Override
  public IFactory<InfusionPedestalTile> getTileEntityFactory () {
    return InfusionPedestalTile::new;
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getShape (BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
    return EssenceBlockModels.INFUSION_PEDESTAL[0];
  }

  @Nonnull
  @Override
  public VoxelShape getCollisionShape (BlockState state, IBlockReader world, BlockPos pos, ISelectionContext selectionContext) {
    return EssenceBlockModels.INFUSION_PEDESTAL[0];
  }

  @Override
  public List<VoxelShape> getBoundingBoxes (BlockState state, IBlockReader source, BlockPos pos) {
    return Collections.singletonList(EssenceBlockModels.INFUSION_PEDESTAL[0]);
  }
}
