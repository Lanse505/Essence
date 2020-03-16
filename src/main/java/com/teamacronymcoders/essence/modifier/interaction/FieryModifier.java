package com.teamacronymcoders.essence.modifier.interaction;

import com.teamacronymcoders.essence.api.modifier.EnchantmentCoreModifier;
import com.teamacronymcoders.essence.api.tool.ModifierInstance;
import com.teamacronymcoders.essence.items.tools.EssenceBow;
import com.teamacronymcoders.essence.items.tools.EssenceHoe;
import com.teamacronymcoders.essence.items.tools.EssenceSword;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FieryModifier extends EnchantmentCoreModifier {

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
        if (stack.getItem() instanceof EssenceSword) {
            return Enchantments.FIRE_ASPECT;
        }
        if (stack.getItem() instanceof EssenceBow) {
            return Enchantments.FLAME;
        }
        return super.getLinkedEnchantment(stack);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return !(stack.getItem() instanceof EssenceHoe);
    }

}
