package com.teamacronymcoders.essence.common.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceBow;
import com.teamacronymcoders.essence.common.item.tool.EssenceHoe;
import com.teamacronymcoders.essence.common.item.tool.EssenceSword;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class FieryModifier extends ItemInteractionModifier {

    public FieryModifier() {
        super();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
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
        return stack.getItem() instanceof EssenceBow ? Enchantments.FLAMING_ARROWS : Enchantments.FIRE_ASPECT;
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return !(object.getItem() instanceof EssenceHoe);
    }

}
