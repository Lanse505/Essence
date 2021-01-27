package com.teamacronymcoders.essence.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceShearingHelper;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class EssenceShear extends ShearsItem implements IModifiedTool {

  private final int baseModifiers;
  private final EssenceToolTiers tier;
  private int freeModifiers;
  private int additionalModifiers;
  private int rainbowVal = 0;

  private final Multimap<Attribute, AttributeModifier> attributeModifiers;

  public EssenceShear(Properties properties, EssenceToolTiers tier) {
    super(properties.maxDamage(tier.getMaxUses()).rarity(tier.getRarity()));
    this.tier = tier;
    this.baseModifiers = tier.getFreeModifiers();
    this.freeModifiers = tier.getFreeModifiers();
    this.additionalModifiers = 0;

    this.attributeModifiers = HashMultimap.create();
  }

  @Override
  public Rarity getRarity(ItemStack stack) {
    return tier.getRarity();
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

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    if (!stack.isEmpty() && nbt != null) {
      return new ItemStackModifierProvider(stack, nbt);
    }
    return new ItemStackModifierProvider(stack);
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
    int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
    return harvestLevel + getHarvestLevelFromModifiers(harvestLevel, stack);
  }

  @Override
  public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
    if (slot == EquipmentSlotType.MAINHAND) {
      return getAttributeModifiersFromModifiers(attributeModifiers, slot, stack);
    }
    return HashMultimap.create();
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    Optional<ActionResultType> modifierResult = onItemUseFromModifiers(context);
    return super.onItemUse(context) == ActionResultType.SUCCESS ? super.onItemUse(context) : modifierResult.orElse(super.onItemUse(context));
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
  public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand) {
    if (sheared.world.isRemote) {
      return ActionResultType.FAIL;
    }
    if (sheared instanceof IForgeShearable) {
      return EssenceShearingHelper.handleIShearableEntity(stack, player, sheared, hand);
    }
    return EssenceShearingHelper.handleHardcodedEntity(stack, player, sheared, hand);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
    super.addInformation(stack, world, list, flag);
    addInformationFromModifiers(stack, world, list, flag, tier);
  }

  @Override
  public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
    if (isRecursive) {
      return ActionResultType.PASS;
    }
    return onItemUse(context);
  }

  public int getRainbowVal() {
    return rainbowVal;
  }

  public void setRainbowVal(int rainbowVal) {
    this.rainbowVal = rainbowVal;
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

}
