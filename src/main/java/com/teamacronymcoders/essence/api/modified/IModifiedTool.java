package com.teamacronymcoders.essence.api.modified;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.util.helper.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.util.tier.IEssenceBaseTier;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
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
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

public interface IModifiedTool extends IModified {

  ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive);

  default int getMaxDamageFromModifiers(ItemStack stack, EssenceToolTiers tier) {
    return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof ItemCoreModifier)
            .map(instance -> {
              ItemCoreModifier modifier = (ItemCoreModifier) instance.getModifier();
              return modifier.getModifiedDurability(stack, instance, tier.getMaxUses());
            }).reduce(0, Integer::sum)).orElse(tier.getMaxUses());
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

  default void onBlockDestroyedFromModifiers(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
    stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
            .forEach(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onBlockDestroyed(stack, world, state, pos, miner, instance)));
  }

  default Optional<ActionResultType> onItemUseFromModifiers(ItemUseContext context) {
    return context.getItem().getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
            .map(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onItemUse(context, instance))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst()).orElse(Optional.of(ActionResultType.PASS));
  }

  default void inventoryTickFromModifiers(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
    LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack, holderLazyOptional);
    holderLazyOptional.ifPresent(holder -> {
      List<ModifierInstance> list = holder.getModifierInstances();
      list.stream()
              .filter(instance -> instance.getModifier() instanceof ItemInteractionCoreModifier)
              .forEach(instance -> ((ItemInteractionCoreModifier) instance.getModifier()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, instance));
    });
  }

  default Multimap<Attribute, AttributeModifier> getAttributeModifiersFromModifiers(Multimap<Attribute, AttributeModifier> baseAttributes, EquipmentSlotType slot, ItemStack stack) {
    if (slot == EquipmentSlotType.MAINHAND) {
      stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(holder -> holder.getModifierInstances().stream()
              .filter(instance -> instance.getModifier() instanceof ItemAttributeModifier)
              .map(instance -> ((ItemAttributeModifier) instance.getModifier()).getAttributeModifiers(stack, null, instance))
              .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> baseAttributes.put(entry.getKey(), entry.getValue()))));
    }
    return baseAttributes;
  }

  default void addInformationFromModifiers(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag, IEssenceBaseTier tier) {
    int freeModifiers = stack.getItem() instanceof IModifiedTool ? ((IModifiedTool) stack.getItem()).getFreeModifiers() : 0;
    int maxModifiers = stack.getItem() instanceof IModifiedTool ? ((IModifiedTool) stack.getItem()).getMaxModifiers() : 0;
    list.add(new TranslationTextComponent("tooltip.essence.tool.tier").mergeStyle(TextFormatting.GRAY).append(new TranslationTextComponent(tier.getLocaleString()).mergeStyle(tier.getRarity().color)));
    list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).mergeStyle(EssenceUtilHelper.getTextColor(freeModifiers, maxModifiers))).mergeStyle(TextFormatting.GRAY));
    if (stack.getOrCreateTag().contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS) && !stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND).isEmpty()) {
      list.add(new TranslationTextComponent("tooltip.essence.modifier").mergeStyle(TextFormatting.GOLD));
      Map<String, List<ITextComponent>> sorting_map = new HashMap<>();
      stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER)
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
