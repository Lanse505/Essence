package com.teamacronymcoders.essenceapi.modifier.item;

import com.teamacronymcoders.essenceapi.modifier.ModifierInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class ItemInteractionModifier extends ItemCoreModifier {

    public ItemInteractionModifier() {
        this(0, 1);
    }

    public ItemInteractionModifier(int minLevel) {
        this(minLevel, 1);
    }

    public ItemInteractionModifier(int minLevel, int maxLevel) {
        super(minLevel, maxLevel);
    }

    public ItemInteractionModifier(Attribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
        this(attribute, identifier, uuid, amount, 0, 1, operation);
    }

    public ItemInteractionModifier(Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
        this(attribute, identifier, uuid, amount, 0, maxLevel, operation);
    }

    public ItemInteractionModifier(Attribute attribute, String identifier, UUID uuid, double amount, int minLevel, int maxLevel, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, minLevel, maxLevel, operation);
    }

    /**
     * Used to modify "useOn" method behaviour.
     *
     * @param context  The provided UseOnContext.
     * @param instance The current instance of the Modifier.
     * @return Returns the InteractionResult of the method.
     */
    public InteractionResult useOn(UseOnContext context, ModifierInstance instance) {
        return InteractionResult.PASS;
    }

    /**
     * Used to modify "hurtEnemy" method behaviour.
     *
     * @param stack    The currently held stack.
     * @param attacked The attacked entity.
     * @param attacker The attacking entity.
     * @param instance The current instance of the com.teamacronymcoders.essenceapi.modifier.
     * @return Returns true if it successfully hurt the attacked entity.
     */
    public boolean hurtEnemy(ItemStack stack, LivingEntity attacked, LivingEntity attacker, ModifierInstance instance) {
        return false;
    }

    /**
     * Used to modify "mineBlock" method behaviour.
     *
     * @param stack    The currently held stack.
     * @param level    The Level where the mining is occurring.
     * @param state    The BlockState of the block being mined.
     * @param pos      The BlockPos where the mined block is.
     * @param miner    The LivingEntity miner.
     * @param instance The current instance of the com.teamacronymcoders.essenceapi.modifier.
     * @return Returns whether it successfully mined the block.
     */
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
        return false;
    }

    /**
     * Used to modify "inventoryTick" method behaviour.
     *
     * @param stack         The currently held stack.
     * @param level         The Level where the entity whose inventory is ticking is occurring.
     * @param entity        The entity whose inventory is ticking.
     * @param inventorySlot The slot where the item is located in the inventory.
     * @param isCurrentItem If the item is the currently selected item.
     * @param instance      The current instance of the com.teamacronymcoders.essenceapi.modifier.
     */
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
    }

    /**
     * Used to modify "onSheared" method behaviour.
     *
     * @param stack     The currently held stack.
     * @param player    The Player doing the shearing.
     * @param sheared   The sheared LivingEntity.
     * @param hand      The InteractionHand being used for the shearing interaction.
     * @param stackList The list to add drops to.
     * @param instance  The current instance of the com.teamacronymcoders.essenceapi.modifier.
     * @return Returns an updated and filled list of Drops.
     */
    public List<ItemStack> onSheared(ItemStack stack, @Nullable Player player, LivingEntity sheared, InteractionHand hand, List<ItemStack> stackList, ModifierInstance instance) {
        return stackList;
    }

    /**
     * Can this Item disable a shield
     *
     * @param stack    The ItemStack
     * @param shield   The shield in question
     * @param entity   The LivingEntity holding the shield
     * @param attacker The LivingEntity holding the ItemStack
     * @param instance The current instance of the com.teamacronymcoders.essenceapi.modifier.
     * @return True if this ItemStack can disable the shield in question.
     */
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker, ModifierInstance instance) {
        return false;
    }

    /**
     * Queries if an item can perform the given action.
     * See {@link ToolActions} for a description of each stock action.
     *
     * @param toolAction The action being queried.
     * @param instance   The current instance of the com.teamacronymcoders.essenceapi.modifier.
     * @return True if the stack can perform the action.
     */
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction, ModifierInstance instance) {
        return false;
    }

    /**
     * Get a bounding box ({@link AABB}) of a sweep attack.
     *
     * @param player   The player performing the attack.
     * @param target   The entity targeted by the attack.
     * @param stack    The currently used ItemStack.
     * @param instance The current instance of the com.teamacronymcoders.essenceapi.modifier.
     * @return the bounding box.
     */
    @Nonnull
    public AABB getSweepHitBox(@Nonnull Player player, @Nonnull Entity target, ItemStack stack, ModifierInstance instance) {
        return stack.getItem().getSweepHitBox(stack, player, target);
    }

}