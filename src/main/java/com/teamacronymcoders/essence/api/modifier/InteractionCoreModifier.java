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

    /**
     * Called when this item is used when targetting a Block
     *
     * @param context ItemUse Context
     * @param level   The Level of the Modifier
     * @return This is where you'd handle the use-functionality.
     */
    public ActionResultType onItemUse(ItemUseContext context, int level) {
        return ActionResultType.PASS;
    }

    /**
     * As taken from Item#onHitEntity:
     * Current implementations of this method in child classes do not use the entry argument beside ev.
     * They just raise the damage on the stack.
     *
     * @param stack  The Tool-Stack
     * @param entity The LivingEntity being Hit
     * @param player The PlayerEntity that's attacking.
     * @param level  The Level of the Modifier
     * @return returns a dummy boolean, this is where things like increasing the damage done by the stack should be handled.
     */
    public boolean onHitEntity(ItemStack stack, LivingEntity entity, LivingEntity player, int level) {
        return false;
    }

    /**
     * Called when a Block is destroyed using this Item.
     *
     * @param stack The Tool-Stack
     * @param world The World
     * @param state The Blockstate of the Block being broken.
     * @param pos   The Position of the Block being broken.
     * @param miner The LivingEntity that mined the block.
     * @param level The Level of the Modifier
     * @return Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {
        return false;
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update maps.
     *
     * @param stack         The Tool-Stack
     * @param world         The World
     * @param entity        The Entity with the Inventory holding the Item.
     * @param inventorySlot The integer for the InventorySlot holding the Item.
     * @param isCurrentItem If the itemstack is the currently held Item.
     * @param level         The Level of the Modifier
     */
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, int level) {
    }


    /**
     * @param stack
     * @param player
     * @param sheared
     * @param hand
     * @param stackList
     * @param level
     * @return
     */
    public List<ItemStack> onSheared(ItemStack stack, @Nullable PlayerEntity player, LivingEntity sheared, Hand hand, List<ItemStack> stackList, int level) {
        return stackList;
    }
}
