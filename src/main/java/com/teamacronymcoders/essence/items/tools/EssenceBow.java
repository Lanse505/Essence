package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.items.base.EssenceToolItem;
import com.teamacronymcoders.essence.utils.EssenceTags;
import com.teamacronymcoders.essence.utils.helpers.EssenceBowHelper;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Predicate;

public class EssenceBow extends EssenceToolItem {

    public static final Predicate<ItemStack> ARROWS = (p_220002_0_) -> p_220002_0_.getItem().isIn(ItemTags.ARROWS);

    public EssenceBow(EssenceToolTiers tier) {
        super(0f, 0f, tier, Sets.newHashSet(), new Item.Properties());
        this.addPropertyOverride(new ResourceLocation("pull"), (stack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return !(livingEntity.getActiveItemStack().getItem() instanceof EssenceBow) ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), (stack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack().getItem() instanceof EssenceBow ? 1.0F : 0.0F);
    }

    /**
     * If you're a Mod-Author and reading this, Hi.
     * If you wish to have your inventory item (Backpack, Satchel, Quiver, Dank Null, etc...) support providing arrows to my bow.
     * Then simply just add the tag of "essence:ammo_holder" to your item, and I'll be able to loop through the ItemHandlerCapability.
     * @param player Player to get Ammo from.
     * @return Returns the Ammo.
     */
    public ItemStack findAmmo(PlayerEntity player) {
        Predicate<ItemStack> predicate = getAmmoPredicate();
        ItemStack stack = getHeldAmmo(player, predicate);
        if (!stack.isEmpty()) {
            return stack;
        } else {
            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
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
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getArrowVelocity(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
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
        if (ret != null) return ret;

        if (!playerIn.abilities.isCreativeMode && !flag) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public static ItemStack getHeldAmmo(PlayerEntity living, Predicate<ItemStack> isAmmo) {
        if (isAmmo.test(living.getHeldItem(Hand.OFF_HAND))) {
            return living.getHeldItem(Hand.OFF_HAND);
        } else {
            return isAmmo.test(living.getHeldItem(Hand.MAIN_HAND)) ? living.getHeldItem(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    public Predicate<ItemStack> getAmmoPredicate() {
        return ARROWS;
    }

}
