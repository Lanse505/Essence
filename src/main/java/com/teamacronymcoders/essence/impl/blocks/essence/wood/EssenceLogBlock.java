package com.teamacronymcoders.essence.impl.blocks.essence.wood;

import com.teamacronymcoders.essence.base.CustomRotatedPillarBlock;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EssenceLogBlock extends CustomRotatedPillarBlock {
    public EssenceLogBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
        setRegistryName(EssenceReferences.MODID, "essence_wood_log");
        setItemGroup(EssenceReferences.CORE_TAB);
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
