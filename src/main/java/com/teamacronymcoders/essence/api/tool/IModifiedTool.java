package com.teamacronymcoders.essence.api.tool;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapabilities;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.modifierholder.IModifierHolder;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import com.teamacronymcoders.essence.utils.tiers.IEssenceBaseTier;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public interface IModifiedTool {

    ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive);

    default int getMaxDamageFromModifiers(ItemStack stack, EssenceToolTiers tier) {
        return stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedDurability(stack, instance, tier.getMaxUses());
            }).reduce(0, Integer::sum)).orElse(tier.getMaxUses());
    }

    default float getDestroySpeedFromModifiers(float baseDestroySpeed, ItemStack stack) {
        return stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedEfficiency(stack, instance, baseDestroySpeed);
            }).reduce(0f, Float::sum)).orElse(baseDestroySpeed);
    }

    default int getHarvestLevelFromModifiers(int baseHarvestLevel, ItemStack stack) {
        return stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedHarvestLevel(stack, instance, baseHarvestLevel);
            }).reduce(0, Integer::sum)).orElse(baseHarvestLevel);
    }

    default void hitEntityFromModifiers(ItemStack stack, LivingEntity entity, LivingEntity player) {
        stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onHitEntity(stack, entity, player, instance)));
    }

    default void onBlockDestroyedFromModifiers(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onBlockDestroyed(stack, world, state, pos, miner, instance)));
    }

    default Optional<ActionResultType> onItemUseFromModifiers(ItemUseContext context) {
        return context.getItem().getCapability(EssenceCapabilities.MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .map(instance -> ((InteractionCoreModifier) instance.getModifier()).onItemUse(context, instance))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst()).orElse(Optional.of(ActionResultType.PASS));
    }

    default void inventoryTickFromModifiers(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        LazyOptional<IModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack, holderLazyOptional);
        holderLazyOptional.ifPresent(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, instance)));
    }

    default Multimap<String, AttributeModifier> getAttributeModifiersFromModifiers(Multimap<String, AttributeModifier> baseAttributes, EquipmentSlotType slot, ItemStack stack) {
        if (slot == EquipmentSlotType.MAINHAND) {
            stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
                .map(instance -> instance.getModifier().getAttributeModifiers(stack, null, instance))
                .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> baseAttributes.put(entry.getKey(), entry.getValue()))));
        }
        return baseAttributes;
    }

    default void addInformationFromModifiers(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag, IEssenceBaseTier tier) {
        int freeModifiers = stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER).map(IModifierHolder::getFreeModifiers).orElse(0);
        list.add(new TranslationTextComponent("tooltip.essence.tool.tier").applyTextStyle(TextFormatting.GRAY).appendSibling(new TranslationTextComponent(tier.getLocaleString()).applyTextStyle(tier.getRarity().color)));
        list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).applyTextStyle(EssenceUtilHelper.getTextColor(freeModifiers))).applyTextStyle(TextFormatting.GRAY));
        if (stack.getOrCreateTag().contains(EssenceModifierHelpers.TAG_MODIFIERS)) {
            list.add(new TranslationTextComponent("tooltip.essence.modifier").applyTextStyle(TextFormatting.GOLD));
            Map<String, List<ITextComponent>> sorting_map = new HashMap<>();
            stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER)
                .ifPresent(holder -> holder.getModifierInstances()
                    .forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance))));
            sorting_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                .forEach((s, iTextComponents) -> list.addAll(iTextComponents));
            list.add(new StringTextComponent(""));
        }
    }
}
