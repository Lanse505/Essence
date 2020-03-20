package com.teamacronymcoders.essence.modifier.item.interaction;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.items.tools.EssenceBow;
import com.teamacronymcoders.essence.items.tools.EssenceHoe;
import com.teamacronymcoders.essence.items.tools.EssenceSword;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FieryModifier extends ItemEnchantmentCoreModifier {

    public FieryModifier() {
        super(1);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        if (stack.getItem() instanceof EssenceSword || stack.getItem() instanceof EssenceBow) {
            EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance, 2);
        }
    }

    @Override
    public int getMaxLevel(ItemStack stack) {
        return (stack.getItem() instanceof EssenceBow || stack.getItem() instanceof EssenceSword) ? 5 : 1;
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return stack.getItem() instanceof EssenceBow ? Enchantments.FLAME : Enchantments.FIRE_ASPECT;
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return !(object.getItem() instanceof EssenceHoe);
    }

}
