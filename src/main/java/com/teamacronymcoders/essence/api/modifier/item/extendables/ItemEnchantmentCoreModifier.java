package com.teamacronymcoders.essence.api.modifier.item.extendables;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public abstract class ItemEnchantmentCoreModifier extends ItemInteractionCoreModifier {

    public ItemEnchantmentCoreModifier() {
        super(1);
    }

    public ItemEnchantmentCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    public ItemEnchantmentCoreModifier(int maxLevel, int minLevel) {
        super(maxLevel, minLevel);
    }

    public ItemEnchantmentCoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, operation);
    }

    public ItemEnchantmentCoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, maxLevel, operation);
    }

    public ItemEnchantmentCoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, int maxLevel, int minLevel, AttributeModifier.Operation operation) {
        super(attribute, identifier, uuid, amount, maxLevel, minLevel, operation);
    }

    public abstract Enchantment getLinkedEnchantment(ItemStack stack);
}
