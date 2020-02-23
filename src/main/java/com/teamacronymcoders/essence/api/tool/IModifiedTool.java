package com.teamacronymcoders.essence.api.tool;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IModifiedTool {
    ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive);
    boolean onBlockDestroyedModified(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, boolean isRecursive);
}
