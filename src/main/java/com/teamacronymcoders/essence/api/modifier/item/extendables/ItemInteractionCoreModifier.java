package com.teamacronymcoders.essence.api.modifier_new.item.extendables;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class ItemInteractionCoreModifier extends ItemAttributeModifier {

    public ItemInteractionCoreModifier() {
        super(1);
    }

    public ItemInteractionCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    public ItemInteractionCoreModifier(int maxLevel, int minLevel) {
        super(maxLevel, minLevel);
    }

    public ItemInteractionCoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, operation);
    }

    public ItemInteractionCoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, maxLevel, operation);
    }

    public ItemInteractionCoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, int maxLevel, int minLevel, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, maxLevel, minLevel, operation);
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
