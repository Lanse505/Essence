package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.utils.registration.EssenceModifierRegistration;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers.ESSENCE;

public class EssenceShovel extends ShovelItem implements IModifiedTool {

    private EssenceToolTiers tier;
    private int freeModifiers;

    public EssenceShovel(EssenceToolTiers tier) {
        super(tier, tier.getAttackDamageShovelMod(), tier.getAttackSpeedShovelMod(), new Item.Properties().group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.tier = tier;
        this.freeModifiers = tier.getFreeModifiers();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return EssenceModifierHelpers.hasEnchantedModifier(stack);
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        if (state.getHarvestTool() == ToolType.SHOVEL) {
            return getTier().getHarvestLevel() >= state.getHarvestLevel();
        }
        return super.canHarvestBlock(state);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) + EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedDurability(stack, instance, tier.getMaxUses());
            }).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) + EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedEfficiency(stack, instance, super.getDestroySpeed(stack, state));
            }).reduce(0f, Float::sum);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedHarvestLevel(stack, instance, harvestLevel);
            }).reduce(0, Integer::sum);
    }

    @Override
    @SuppressWarnings("deprecation")
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        if (slot == EquipmentSlotType.MAINHAND) {
            Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
            EssenceModifierHelpers.getModifiers(stack).stream()
                .map(instance -> instance.getModifier().getAttributeModifiers(stack, null, instance))
                .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> multimap.put(entry.getKey(), entry.getValue())));
            return multimap;
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType superResult = super.onItemUse(context);
        Optional<ActionResultType> modifierResult = EssenceModifierHelpers.getModifiers(context.getItem()).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .map(instance -> ((InteractionCoreModifier) instance.getModifier()).onItemUse(context, instance))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst();
        return superResult == ActionResultType.SUCCESS ? superResult : modifierResult.orElse(superResult);
    }

    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        if (isRecursive) {
            return super.onItemUse(context);
        }
        return onItemUse(context);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity entity, LivingEntity player) {
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onHitEntity(stack, entity, player, instance));
        return super.hitEntity(stack, entity, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onBlockDestroyed(stack, world, state, pos, miner, instance));
        return super.onBlockDestroyed(stack, world, state, pos, miner);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack);
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, instance));
        super.inventoryTick(stack, world, entity, inventorySlot, isCurrentItem);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("tooltip.essence.tool.tier").applyTextStyle(TextFormatting.GRAY).appendSibling(new TranslationTextComponent(tier.getLocalName()).applyTextStyle(tier.getRarity().color)));
        list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).applyTextStyle(EssenceUtilHelper.getTextColor(freeModifiers))).applyTextStyle(TextFormatting.GRAY));
        if (stack.getOrCreateTag().contains(EssenceModifierHelpers.TAG_MODIFIERS)) {
            list.add(new TranslationTextComponent("tooltip.essence.modifier").applyTextStyle(TextFormatting.GOLD));
            Map<String, ITextComponent> sorting_map = new HashMap<>();
            EssenceModifierHelpers.getModifiers(stack).forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance).get(0)));
            sorting_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                .forEach((s, iTextComponent) -> list.add(iTextComponent));
            list.add(new StringTextComponent(""));
        }
    }
}
