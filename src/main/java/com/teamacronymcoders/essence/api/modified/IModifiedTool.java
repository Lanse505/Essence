package com.teamacronymcoders.essence.api.modified;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.common.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.common.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.common.util.tier.IEssenceBaseTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public interface IModifiedTool extends IModified {

    InteractionResult useOnModified(UseOnContext context, boolean isRecursive);

    default int getMaxDamageFromModifiers(ItemStack stack, EssenceToolTiers tier) {
        return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof ItemCoreModifier)
                .map(instance -> {
                    ItemCoreModifier modifier = (ItemCoreModifier) instance.getModifier();
                    return modifier.getModifiedDurability(stack, instance, tier.getUses());
                }).reduce(0, Integer::sum)).orElse(tier.getUses());
    }

    default float getDestroySpeedFromModifiers(float baseDestroySpeed, ItemStack stack) {
        return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof ItemCoreModifier)
                .map(instance -> {
                    ItemCoreModifier modifier = (ItemCoreModifier) instance.getModifier();
                    return modifier.getModifiedEfficiency(stack, instance, baseDestroySpeed);
                }).reduce(0f, Float::sum)).orElse(baseDestroySpeed);
    }

    default int getHarvestLevelFromModifiers(int baseHarvestLevel, ItemStack stack) {
        return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof ItemCoreModifier)
                .map(instance -> {
                    ItemCoreModifier modifier = (ItemCoreModifier) instance.getModifier();
                    return modifier.getModifiedHarvestLevel(stack, instance, baseHarvestLevel);
                }).reduce(0, Integer::sum)).orElse(baseHarvestLevel);
    }

    default void hitEntityFromModifiers(ItemStack stack, LivingEntity entity, LivingEntity player) {
        stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
                .forEach(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onHitEntity(stack, entity, player, instance)));
    }

    default void onBlockDestroyedFromModifiers(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
                .forEach(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onBlockDestroyed(stack, level, state, pos, miner, instance)));
    }

    default Optional<InteractionResult> onItemUseFromModifiers(UseOnContext context) {
        ItemStack stack = Objects.requireNonNull(context.getPlayer()).getItemInHand(context.getHand());
        return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
                .map(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onItemUse(context, instance))
                .filter(actionResultType -> Objects.equals(actionResultType, InteractionResult.PASS))
                .findFirst()).orElse(Optional.of(InteractionResult.PASS));
    }

    default void inventoryTickFromModifiers(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem) {
        LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack, holderLazyOptional);
        holderLazyOptional.ifPresent(holder -> {
            List<ModifierInstance> list = holder.getModifierInstances();
            list.stream()
                    .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
                    .forEach(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onInventoryTick(stack, level, entity, inventorySlot, isCurrentItem, instance));
        });
    }

    default Multimap<Attribute, AttributeModifier> getAttributeModifiersFromModifiers(Multimap<Attribute, AttributeModifier> baseAttributes, EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
                    .filter(instance -> instance.getModifier() instanceof ItemAttributeModifier)
                    .map(instance -> ((ItemAttributeModifier) instance.getModifier()).getAttributeModifiers(stack, null, instance))
                    .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> baseAttributes.put(entry.getKey(), entry.getValue()))));
        }
        return baseAttributes;
    }

    default void addInformationFromModifiers(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag, IEssenceBaseTier tier) {
        int freeModifiers = stack.getItem() instanceof IModifiedTool ? ((IModifiedTool) stack.getItem()).getFreeModifiers() : 0;
        int maxModifiers = stack.getItem() instanceof IModifiedTool ? ((IModifiedTool) stack.getItem()).getMaxModifiers() : 0;
        list.add(new TranslatableComponent("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent(tier.getLocaleString()).withStyle(tier.getRarity().color)));
        list.add(new TranslatableComponent("tooltip.essence.modifier.free", new TextComponent(String.valueOf(freeModifiers)).withStyle(EssenceUtilHelper.getTextColor(freeModifiers, maxModifiers))).withStyle(ChatFormatting.GRAY));
        if (stack.getOrCreateTag().contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS) && !stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND).isEmpty()) {
            list.add(new TranslatableComponent("tooltip.essence.modifier").withStyle(ChatFormatting.GOLD));
            Map<String, List<Component>> sorting_map = new HashMap<>();
            stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER)
                    .ifPresent(holder -> holder.getModifierInstances()
                            .forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance))));
            sorting_map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                    .forEach((s, iTextComponents) -> list.addAll(iTextComponents));
            list.add(new TextComponent(""));
        }
    }

    EssenceToolTiers getTier();

}
