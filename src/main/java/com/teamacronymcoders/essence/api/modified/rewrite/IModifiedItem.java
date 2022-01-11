package com.teamacronymcoders.essence.api.modified.rewrite;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.modifier.item.tank.HoldingModifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public interface IModifiedItem {

    EssenceToolTiers getTier();

    InteractionResult useOnModified(UseOnContext context, boolean isRecursive);

    default Multimap<Attribute, AttributeModifier> getAttributeModifiersFromModifiers(Multimap<Attribute, AttributeModifier> baseAttributes, EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> {
                for (ModifierInstance instance : holder.getModifierInstances()) {
                    if (instance.getModifier().get() instanceof ItemCoreModifier) {
                        Multimap<Attribute, AttributeModifier> modifierMultimap = ((ItemCoreModifier) instance.getModifier().get()).getAttributeModifiers(stack, null, instance);
                        for (Map.Entry<Attribute, AttributeModifier> entry : modifierMultimap.entries()) {
                            baseAttributes.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            });
        }
        return baseAttributes;
    }

    default int getMaxDurabilityFromModifiers(ItemStack stack, EssenceToolTiers tier) {
        return stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier().get() instanceof ItemCoreModifier)
                .map(instance -> {
                    ItemCoreModifier modifier = (ItemCoreModifier) instance.getModifier().get();
                    return modifier.getModifiedDurability(stack, instance, tier.getUses());
                }).reduce(0, Integer::sum)).orElse(tier.getUses());
    }

    default float getDestroySpeedFromModifiers(ItemStack stack, BlockState state, float baseDestroySpeed) {
        return stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier().get() instanceof ItemCoreModifier)
                .map(instance -> {
                    ItemCoreModifier modifier = (ItemCoreModifier) instance.getModifier().get();
                    return modifier.getDestroySpeed(stack, state, baseDestroySpeed, instance);
                }).reduce(0f, Float::sum)).orElse(baseDestroySpeed);
    }

    default Optional<InteractionResult> useOnFromModifier(UseOnContext context) {
        ItemStack stack = Objects.requireNonNull(context.getPlayer()).getItemInHand(context.getHand());
        return stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> {
            for (ModifierInstance instance : holder.getModifierInstances()) {
                if (instance.getModifier().get() instanceof ItemInteractionModifier) {
                    InteractionResult actionResultType = ((ItemInteractionModifier) instance.getModifier().get()).useOn(context, instance);
                    if (Objects.equals(actionResultType, InteractionResult.PASS)) {
                        return Optional.of(actionResultType);
                    }
                }
            }
            return Optional.<InteractionResult>empty();
        }).orElse(Optional.of(InteractionResult.PASS));
    }

    default void hurtEnemyFromModifiers(ItemStack stack, LivingEntity entity, LivingEntity player) {
        stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> {
            for (ModifierInstance instance : holder.getModifierInstances()) {
                if (instance.getModifier().get() instanceof ItemInteractionModifier) {
                    ((ItemInteractionModifier) instance.getModifier().get()).hurtEnemy(stack, entity, player, instance);
                }
            }
        });
    }

    default void mineBlockFromModifiers(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> {
            for (ModifierInstance instance : holder.getModifierInstances()) {
                if (instance.getModifier().get() instanceof ItemInteractionModifier) {
                    ((ItemInteractionModifier) instance.getModifier().get()).mineBlock(stack, level, state, pos, miner, instance);
                }
            }
        });
    }

    default void inventoryTickFromModifiers(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem) {
        LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack, holderLazyOptional);
        holderLazyOptional.ifPresent(holder -> {
            List<ModifierInstance> list = holder.getModifierInstances();
            for (ModifierInstance instance : list) {
                if (instance.getModifier().get() instanceof ItemInteractionModifier) {
                    ((ItemInteractionModifier) instance.getModifier().get()).inventoryTick(stack, level, entity, inventorySlot, isCurrentItem, instance);
                }
            }
        });
    }

    default List<ItemStack> onShearedFromModifiers(ItemStack stack, @Nullable Player player, LivingEntity sheared, InteractionHand hand, List<ItemStack> drops) {
        LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            List<ModifierInstance> list = holder.getModifierInstances();
            for (ModifierInstance instance : list) {
                if (instance.getModifier().get() instanceof ItemInteractionModifier) {
                    ((ItemInteractionModifier) instance.getModifier().get()).onSheared(stack, player, sheared, hand, drops, instance);
                }
            }
        });
        return drops;
    }

    @SuppressWarnings("unchecked")
    default void addInformationFromModifiers(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS) && !stack.getTag().getCompound(EssenceItemstackModifierHelpers.TAG_MODIFIERS).isEmpty()) {
            list.add(new TranslatableComponent("tooltip.essence.modifier").withStyle(ChatFormatting.GOLD));
            Map<String, List<Component>> sorting_map = new HashMap<>();
            stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> {
                for (ModifierInstance instance : holder.getModifierInstances()) {
                    sorting_map.put(instance.getModifier().get().getRenderedText(instance).get(0).toString(), instance.getModifier().get().getRenderedText(instance));
                }
            });
            for (Map.Entry<String, List<Component>> entry : sorting_map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new)).entrySet()) {
                List<Component> iTextComponents = entry.getValue();
                list.addAll(iTextComponents);
            }
            list.add(new TextComponent(""));
        }

    }

    default int getMaxCapacityFromModifiers(int currentCapacity, ItemStack stack) {
        return stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER)
                .map(holder -> holder.getModifierInstances().stream()
                        .filter(instance -> instance.getModifier().get() instanceof ItemCoreModifier)
                        .map(instance -> {
                            HoldingModifier modifier = (HoldingModifier) instance.getModifier().get();
                            return modifier.getTankSizeModifier(instance.getLevel(), currentCapacity);
                        }).reduce(0, Integer::sum)).orElse(currentCapacity);
    }
}
