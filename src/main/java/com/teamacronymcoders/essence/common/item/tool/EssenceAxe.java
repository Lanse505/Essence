package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EssenceAxe extends AxeItem implements IModifiedItem {

    private final EssenceToolTiers tier;

    public EssenceAxe(Properties properties, EssenceToolTiers tier) {
        super(tier, tier.getAttackDamageAxeMod(), tier.getAttackSpeedAxeMod(), properties.rarity(tier.getRarity()));
        this.tier = tier;
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return tier.getRarity();
    }

    public InteractionResult onItemBehaviour(UseOnContext context) {
        Level level = context.getLevel();
        Block block = level.getBlockState(context.getClickedPos()).getBlock();
        return level.getRecipeManager().getRecipes().stream()
                .filter(iRecipe -> iRecipe.getType() == AxeStrippingRecipe.SERIALIZER.getRecipeType())
                .map(iRecipe -> (AxeStrippingRecipe) iRecipe)
                .filter(recipe -> recipe.matches(block))
                .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(InteractionResult.PASS);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();
        InteractionResult resultType = InteractionResult.FAIL;
        BlockState behaviourState;

        // Check Vanilla Axe Behaviour
        behaviourState = state.getToolModifiedState(level, pos, player, stack, ToolActions.AXE_DIG);
        if (behaviourState != null && !behaviourState.equals(state)) {
            level.setBlock(pos, behaviourState, Block.UPDATE_ALL_IMMEDIATE);
            resultType = InteractionResult.SUCCESS;
        }
        if (resultType == InteractionResult.SUCCESS) {
            return resultType;
        }

        // Check Recipes
        resultType = onItemBehaviour(context);
        if (resultType == InteractionResult.SUCCESS) {
            return resultType;
        }

        // Fallback on Modifier Behaviour
        return useOnFromModifier(context).orElse(resultType);
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        if (isRecursive) {
            return onItemBehaviour(context);
        }
        return useOn(context);
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
        return EssenceItemstackModifierHelpers.hasEnchantedModifier(stack);
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
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        inventoryTickFromModifiers(stack, worldIn, entityIn, itemSlot, isSelected);
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
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
        tooltip.add(new TranslatableComponent("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent(tier.getLocaleString()).withStyle(tier.getRarity().color)));
        addInformationFromModifiers(stack, level, tooltip, flagIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (!stack.isEmpty() && nbt != null) {
            return new ItemStackModifierProvider(stack, nbt);
        }
        return new ItemStackModifierProvider(stack);
    }

    @Override
    public @NotNull EssenceToolTiers getTier() {
        return tier;
    }
}
