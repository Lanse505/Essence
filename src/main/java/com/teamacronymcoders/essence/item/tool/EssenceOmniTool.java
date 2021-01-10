package com.teamacronymcoders.essence.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants.BlockFlags;

public class EssenceOmniTool extends ToolItem implements IModifiedTool {

  private static final ImmutableSet<Block> EFFECTIVE_ON = ImmutableSet.<Block>builder().addAll(PickaxeItem.EFFECTIVE_ON).addAll(ShovelItem.EFFECTIVE_ON).addAll(AxeItem.EFFECTIVE_ON_BLOCKS).build();
  private final EssenceToolTiers tier;
  private final int baseModifiers;
  private int freeModifiers;
  private int additionalModifiers;

  public EssenceOmniTool (EssenceToolTiers tier) {
    super(tier.getAttackDamage(), tier.getEfficiency(), tier, EFFECTIVE_ON, new Item.Properties().group(Essence.TOOL_TAB).rarity(tier.getRarity())
            .addToolType(ToolType.AXE, tier.getHarvestLevel())
            .addToolType(ToolType.PICKAXE, tier.getHarvestLevel())
            .addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    this.tier = tier;
    this.baseModifiers = tier.getFreeModifiers();
    this.freeModifiers = tier.getFreeModifiers();
    this.additionalModifiers = 0;
  }

  @Override
  @ParametersAreNonnullByDefault
  public Rarity getRarity (ItemStack stack) {
    return tier.getRarity();
  }

  @Override
  public boolean canHarvestBlock (BlockState state) {
    if (state.getHarvestTool() == ToolType.AXE || state.getHarvestTool() == ToolType.PICKAXE || state.getHarvestTool() == ToolType.SHOVEL) {
      return getTier().getHarvestLevel() >= state.getHarvestLevel();
    }
    return super.canHarvestBlock(state);
  }

  public ActionResultType onItemBehaviour (ItemUseContext context) {
    World world = context.getWorld();
    Block block = world.getBlockState(context.getPos()).getBlock();
    ActionResultType result = world.getRecipeManager().getRecipes().stream()
            .filter(iRecipe -> iRecipe.getType() == AxeStrippingRecipe.SERIALIZER.getRecipeType())
            .map(iRecipe -> (AxeStrippingRecipe) iRecipe)
            .filter(recipe -> recipe.matches(block))
            .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
    if (result == ActionResultType.PASS) {
      result = world.getRecipeManager().getRecipes().stream()
              .filter(iRecipe -> iRecipe.getType() == ShovelPathingRecipe.SERIALIZER.getRecipeType())
              .map(iRecipe -> (ShovelPathingRecipe) iRecipe)
              .filter(recipe -> recipe.matches(block))
              .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
    }
    return result;
  }

  @Override
  @ParametersAreNonnullByDefault
  public ActionResultType onItemUse (ItemUseContext context) {
    World world = context.getWorld();
    PlayerEntity player = context.getPlayer();
    BlockPos pos = context.getPos();
    BlockState state = world.getBlockState(pos);
    ItemStack stack = context.getItem();
    ActionResultType resultType = ActionResultType.FAIL;
    BlockState behaviourState;

    // Check Vanilla Axe Behaviour
    behaviourState = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);
    if (behaviourState != null && !behaviourState.equals(state)) {
      world.setBlockState(pos, behaviourState, BlockFlags.DEFAULT_AND_RERENDER);
      resultType = ActionResultType.SUCCESS;
    }
    if (resultType == ActionResultType.SUCCESS) {
      return resultType;
    }

    // Check Pickaxe Behaviour
    behaviourState = state.getToolModifiedState(world, pos, player, stack, ToolType.PICKAXE);
    if (behaviourState != null && !behaviourState.equals(state)) {
      world.setBlockState(pos, behaviourState, BlockFlags.DEFAULT_AND_RERENDER);
      resultType = ActionResultType.SUCCESS;
    }
    if (resultType == ActionResultType.SUCCESS) {
      return resultType;
    }

    // Check Vanilla Shovel Behaviour
    behaviourState = state.getToolModifiedState(world, pos, player, stack, ToolType.SHOVEL);
    if (!behaviourState.equals(state)) {
      world.setBlockState(pos, behaviourState, BlockFlags.DEFAULT_AND_RERENDER);
      resultType = ActionResultType.SUCCESS;
    }
    if (resultType == ActionResultType.SUCCESS) {
      return resultType;
    }

    // Check Recipes
    resultType = onItemBehaviour(context);
    if (resultType == ActionResultType.SUCCESS) {
      return resultType;
    }

    // Fallback on Modifier Behaviour
    return onItemUseFromModifiers(context).orElse(resultType);
  }

