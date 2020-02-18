package com.teamacronymcoders.essence.api.modifier;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class EnchantmentCoreModifier extends InteractionCoreModifier {

    public EnchantmentCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return null;
    }
}
