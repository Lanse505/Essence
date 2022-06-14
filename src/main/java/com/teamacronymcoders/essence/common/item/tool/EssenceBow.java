package com.teamacronymcoders.essence.common.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.common.util.EssenceTags.EssenceItemTags;
import com.teamacronymcoders.essence.common.util.helper.EssenceBowHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.ChatFormatting;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class EssenceBow extends BowItem implements IModifiedItem {

    private final EssenceToolTiers tier;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public EssenceBow(Properties properties, EssenceToolTiers tier) {
        super(properties.durability(tier.getMaxUsesBow()).rarity(tier.getRarity()));
        this.tier = tier;
        this.attributeModifiers = HashMultimap.create();
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
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
                ItemStack containerStack = player.getInventory().getItem(i);
                if (predicate.test(containerStack)) {
                    return containerStack;
                }
                if (containerStack.m_204117_(EssenceItemTags.AMMO_HOLDER)) {
                    if (containerStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                        LazyOptional<IItemHandler> handler = containerStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
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
    public void releaseUsing(@NotNull ItemStack bow, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
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
                    if(!player.level.isClientSide){
                        ModifiableArrowEntity modifiableArrowEntity = EssenceBowHelper.getArrowEntity(level, bow, arrow, player, f);
                        EssenceBowHelper.modifyArrowEntityWithEnchantments(modifiableArrowEntity, bow);
                        bow.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));
                        level.addFreshEntity(modifiableArrowEntity);
                        EssenceBowHelper.alterFinalEntity(player, modifiableArrowEntity, bow);
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
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player playerIn, @NotNull InteractionHand handIn) {
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
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(Component.translatable(tier.getLocaleString()).withStyle(tier.getRarity().color)));
        addInformationFromModifiers(stack, level, tooltip, flagIn);
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        return InteractionResult.PASS;
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
