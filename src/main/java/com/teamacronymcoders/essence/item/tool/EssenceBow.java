package com.teamacronymcoders.essence.item.tool;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.util.EssenceTags;
import com.teamacronymcoders.essence.util.helper.EssenceBowHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class EssenceBow extends BowItem implements IModifiedTool {

    private final EssenceToolTiers tier;
    private final int baseModifiers;
    private int freeModifiers;
    private int additionalModifiers;

    public EssenceBow(EssenceToolTiers tier) {
        super(new Item.Properties().group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.tier = tier;
        this.baseModifiers = tier.getFreeModifiers();
        this.freeModifiers = tier.getFreeModifiers();
        this.additionalModifiers = 0;
        this.addPropertyOverride(new ResourceLocation(Essence.MODID, "pull"), (stack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return !(livingEntity.getActiveItemStack().getItem() instanceof EssenceBow) ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation(Essence.MODID, "pulling"), (stack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack().getItem() instanceof EssenceBow ? 1.0F : 0.0F);
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

    /**
     * If you're a Mod-Author and reading this, Hi.
     * If you wish to have your inventory item (Backpack, Satchel, Quiver, Dank Null, etc...) support providing arrows to my bow.
     * Then simply just add the tag of "essence:ammo_holder" to your item, and I'll be able to loop through the ItemHandlerCapability.
     *
     * @param player Player to get Ammo from.
     * @return Returns the Ammo.
     */
    public ItemStack findAmmo(PlayerEntity player) {
        Predicate<ItemStack> predicate = getAmmoPredicate();
        ItemStack stack = getHeldAmmo(player, predicate);
        if (!stack.isEmpty()) {
            return stack;
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack1 = player.inventory.getStackInSlot(i);
                if (predicate.test(itemstack1)) {
                    return itemstack1;
                }
                if (itemstack1.getItem().isIn(EssenceTags.Items.AMMO_HOLDER)) {
                    if (itemstack1.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                        LazyOptional<IItemHandler> handler = itemstack1.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                        return handler.map(stackHandler -> {
                            for (int j = 0; j < stackHandler.getSlots(); j++) {
                                ItemStack internalStack = stackHandler.getStackInSlot(j);
                                if (predicate.test(internalStack)) {
                                    return internalStack;
                                }
                            }
                            return ItemStack.EMPTY;
                        }).orElse(ItemStack.EMPTY);
                    }
                }
            }
            return player.abilities.isCreativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
        }
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack bow, World world, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            boolean flag = player.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
            ItemStack arrow = findAmmo(player);

            int i = this.getUseDuration(bow) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, world, player, i, !arrow.isEmpty() || flag);
            if (i < 0) {
                return;
            }

            if (!arrow.isEmpty() || flag) {
                if (arrow.isEmpty()) {
                    arrow = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double) f < 0.1D)) {
                    boolean flag1 = player.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));
                    if (!world.isRemote) {
                        AbstractArrowEntity abstractarrowentity = EssenceBowHelper.getArrowEntity(world, bow, arrow, player, f);
                        EssenceBowHelper.modifyArrowEntityWithEnchantments(abstractarrowentity, bow);
                        bow.damageItem(1, player, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(player.getActiveHand());
                        });
                        world.addEntity(abstractarrowentity);
                    }
                    world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.abilities.isCreativeMode) {
                        arrow.shrink(1);
                        if (arrow.isEmpty()) {
                            player.inventory.deleteStack(arrow);
                        }
                    }
                    player.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) {
            return ret;
        }

        if (!playerIn.abilities.isCreativeMode && !flag) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
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

    @SuppressWarnings("deprecation")
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return getAttributeModifiersFromModifiers(getAttributeModifiers(slot), slot, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        addInformationFromModifiers(stack, worldIn, tooltip, flagIn, tier);
    }

    @Override
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        return ActionResultType.PASS;
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
        freeModifiers -= decrease;
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
