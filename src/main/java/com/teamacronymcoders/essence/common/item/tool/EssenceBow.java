package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.common.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.common.util.EssenceTags.EssenceItemTags;
import com.teamacronymcoders.essence.common.util.helper.EssenceBowHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
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

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public EssenceBow(Properties properties, EssenceToolTiers tier) {
        super(properties.durability(tier.getUses()).rarity(tier.getRarity()));
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

    /**
     * If you're a Mod-Author and reading this, Hi.
     * If you wish to have your inventory item (Backpack, Satchel, Quiver, Dank Null, etc...) support providing arrows to my bow.
     * Then simply just add the tag of "essence:ammo_holder" to your item, and I'll be able to loop through the ItemHandlerCapability.
     *
     * @param player Player to get Ammo from.
     * @return Returns the Ammo.
     */
    public ItemStack findAmmo(Player player) {
        Predicate<ItemStack> predicate = getSupportedHeldProjectiles();
        ItemStack stack = getHeldProjectile(player, predicate);
        if (!stack.isEmpty()) {
            return stack;
        } else {
            for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack1 = player.getInventory().getItem(i);
                if (predicate.test(itemstack1)) {
                    return itemstack1;
                }
                if (itemstack1.is(EssenceItemTags.AMMO_HOLDER)) {
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
            return player.getAbilities().instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
        }
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void releaseUsing(ItemStack bow, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0;
            ItemStack arrow = findAmmo(player);

            int i = this.getUseDuration(bow) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, level, player, i, !arrow.isEmpty() || flag);
            if (i < 0) {
                return;
            }

            if (!arrow.isEmpty() || flag) {
                if (arrow.isEmpty()) {
                    arrow = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double) f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));
                    if (!level.isClientSide()) {
                        ModifiableArrowEntity modifiableArrowEntity = EssenceBowHelper.getArrowEntity(level, bow, arrow, player, f);
                        EssenceBowHelper.modifyArrowEntityWithEnchantments(modifiableArrowEntity, bow);
                        bow.hurtAndBreak(1, player, (entity) -> {
                            entity.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        level.addFreshEntity(modifiableArrowEntity);
                    }
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (Essence.RANDOM.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        arrow.shrink(1);
                        if (arrow.isEmpty()) {
                            player.getInventory().removeItem(arrow);
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #use(Level, Player, InteractionHand)}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        boolean flag = !findAmmo(playerIn).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, level, playerIn, handIn, flag);
        if (ret != null) {
            return ret;
        }

        if (!playerIn.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
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
            return getAttributeModifiersFromModifiers(attributeModifiers, slot, stack);
        }
        return HashMultimap.create();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        addInformationFromModifiers(stack, level, tooltip, flagIn, tier);
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        return InteractionResult.PASS;
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
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (!stack.isEmpty() && nbt != null) {
            return new ItemStackModifierProvider(stack, nbt);
        }
        return new ItemStackModifierProvider(stack);
    }

    @Override
    public EssenceToolTiers getTier() {
        return tier;
    }
}
