package com.teamacronymcoders.essence.common.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.common.capability.EssenceCoreCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.IForgeShearable;

import java.util.ArrayList;
import java.util.List;

public class EssenceShearingHelper {

    public static InteractionResult handleIShearableEntity(ItemStack stack, Player player, LivingEntity sheared, InteractionHand hand) {
        if (sheared instanceof IForgeShearable) {
            IForgeShearable target = (IForgeShearable) sheared;
            BlockPos pos = new BlockPos(sheared.getX(), sheared.getY(), sheared.getZ());
            if (target.isShearable(stack, sheared.level, pos)) {
                List<ItemStack> dropList = sheared instanceof Sheep ? handleShearingSheep((Sheep) sheared, stack, sheared.level, pos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack)) : target.onSheared(player, stack, sheared.level, pos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack));

                // Gathers a list of Entries with InteractionCoreModifiers that also return a different value than the default
                List<? extends ModifierInstance> unchecked = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());

                // Loops over and Gathers the final modified list
                for (ModifierInstance instance : unchecked) {
                    if (instance.getModifier() instanceof ItemInteractionCoreModifier interactionCoreModifier) {
                        dropList = interactionCoreModifier.onShearedAltered(stack, player, sheared, hand, dropList, instance);
                    }
                }

                // Handle dropping the final list of ItemStacks
                dropList.forEach(s -> {
                    ItemEntity entity = player.drop(s, false);
                    if (entity != null) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(
                                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                                Essence.RANDOM.nextFloat() * 0.05F,
                                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
                    }
                });

                // Damage the ItemStack
                stack.hurtAndBreak(1, sheared, entity -> entity.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    private static List<ItemStack> handleShearingSheep(Sheep sheep, ItemStack item, Level level, BlockPos pos, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        if (!sheep.level.isClientSide()) {
            sheep.setSheared(true);
            int i = EssenceUtilHelper.nextIntInclusive(1 + fortune, 4 + fortune);
            for (int j = 0; j < i; ++j) {
                ret.add(new ItemStack(Sheep.ITEM_BY_DYE.get(sheep.getColor())));
            }
        }
        sheep.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }
}
