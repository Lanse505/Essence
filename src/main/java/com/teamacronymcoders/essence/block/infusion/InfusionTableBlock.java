package com.teamacronymcoders.essence.block.infusion;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableTile;
import com.teamacronymcoders.essence.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.util.EssenceBlockModels;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class InfusionTableBlock extends BasicTileBlock<InfusionTableTile> {

  public InfusionTableBlock (Properties properties) {
    super(properties, InfusionTableTile.class);
  }

  @Override
  public ActionResultType onBlockActivated (BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
    if (worldIn.isRemote) {
      return ActionResultType.SUCCESS;
    }
    InfusionTableTile te = worldIn.getTileEntity(pos) instanceof InfusionTableTile ? (InfusionTableTile) worldIn.getTileEntity(pos) : null;
    if (te != null) {
      ItemStack stack = player.getHeldItem(hand);

      if (!stack.isEmpty() && stack.getItem() instanceof EssenceWrench) {
        EssenceWrench wrench = (EssenceWrench) stack.getItem();
        if (wrench.getMode() == WrenchModeEnum.TRIGGER) {
          te.setShouldBeWorking(true);
        }
        return ActionResultType.SUCCESS;
      }

      // Handle Player Inventory -> Block Inventory
      if (!stack.isEmpty() && stack.getItem() instanceof TomeOfKnowledgeItem && !te.hasTome()) {
        ItemStack copy = stack.copy();
        te.getTome().setStackInSlot(0, copy);
        stack.shrink(1);
        te.markComponentForUpdate(false);
        return ActionResultType.SUCCESS;
      } else if (!stack.isEmpty() && te.getInfusable().getStackInSlot(0).isEmpty()) {
        ItemStack copy = stack.copy();
        te.getInfusable().setStackInSlot(0, copy);
        stack.shrink(1);
        te.markComponentForUpdate(false);
        return ActionResultType.SUCCESS;
      }

      // Handle Block Inventory -> Player Inventory
      if (stack.isEmpty() && te.hasTome()) {
        ItemStack copy = te.getTome().getStackInSlot(0);
        player.addItemStackToInventory(copy);
        te.getTome().setStackInSlot(0, ItemStack.EMPTY);
        te.markComponentForUpdate(false);
        return ActionResultType.SUCCESS;
      } else if (stack.isEmpty() && !te.getInfusable().getStackInSlot(0).isEmpty()) {
        ItemStack copy = te.getInfusable().getStackInSlot(0).copy();
        player.addItemStackToInventory(copy);
        te.getInfusable().setStackInSlot(0, ItemStack.EMPTY);
        te.markComponentForUpdate(false);
        return ActionResultType.SUCCESS;
      }
    }
    return ActionResultType.PASS;
  }

  @Override
  public IFactory<InfusionTableTile> getTileEntityFactory () {
    return InfusionTableTile::new;
  }

  @Override
  public void animateTick (BlockState state, World world, BlockPos pos, Random random) {
    super.animateTick(state, world, pos, random);
    InfusionTableTile te = world.getTileEntity(pos) instanceof InfusionTableTile ? (InfusionTableTile) world.getTileEntity(pos) : null;
    if (te != null && te.getWorking()) {
      for (int i = -2; i <= 2; ++i) {
        for (int j = -2; j <= 2; ++j) {
          if (i > -2 && i < 2 && j == -1) {
            j = 2;
          }
          if (random.nextInt(16) == 0) {
            for (int k = 0; k <= 1; ++k) {
              if (!world.isAirBlock(pos.add(i / 2, 0, j / 2))) {
                break;
              }
              world.addParticle(ParticleTypes.ENCHANT, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.0D, (double) pos.getZ() + 0.5D, (double) ((float) i + random.nextFloat()) - 0.5D, (float) k - random.nextFloat() - 1.0F, (double) ((float) j + random.nextFloat()) - 0.5D);
            }
          }
        }
      }
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  @ParametersAreNonnullByDefault
  public BlockRenderType getRenderType (BlockState state) {
    return BlockRenderType.MODEL;
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getShape (BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
    return EssenceBlockModels.INFUSION_TABLE[0];
  }

  @Nonnull
  @Override
  public VoxelShape getCollisionShape (BlockState state, IBlockReader world, BlockPos pos, ISelectionContext selectionContext) {
    return EssenceBlockModels.INFUSION_TABLE[0];
  }

  @Override
  public List<VoxelShape> getBoundingBoxes (BlockState state, IBlockReader source, BlockPos pos) {
    return Collections.singletonList(EssenceBlockModels.INFUSION_TABLE[0]);
  }
}
