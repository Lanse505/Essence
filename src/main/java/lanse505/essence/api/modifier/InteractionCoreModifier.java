package lanse505.essence.api.modifier;

import lanse505.essence.api.modifier.core.BaseModifier;
import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InteractionCoreModifier extends CoreModifier {

    /**
     * This returns an InteractionCoreModifier Object.
     * This object is used for handling the same stuff as CoreModifier but also for handling things like:
     * onItemUse (Right-click - Item on Block Behaviour)
     * onHitEntity (Left-click - Item-attack Entity Behaviour)
     * onBlockDestroyed (Left-Click  - Item-Destroyed Block)
     * onInventoryTick (None-Click - Action-performed when the ItemStack ticks in the inventory)
     */
    public InteractionCoreModifier(ResourceLocation id, int maxLevel) {
        super(id, maxLevel);
    }

    /**
     * This returns an InteractionCoreModifier Object.
     * This grabs the currently active mod and inserts it as the domain.
     * This object is used for handling the same stuff as CoreModifier but also for handling things like:
     * onItemUse (Right-click - Item on Block Behaviour)
     * onHitEntity (Left-click - Item-attack Entity Behaviour)
     * onBlockDestroyed (Left-Click  - Item-Destroyed Block)
     * onInventoryTick (None-Click - Action-performed when the ItemStack ticks in the inventory)
     */
    public InteractionCoreModifier(String id, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), maxLevel);
    }

    /**
     * Called when this item is used when targetting a Block
     * @param context ItemUse Context
     * @return This is where you'd handle the use-functionality.
     */
    public ActionResultType onItemUse(ItemUseContext context, int level){
        return ActionResultType.PASS;
    }

    /**
     * As taken from Item#onHitEntity:
     *  Current implementations of this method in child classes do not use the entry argument beside ev.
     *  They just raise the damage on the stack.
     * @param stack The Tool-Stack
     * @param entity The LivingEntity being Hit
     * @param player The PlayerEntity that's attacking.
     * @return returns a dummy boolean, this is where things like increasing the damage done by the stack should be handled.
     */
    public boolean onHitEntity(ItemStack stack, LivingEntity entity, LivingEntity player, int level) {
        return false;
    }

    /**
     * Called when a Block is destroyed using this Item.
     * @param stack The Tool-Stack
     * @param world The World
     * @param state The Blockstate of the Block being broken.
     * @param pos The Position of the Block being broken.
     * @param miner The LivingEntity that mined the block.
     * @return Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {
        return false;
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update maps.
     * @param stack The Tool-Stack
     * @param world The World
     * @param entity The Entity with the Inventory holding the Item.
     * @param inventorySlot The integer for the InventorySlot holding the Item.
     * @param isCurrentItem If
     */
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, int level) {}

}
