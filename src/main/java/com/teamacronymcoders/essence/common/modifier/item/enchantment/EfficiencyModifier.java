package com.teamacronymcoders.essence.common.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceAxe;
import com.teamacronymcoders.essence.common.item.tool.EssenceOmniTool;
import com.teamacronymcoders.essence.common.item.tool.EssencePickaxe;
import com.teamacronymcoders.essence.common.item.tool.EssenceShovel;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class EfficiencyModifier extends ItemEnchantmentCoreModifier {

    public EfficiencyModifier() {
        super(5);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return Enchantments.BLOCK_EFFICIENCY;
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof EssenceAxe || item instanceof EssencePickaxe || item instanceof EssenceShovel || item instanceof EssenceOmniTool;
    }

}
