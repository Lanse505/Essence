package com.teamacronymcoders.essence.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class EssenceShear extends ShearsItem implements IModifiedTool {

    private final int baseModifiers;
    private EssenceToolTiers tier;
    private int freeModifiers;
    private int additionalModifiers;
    private int rainbowVal = 0;

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public EssenceShear(EssenceToolTiers tier) {
        super(new Item.Properties().maxDamage(tier.getMaxUses()).group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.tier = tier;
        this.baseModifiers = tier.getFreeModifiers();
        this.freeModifiers = tier.getFreeModifiers();
        this.additionalModifiers = 0;

        this.attributeModifiers = HashMultimap.create();
    }

    @Override
    public Rarity getRarity(ItemStack p_77613_1_) {
        return tier.getRarity();
    }

    private static List<ItemStack> handleShearingSheep(SheepEntity sheep, ItemStack item, IWorld world, BlockPos pos, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        if (!sheep.world.isRemote) {
            sheep.setSheared(true);
            int i = EssenceUtilHelper.nextIntInclusive(1 + fortune, 4 + fortune);
            for (int j = 0; j < i; ++j) {
                ret.add(new ItemStack(SheepEntity.WOOL_BY_COLOR.get(sheep.getFleeceColor())));
            }
        }
        sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
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
        if (slot == EquipmentSlotType.MAINHAND) return getAttributeModifiersFromModifiers(attributeModifiers, slot, stack);
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
    @SuppressWarnings("deprecation")
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand) {
        if (sheared.world.isRemote) {
            return ActionResultType.FAIL;
        }
        if (sheared instanceof IForgeShearable) {
            IForgeShearable target = (IForgeShearable) sheared;
            BlockPos pos = new BlockPos(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
            if (target.isShearable(stack, sheared.world, pos)) {
                List<ItemStack> dropList = sheared instanceof SheepEntity ? handleShearingSheep((SheepEntity) sheared, stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)) : target.onSheared(player, stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

                // Gathers a list of Entries with InteractionCoreModifiers that also return a different value than the default
                List<? extends ModifierInstance<?>> unchecked = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());
                List<ModifierInstance<ItemStack>> matchingEntries = new ArrayList<>();
                for (ModifierInstance<?> instance : unchecked) {
                    if (instance.getType() == ItemStack.class) {
                        matchingEntries.add((ModifierInstance<ItemStack>) instance);
                    }
                }

                // Loops over and Gathers the final modified list
                for (ModifierInstance<ItemStack> instance : matchingEntries) {
                    if (instance.getModifier() instanceof ItemInteractionCoreModifier) {
                        ItemInteractionCoreModifier interactionCoreModifier = (ItemInteractionCoreModifier) instance.getModifier();
                        dropList = interactionCoreModifier.onSheared(stack, player, sheared, hand, dropList, instance);
                    }
                }

                // Handle dropping the final list of ItemStacks
                dropList.forEach(s -> {
                    ItemEntity entity = sheared.entityDropItem(s);
                    entity.setMotion(entity.getMotion().add(
                        (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                        Essence.RANDOM.nextFloat() * 0.05F,
                        (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
                });

                // Damage the ItemStack
                stack.damageItem(1, sheared, entity -> entity.sendBreakAnimation(hand));
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
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

}
