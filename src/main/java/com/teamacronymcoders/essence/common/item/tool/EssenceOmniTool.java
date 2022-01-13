package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
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
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class EssenceOmniTool extends DiggerItem implements IModifiedItem {

    private final EssenceToolTiers tier;
    public static final Set<ToolAction> DEFAULT_OMNITOOL_ACTIONS = of(ToolActions.DEFAULT_AXE_ACTIONS, ToolActions.DEFAULT_PICKAXE_ACTIONS, ToolActions.DEFAULT_SHOVEL_ACTIONS);

    private static Set<ToolAction> of(Set<ToolAction>... defaults) {
        Set<ToolAction> actions = Sets.newIdentityHashSet();
        for (Set<ToolAction> toolActions : defaults) {
            actions.addAll(toolActions);
        }
        return actions;
    }

    //TODO: Figure out what to do about EFFECTIVE_ON
    public EssenceOmniTool(Properties properties, EssenceToolTiers tier) {
        super(tier.getAttackDamageAxeMod(), tier.getSpeedAxeMod(), tier, EssenceTags.EssenceBlockTags.OMNITOOL_BLOCKS, properties.rarity(tier.getRarity()));
        this.tier = tier;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull Rarity getRarity(ItemStack stack) {
        return tier.getRarity();
    }

    public InteractionResult onItemBehaviour(UseOnContext context) {
        Level level = context.getLevel();
        BlockState state = level.getBlockState(context.getClickedPos());
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        InteractionResult resultType = InteractionResult.FAIL;
        BlockState behaviourState;

        for (ToolAction action : DEFAULT_OMNITOOL_ACTIONS) {
            behaviourState = state.getToolModifiedState(level, pos, player, stack, action);
            if (behaviourState != null && !behaviourState.equals(state)) {
                level.setBlock(pos, behaviourState, Block.UPDATE_ALL_IMMEDIATE);
                resultType = InteractionResult.SUCCESS;
            }
            if (resultType == InteractionResult.SUCCESS) {
                return resultType;
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();
        InteractionResult resultType = useOnFromModifier(context).orElse(InteractionResult.FAIL);
        if (resultType == InteractionResult.SUCCESS) return resultType;
        BlockState behaviourState;

        // Check Vanilla Axe Behaviour
        if (resultType == InteractionResult.FAIL) {
            behaviourState = state.getToolModifiedState(level, pos, player, stack, ToolActions.AXE_STRIP);
            if (behaviourState != null && !behaviourState.equals(state)) {
                level.setBlock(pos, behaviourState, Block.UPDATE_ALL_IMMEDIATE);
                resultType = InteractionResult.SUCCESS;
            }
            if (resultType == InteractionResult.SUCCESS) {
                return resultType;
            }
        }

        // Check Pickaxe Behaviour
        behaviourState = state.getToolModifiedState(level, pos, player, stack, ToolActions.PICKAXE_DIG);
        if (behaviourState != null && !behaviourState.equals(state)) {
            level.setBlock(pos, behaviourState, Block.UPDATE_ALL_IMMEDIATE);
            resultType = InteractionResult.SUCCESS;
        }
        if (resultType == InteractionResult.SUCCESS) {
            return resultType;
        }

        // Check Vanilla Shovel Behaviour
        behaviourState = state.getToolModifiedState(level, pos, player, stack, ToolActions.SHOVEL_FLATTEN);
        if (behaviourState != null && behaviourState.equals(state)) {
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
    @ParametersAreNonnullByDefault
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isFoil(ItemStack stack) {
        return EssenceItemstackModifierHelpers.hasModifier(EssenceModifierRegistrate.ENCHANTED_MODIFIER.get(), stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) + getMaxDurabilityFromModifiers(stack, tier);
    }

    @Override
    @ParametersAreNonnullByDefault
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return this.blocks.contains(state.getBlock()) ? this.speed + getDestroySpeedFromModifiers(stack, state, this.speed) : 1.0F + getDestroySpeedFromModifiers(stack, state, 1.0F);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        hurtEnemyFromModifiers(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        mineBlockFromModifiers(stack, level, state, pos, entityLiving);
        return super.mineBlock(stack, level, state, pos, entityLiving);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {
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
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
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

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction);
    }
}
