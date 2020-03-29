package com.teamacronymcoders.essence.blocks.infusion;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.blocks.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.utils.helpers.EssenceVoxelHelper;
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

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class InfusionPedestalBlock extends BasicTileBlock<InfusionPedestalTile> {

    private static final VoxelShape[] bounds = new VoxelShape[]{
        EssenceVoxelHelper.combine(
            makeCuboidShape(5.2, 14, 5.2, 10.8, 14.2, 10.8),
            makeCuboidShape(11, 13, 8, 12, 14, 10),
            makeCuboidShape(11, 13, 6, 12, 14, 8),
            makeCuboidShape(10, 13, 5, 11, 14, 6),
            makeCuboidShape(8, 13, 4, 10, 14, 5),
            makeCuboidShape(6, 13, 4, 8, 14, 5),
            makeCuboidShape(5, 13, 5, 6, 14, 6),
            makeCuboidShape(4, 13, 6, 5, 14, 8),
            makeCuboidShape(4, 13, 8, 5, 14, 10),
            makeCuboidShape(5, 13, 10, 6, 14, 11),
            makeCuboidShape(6, 13, 11, 8, 14, 12),
            makeCuboidShape(8, 13, 11, 10, 14, 12),
            makeCuboidShape(5, 13, 6, 6, 14, 10),
            makeCuboidShape(10, 13, 10, 11, 14, 11),
            makeCuboidShape(6, 13, 5, 10, 14, 11),
            makeCuboidShape(10, 13, 6, 11, 14, 10),
            makeCuboidShape(5, 0, 5, 11, 1, 11),
            makeCuboidShape(6, 0, 4, 10, 1, 5),
            makeCuboidShape(6, 0, 11, 10, 1, 12),
            makeCuboidShape(7, 0, 12, 9, 0.5, 14),
            makeCuboidShape(12, 0, 7, 14, 0.5, 9),
            makeCuboidShape(2, 0, 7, 4, 0.5, 9),
            makeCuboidShape(7, 0, 2, 9, 0.5, 4),
            makeCuboidShape(4, 0, 6, 5, 1, 10),
            makeCuboidShape(11, 0, 6, 12, 1, 10),
            makeCuboidShape(11, 12, 6, 12, 13, 8),
            makeCuboidShape(10, 1, 5, 11, 2, 6),
            makeCuboidShape(5, 7, 10, 6, 8, 11),
            makeCuboidShape(10, 10, 10, 11, 11, 11),
            makeCuboidShape(8, 2, 4, 10, 3, 5),
            makeCuboidShape(6, 3, 4, 8, 4, 5),
            makeCuboidShape(6, 8, 11, 8, 9, 12),
            makeCuboidShape(8, 9, 11, 10, 10, 12),
            makeCuboidShape(5, 4, 5, 6, 5, 6),
            makeCuboidShape(4, 5, 6, 5, 6,8),
            makeCuboidShape(4,6, 8, 5, 7, 10),
            makeCuboidShape(11, 11, 8, 12, 12, 10),
            makeCuboidShape(5, 1, 10, 6, 2, 11),
            makeCuboidShape(10, 4, 10, 11, 5, 11),
            makeCuboidShape(10, 7, 5, 11, 8, 6),
            makeCuboidShape(8, 8, 4, 10, 9, 5),
            makeCuboidShape(6, 9, 4, 8, 10, 5),
            makeCuboidShape(5, 10, 5, 6, 11, 6),
            makeCuboidShape(4, 11, 6, 5, 12, 8),
            makeCuboidShape(4, 12, 8, 5, 13, 10),
            makeCuboidShape(11, 5, 8, 12, 6, 10),
            makeCuboidShape(11, 6, 6, 12, 7, 8),
            makeCuboidShape(6, 2, 11, 8, 3, 12),
            makeCuboidShape(8, 3, 11, 10, 4, 12),
            makeCuboidShape(6, 0.1, 6, 10, 13, 10),
            makeCuboidShape(6.5, 1, 5.75, 9.5, 13, 6),
            makeCuboidShape(6.5, 1, 10, 9.5, 13, 10.25),
            makeCuboidShape(5.75, 1, 6.5, 6, 13, 9.5),
            makeCuboidShape(10, 1, 6.5, 10.25, 13, 9.5)
        )
    };

    public InfusionPedestalBlock() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2).notSolid(), InfusionPedestalTile.class);
        setItemGroup(Essence.CORE_TAB);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
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
                te.markComponentForUpdate();
            } else if (!te.getStack().isEmpty()) {
                ItemStack copy = te.getStack().copy();
                te.getStack().shrink(1);
                player.inventory.addItemStackToInventory(copy);
                te.markComponentForUpdate();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public IFactory<InfusionPedestalTile> getTileEntityFactory() {
        return InfusionPedestalTile::new;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return bounds[0];
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext selectionContext) {
        return bounds[0];
    }

    @Override
    public List<VoxelShape> getBoundingBoxes(BlockState state, IBlockReader source, BlockPos pos) {
        return Arrays.asList(bounds);
    }
}
