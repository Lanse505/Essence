package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.api.recipe.tool.EssenceShearingRecipe;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.helper.EssenceShearingHelper;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class EssenceShear extends ShearsItem implements IModifiedItem {

    private final EssenceToolTiers tier;
    private int rainbowVal = 0;

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public EssenceShear(Properties properties, EssenceToolTiers tier) {
        super(properties.durability(tier.getUses()).rarity(tier.getRarity()));
        this.tier = tier;
        this.attributeModifiers = HashMultimap.create();
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return tier.getRarity();
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        if (!stack.isEmpty() && tag != null) {
            return new ItemStackModifierProvider(stack, tag);
        }
        return new ItemStackModifierProvider(stack);
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return getAttributeModifiersFromModifiers(attributeModifiers, slot, stack);
        }
        return HashMultimap.create();
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Optional<InteractionResult> modifierResult = useOnFromModifier(context);
        return super.useOn(context).equals(InteractionResult.SUCCESS) ? super.useOn(context) : modifierResult.orElse(super.useOn(context));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        hurtEnemyFromModifiers(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity miner) {
        mineBlockFromModifiers(stack, level, state, pos, miner);
        return super.mineBlock(stack, level, state, pos, miner);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        inventoryTickFromModifiers(stack, level, entity, itemSlot, isSelected);
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, Player player, LivingEntity sheared, @NotNull InteractionHand hand) {
        if (sheared.level.isClientSide()) {
            return InteractionResult.FAIL;
        }
        if (sheared instanceof IForgeShearable) {
            return EssenceShearingHelper.handleIShearableEntity(stack, player, sheared, hand);
        }
        Optional<EssenceShearingRecipe> recipe = player.level.getRecipeManager().getAllRecipesFor(EssenceShearingRecipe.SERIALIZER.getRecipeType()).stream()
                .filter(checking -> checking.matches(sheared)).findFirst();
        return recipe.isPresent() ? recipe.get().resolveRecipe(stack, player, sheared, hand) : InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        list.add(Component.translatable("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(Component.translatable(tier.getLocaleString()).withStyle(tier.getRarity().color)));
        addInformationFromModifiers(stack, level, list, flag);
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        if (isRecursive) {
            return InteractionResult.PASS;
        }
        return useOn(context);
    }

    public int getRainbowVal() {
        return rainbowVal;
    }

    public void setRainbowVal(int rainbowVal) {
        this.rainbowVal = rainbowVal;
    }

    @Override
    public EssenceToolTiers getTier() {
        return tier;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHEARS_ACTIONS.contains(toolAction);
    }
}
