package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class InfusionTableBlock extends BasicTileBlock<InfusionTableTile> {
    public InfusionTableBlock() {
        super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.5F), InfusionTableTile.class);
        setRegistryName(new ResourceLocation(Essence.MODID, "essence_infusion_table"));
        setItemGroup(Essence.CORE_TAB);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
        InfusionTableTile te = worldIn.getTileEntity(pos) instanceof InfusionTableTile ? (InfusionTableTile) worldIn.getTileEntity(pos) : null;
        if (te == null) {
            return ActionResultType.PASS;
        }
        te.openGui(player);
        return ActionResultType.SUCCESS;
    }

    @Override
    public IFactory<InfusionTableTile> getTileEntityFactory() {
        return InfusionTableTile::new;
    }
}
