package com.teamacronymcoders.essence.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.items.tools.EssenceBow;
import com.teamacronymcoders.essence.items.tools.EssenceSword;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class KnockbackModifier extends ItemEnchantmentCoreModifier {

    public KnockbackModifier() {
        super(3);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance, 0);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return stack.getItem() instanceof EssenceBow ? Enchantments.PUNCH : Enchantments.KNOCKBACK;
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword || stack.getItem() instanceof EssenceBow;
    }


}
