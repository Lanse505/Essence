package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.api.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.common.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

public class EssenceShovel extends ShovelItem implements IModifiedTool {

    private final EssenceToolTiers tier;
    private final int baseModifiers;
    private int freeModifiers;
    private int additionalModifiers;

    public EssenceShovel(Properties properties, EssenceToolTiers tier) {
        super(tier, tier.getAttackDamageShovelMod(), tier.getAttackSpeedShovelMod(), properties.rarity(tier.getRarity()));
        this.tier = tier;
        this.baseModifiers = tier.getFreeModifiers();
        this.freeModifiers = tier.getFreeModifiers();
        this.additionalModifiers = 0;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return tier.getRarity();
    }

    public InteractionResult onItemBehaviour(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        }
        if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
            Player playerentity = context.getPlayer();
            world.levelEvent(null, 1009, pos, 0);
            BlockState newState = state.setValue(CampfireBlock.LIT, Boolean.FALSE);
            if (!world.isClientSide()) {
                world.setBlock(pos, newState, 11);
                if (playerentity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerentity, (playerIn) -> {
                        playerIn.broadcastBreakEvent(context.getHand());
                    });
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return world.getRecipeManager().getRecipes().stream()
                    .filter(iRecipe -> iRecipe.getType() == ShovelPathingRecipe.SERIALIZER.getRecipeType())
                    .map(iRecipe -> (ShovelPathingRecipe) iRecipe)
                    .filter(recipe -> recipe.matches(state.getBlock()))
                    .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(InteractionResult.PASS);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getItemInHand();
        InteractionResult resultType = InteractionResult.FAIL;
        BlockState behaviourState;

        // Check Vanilla Axe Behaviour
        behaviourState = state.getToolModifiedState(world, pos, player, stack, ToolActions.SHOVEL_FLATTEN);
        if (behaviourState != null && !behaviourState.equals(state)) {
            world.setBlock(pos, behaviourState, Block.UPDATE_ALL_IMMEDIATE);
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
        return onItemUseFromModifiers(context).orElse(resultType);
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        if (isRecursive) {
            return onItemBehaviour(context);
        }
        return useOn(context);
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
    public boolean isFoil(ItemStack stack) {
        return EssenceItemstackModifierHelpers.hasEnchantedModifier(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) + getMaxDamageFromModifiers(stack, tier);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) + getDestroySpeedFromModifiers(super.getDestroySpeed(stack, state), stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        hitEntityFromModifiers(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        onBlockDestroyedFromModifiers(stack, level, state, pos, entityLiving);
        return super.mineBlock(stack, level, state, pos, entityLiving);
    }

    @Override
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        addInformationFromModifiers(stack, level, tooltip, flagIn, tier);
    }

    @Override
    public void addModifierWithoutIncreasingAdditional(int increase) {
        freeModifiers += increase;
    }

    @Override
    public void increaseFreeModifiers(int increase) {
        freeModifiers += increase;
        additionalModifiers += increase;
    }

    @Override
    public boolean decreaseFreeModifiers(int decrease) {
        if (freeModifiers - decrease < 0) {
            return false;
        }
        freeModifiers = freeModifiers - decrease;
        return true;
    }

    @Override
    public int getFreeModifiers() {
        return freeModifiers;
    }

    @Override
    public int getMaxModifiers() {
        return baseModifiers + additionalModifiers;
    }

    @Override
    public boolean recheck(List<ModifierInstance> modifierInstances) {
        int cmc = 0;
        for (ModifierInstance instance : modifierInstances) {
            if (instance.getModifier() instanceof ItemCoreModifier) {
                cmc += instance.getModifier().getModifierCountValue(instance.getLevel());
            }
        }
        return cmc <= baseModifiers + additionalModifiers;
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
    public EssenceToolTiers getTier() {
        return tier;
    }
}