  @Override
  public ActionResultType onItemUseModified (ItemUseContext context, boolean isRecursive) {
    if (isRecursive) {
      return onItemBehaviour(context);
    }
    return onItemUse(context);
  }

  @Override
  @ParametersAreNonnullByDefault
  public boolean isEnchantable (ItemStack stack) {
    return false;
  }

  @Override
  public boolean isBookEnchantable (ItemStack stack, ItemStack book) {
    return false;
  }

  @Override
  @ParametersAreNonnullByDefault
  public boolean isRepairable (ItemStack stack) {
    return false;
  }

  @Override
  @ParametersAreNonnullByDefault
  public boolean hasEffect (ItemStack stack) {
    return EssenceItemstackModifierHelpers.hasEnchantedModifier(stack);
  }

  @Override
  public int getMaxDamage (ItemStack stack) {
    return super.getMaxDamage(stack) + getMaxDamageFromModifiers(stack, tier);
  }

  @Override
  @ParametersAreNonnullByDefault
  public float getDestroySpeed (ItemStack stack, BlockState state) {
    return super.getDestroySpeed(stack, state) + getDestroySpeedFromModifiers(super.getDestroySpeed(stack, state), stack);
  }

  @Override
  @ParametersAreNonnullByDefault
  public int getHarvestLevel (ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
    return super.getHarvestLevel(stack, tool, player, blockState) + getHarvestLevelFromModifiers(super.getHarvestLevel(stack, tool, player, blockState), stack);
  }

  @Override
  @ParametersAreNonnullByDefault
  public boolean hitEntity (ItemStack stack, LivingEntity target, LivingEntity attacker) {
    hitEntityFromModifiers(stack, target, attacker);
    return super.hitEntity(stack, target, attacker);
  }

  @Override
  @ParametersAreNonnullByDefault
  public boolean onBlockDestroyed (ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
    onBlockDestroyedFromModifiers(stack, worldIn, state, pos, entityLiving);
    return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
  }

  @Override
  @ParametersAreNonnullByDefault
  public void inventoryTick (ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    inventoryTickFromModifiers(stack, worldIn, entityIn, itemSlot, isSelected);
    super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
  }

  @Override
  public Multimap<Attribute, AttributeModifier> getAttributeModifiers (EquipmentSlotType slot, ItemStack stack) {
    if (slot == EquipmentSlotType.MAINHAND) {
      return getAttributeModifiersFromModifiers(getAttributeModifiers(slot), slot, stack);
    }
    return HashMultimap.create();
  }

  @Override
  @ParametersAreNonnullByDefault
  public void addInformation (ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    addInformationFromModifiers(stack, worldIn, tooltip, flagIn, tier);
  }

  @Override
  public void addModifierWithoutIncreasingAdditional (int increase) {
    freeModifiers += increase;
  }

  @Override
  public void increaseFreeModifiers (int increase) {
    freeModifiers += increase;
    additionalModifiers += increase;
  }

  @Override
  public boolean decreaseFreeModifiers (int decrease) {
    if (freeModifiers - decrease < 0) {
      return false;
    }
    freeModifiers = freeModifiers - decrease;
    return true;
  }

  @Override
  public int getFreeModifiers () {
    return freeModifiers;
  }

  @Override
  public int getMaxModifiers () {
    return baseModifiers + additionalModifiers;
  }

  @Override
  public boolean recheck (ItemStack object, List<ModifierInstance<ItemStack>> modifierInstances) {
    int cmc = 0;
    for (ModifierInstance<ItemStack> instance : modifierInstances) {
      if (instance.getModifier() instanceof ItemCoreModifier) {
        cmc += instance.getModifier().getModifierCountValue(instance.getLevel(), object);
      }
    }
    return cmc <= baseModifiers + additionalModifiers;
  }

  @Override
  public Class<ItemStack> getType () {
    return ItemStack.class;
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt) {
    if (!stack.isEmpty() && nbt != null) {
      return new ItemStackModifierProvider(stack, nbt);
    }
    return new ItemStackModifierProvider(stack);
  }

}
