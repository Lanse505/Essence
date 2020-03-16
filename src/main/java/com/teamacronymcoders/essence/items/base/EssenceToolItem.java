package com.teamacronymcoders.essence.items.base;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.legacy.IModifiedTool;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class EssenceToolItem extends ToolItem implements IModifiedTool {

    private EssenceToolTiers tier;
    private int freeModifiers;

    private final Set<Block> effectiveBlocks;
    protected final float efficiency;
    protected final float attackDamage;
    protected final float attackSpeed;

    protected EssenceToolItem(float attackIn, float attackSpeedIn, EssenceToolTiers tier, Set<Block> effectiveBlocksIn, Properties builder) {
        super(tier.getAttackDamage(), attackSpeedIn, tier, effectiveBlocksIn, builder.group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.tier = tier;
        this.freeModifiers = tier.getFreeModifiers();
        this.effectiveBlocks = effectiveBlocksIn;
        this.efficiency = tier.getEfficiency();
        this.attackDamage = tier.getAttackDamage() + attackIn;
        this.attackSpeed = attackSpeedIn;
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
        float efficiency;
        if (getToolTypes(stack).stream().anyMatch(e -> state.isToolEffective(e))) {
            efficiency = this.efficiency;
        } else {
            efficiency = this.effectiveBlocks.contains(state.getBlock()) ? this.efficiency : 1.0F;
        }
        return efficiency + EssenceModifierHelpers.getModifiers(stack).stream()
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
    public boolean hitEntity(ItemStack stack, LivingEntity entity, LivingEntity player) {
        stack.damageItem(2, player, (entity1 -> entity1.sendBreakAnimation(EquipmentSlotType.MAINHAND)));
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onHitEntity(stack, entity, player, instance));
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isRemote && state.getBlockHardness(world, pos) != 0.0F) {
            stack.damageItem(1, miner, entity -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onBlockDestroyed(stack, world, state, pos, miner, instance));
        return true;
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multiMap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multiMap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
            multiMap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        }
        return multiMap;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        if (slot == EquipmentSlotType.MAINHAND) {
            Multimap<String, AttributeModifier> multimap = getAttributeModifiers(slot);
            EssenceModifierHelpers.getModifiers(stack).stream()
                .map(instance -> instance.getModifier().getAttributeModifiers(stack, null, instance))
                .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> multimap.put(entry.getKey(), entry.getValue())));
            return multimap;
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("tooltip.essence.tool.tier").applyTextStyle(TextFormatting.GRAY).appendSibling(new TranslationTextComponent(tier.getLocalName()).applyTextStyle(tier.getRarity().color)));
        list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).applyTextStyle(EssenceUtilHelper.getTextColor(freeModifiers))).applyTextStyle(TextFormatting.GRAY));
        if (stack.getOrCreateTag().contains(EssenceModifierHelpers.TAG_MODIFIERS)) {
            list.add(new TranslationTextComponent("tooltip.essence.modifier").applyTextStyle(TextFormatting.GOLD));
            Map<String, List<ITextComponent>> sorting_map = new HashMap<>();
            EssenceModifierHelpers.getModifiers(stack).forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance)));
            sorting_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                .forEach((s, iTextComponents) -> list.addAll(iTextComponents));
            list.add(new StringTextComponent(""));
        }
    }

    @Override
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        return ActionResultType.PASS;
    }
}
