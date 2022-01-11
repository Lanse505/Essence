package com.teamacronymcoders.essence.common.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceBow;
import com.teamacronymcoders.essence.common.item.tool.EssenceSword;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class KnockbackModifier extends ItemInteractionModifier {

    public KnockbackModifier() {
        super(1, 3);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return stack.getItem() instanceof EssenceBow ? Enchantments.PUNCH_ARROWS : Enchantments.KNOCKBACK;
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword || stack.getItem() instanceof EssenceBow;
    }


}
