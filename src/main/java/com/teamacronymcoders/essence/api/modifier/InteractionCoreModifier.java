package com.teamacronymcoders.essence.api.modifier;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class InteractionCoreModifier extends CoreModifier {

    public InteractionCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    public ActionResultType onItemUse(ItemUseContext context, ModifierInstance instance) {
        return ActionResultType.PASS;
    }

    public boolean onHitEntity(ItemStack stack, LivingEntity entity, LivingEntity player, ModifierInstance instance) {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
        return false;
    }

    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
    }

    public List<ItemStack> onSheared(ItemStack stack, @Nullable PlayerEntity player, LivingEntity sheared, Hand hand, List<ItemStack> stackList, ModifierInstance instance) {
        return stackList;
    }
}
