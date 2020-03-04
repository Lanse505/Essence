package com.teamacronymcoders.essence.modifier.enchantment;

import com.teamacronymcoders.essence.api.modifier.EnchantmentCoreModifier;
import com.teamacronymcoders.essence.items.tools.EssenceBow;
import com.teamacronymcoders.essence.items.tools.EssenceSword;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class KnockbackModifier extends EnchantmentCoreModifier {

    public KnockbackModifier() {
        super(3);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, int level) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), level);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        if (stack.getItem() instanceof EssenceSword) {
            return Enchantments.KNOCKBACK;
        }
        if (stack.getItem() instanceof EssenceBow) {
            return Enchantments.PUNCH;
        }
        return super.getLinkedEnchantment(stack);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword || stack.getItem() instanceof EssenceBow;
    }
}
