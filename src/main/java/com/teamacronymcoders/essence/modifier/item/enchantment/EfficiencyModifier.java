package com.teamacronymcoders.essence.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.item.tool.EssenceAxe;
import com.teamacronymcoders.essence.item.tool.EssenceOmniTool;
import com.teamacronymcoders.essence.item.tool.EssencePickaxe;
import com.teamacronymcoders.essence.item.tool.EssenceShovel;
import com.teamacronymcoders.essence.util.helper.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EfficiencyModifier extends ItemEnchantmentCoreModifier {

    public EfficiencyModifier() {
        super(5);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance<ItemStack> instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return Enchantments.EFFICIENCY;
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof EssenceAxe || item instanceof EssencePickaxe || item instanceof EssenceShovel || item instanceof EssenceOmniTool;
    }

}
