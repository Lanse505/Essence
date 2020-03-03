package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.utils.helpers.EssenceVoxelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
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

    public static VoxelShape shape;
    public static List<VoxelShape> shapes;

    private static final VoxelShape[] bounds = new VoxelShape[] {
        EssenceVoxelHelper.combine(
            makeCuboidShape(4, 0, 4, 12, 2, 12),
            makeCuboidShape(5, 2, 5, 11, 12, 11),
            makeCuboidShape(3, 12, 3, 13, 13, 13)
        )
    };

    public InfusionPedestalBlock() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3).sound(SoundType.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid(), InfusionPedestalTile.class);
        setRegistryName(new ResourceLocation(Essence.MODID, "essence_infusion_pedestal"));
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
                    stack.shrink(1);
                    ItemStack copy = stack.copy();
                    copy.setCount(1);
                    te.addItem(copy);
                    te.markComponentForUpdate();
                    return ActionResultType.SUCCESS;
                }
                if (!te.getStack().isEmpty()) {
                    player.inventory.addItemStackToInventory(te.getStack().copy());
                    te.getStack().shrink(1);
                    te.markComponentForUpdate();
                }
                return ActionResultType.SUCCESS;
            }
            if (!te.getStack().isEmpty()) {
                player.inventory.addItemStackToInventory(te.getStack().copy());
                te.getStack().shrink(1);
                te.markComponentForUpdate();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public IFactory<InfusionPedestalTile> getTileEntityFactory() {
        return InfusionPedestalTile::new;
    }

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
