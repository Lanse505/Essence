package com.teamacronymcoders.essence.item.tool;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.registration.EssenceModifierRegistration;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class EssenceAxe extends AxeItem implements IModifiedTool {

    private final EssenceToolTiers tier;
    private final int baseModifiers;
    private int freeModifiers;
    private int additionalModifiers;

    public EssenceAxe(EssenceToolTiers tier) {
        super(tier, tier.getAttackDamageAxeMod(), tier.getAttackSpeedAxeMod(), new Item.Properties().group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.tier = tier;
        this.baseModifiers = tier.getFreeModifiers();
        this.freeModifiers = tier.getFreeModifiers();
        this.additionalModifiers = 0;
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = super.getShareTag(stack);
        ListNBT capTag = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(cap -> cap.serializeNBT()).orElse(null);
        if (capTag != null) {
            if (tag == null) {
                tag = new CompoundNBT();
            }
            tag.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, capTag);
        }
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS)) {
            ListNBT capTag = nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
            stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(cap -> cap.deserializeNBT(capTag));
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public Rarity getRarity(ItemStack p_77613_1_) {
        return tier.getRarity();
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.isInGroup(group)) {
            list.add(new ItemStack(this));
            ItemStack stack = new ItemStack(this, 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
                new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.EFFICIENCY_MODIFIER.get(), 5, null),
                new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.FIERY_MODIFIER.get(), 5, null),
                new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.ENCHANTED_MODIFIER.get(), 1, null)
            ));
            list.add(stack);
        }
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        if (state.getHarvestTool() == ToolType.AXE) {
            return getTier().getHarvestLevel() >= state.getHarvestLevel();
        }
        return super.canHarvestBlock(state);
    }

    public ActionResultType onItemBehaviour(ItemUseContext context) {
        World world = context.getWorld();
        Block block = world.getBlockState(context.getPos()).getBlock();
        return world.getRecipeManager().getRecipes().stream()
            .filter(iRecipe -> iRecipe.getType() == AxeStrippingRecipe.SERIALIZER.getRecipeType())
            .map(iRecipe -> (AxeStrippingRecipe) iRecipe)
            .filter(recipe -> recipe.matches(block))
            .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType resultType = onItemBehaviour(context);
        Optional<ActionResultType> modifierResult = onItemUseFromModifiers(context);
        return resultType == ActionResultType.SUCCESS ? resultType : modifierResult.orElse(resultType);
    }

    @Override
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        if (isRecursive) {
            return onItemBehaviour(context);
        }
        return onItemUse(context);
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
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return super.getHarvestLevel(stack, tool, player, blockState) + getHarvestLevelFromModifiers(super.getHarvestLevel(stack, tool, player, blockState), stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        hitEntityFromModifiers(stack, target, attacker);
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        onBlockDestroyedFromModifiers(stack, worldIn, state, pos, entityLiving);
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        inventoryTickFromModifiers(stack, worldIn, entityIn, itemSlot, isSelected);
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return getAttributeModifiersFromModifiers(getAttributeModifiers(slot), slot, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        addInformationFromModifiers(stack, worldIn, tooltip, flagIn, tier);
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
    public boolean recheck(ItemStack object, List<ModifierInstance<ItemStack>> modifierInstances) {
        int cmc = 0;
        for (ModifierInstance<ItemStack> instance : modifierInstances) {
            if (instance.getModifier() instanceof ItemCoreModifier) {
                cmc += instance.getModifier().getModifierCountValue(instance.getLevel(), object);
            }
        }
        return cmc <= baseModifiers + additionalModifiers;
    }

    @Override
    public Class<ItemStack> getType() {
        return ItemStack.class;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (!stack.isEmpty() && nbt != null) {
            return new ItemStackModifierProvider(stack, nbt);
        }
        return new ItemStackModifierProvider(stack);
    }

}