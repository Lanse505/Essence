package com.teamacronymcoders.essence.modifier.item.interaction;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.items.tools.EssencePickaxe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrindrModifier extends ItemInteractionCoreModifier {

    public GrindrModifier() {
        super(2);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance<ItemStack> instance) {

        return super.onBlockDestroyed(stack, world, state, pos, miner, instance);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return object.getItem() instanceof EssencePickaxe;
    }
}
