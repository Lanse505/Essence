package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EssenceSword extends SwordItem implements IModifiedItem {

    private final EssenceToolTiers tier;

    public EssenceSword(Properties properties, EssenceToolTiers tier) {
        super(tier, tier.getAttackDamageSwordMod(), tier.getAttackSpeedSwordMod(), properties.rarity(tier.getRarity()));
        this.tier = tier;
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return tier.getRarity();
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return EssenceItemstackModifierHelpers.hasModifier(EssenceModifierRegistrate.ENCHANTED_MODIFIER.get(), stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) + getMaxDurabilityFromModifiers(stack, tier);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        return super.getDestroySpeed(stack, state) + getDestroySpeedFromModifiers(stack, state, super.getDestroySpeed(stack, state));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        hurtEnemyFromModifiers(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        mineBlockFromModifiers(stack, level, state, pos, entityLiving);
        return super.mineBlock(stack, level, state, pos, entityLiving);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        inventoryTickFromModifiers(stack, level, entityIn, itemSlot, isSelected);
        super.inventoryTick(stack, level, entityIn, itemSlot, isSelected);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return getAttributeModifiersFromModifiers(getDefaultAttributeModifiers(slot), slot, stack);
        }
        return HashMultimap.create();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(Component.translatable(tier.getLocaleString()).withStyle(tier.getRarity().color)));
        addInformationFromModifiers(stack, level, tooltip, flagIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        if (tag != null) {
            return new ItemStackModifierProvider(stack, tag);
        }
        return new ItemStackModifierProvider(stack);
    }

    @Override
    public @NotNull EssenceToolTiers getTier() {
        return tier;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }
}
