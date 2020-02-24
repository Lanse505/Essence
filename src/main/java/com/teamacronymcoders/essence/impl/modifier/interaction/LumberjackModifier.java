package com.teamacronymcoders.essence.impl.modifier.interaction;

import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LumberjackModifier extends InteractionCoreModifier {

    public LumberjackModifier() {
        super(1);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {

        return super.onBlockDestroyed(stack, world, state, pos, miner, level);
    }
}
