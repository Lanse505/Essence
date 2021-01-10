package com.teamacronymcoders.essence.api.modifier.item.extendable;

import java.util.UUID;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public abstract class ItemEnchantmentCoreModifier extends ItemInteractionCoreModifier {

  public ItemEnchantmentCoreModifier () {
    super(1);
  }

  public ItemEnchantmentCoreModifier (int maxLevel) {
    super(maxLevel);
  }

  public ItemEnchantmentCoreModifier (int maxLevel, int minLevel) {
    super(maxLevel, minLevel);
  }

  public ItemEnchantmentCoreModifier (Attribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, operation);
  }

  public ItemEnchantmentCoreModifier (Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, maxLevel, operation);
  }

  public ItemEnchantmentCoreModifier (Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, int minLevel, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, maxLevel, minLevel, operation);
  }

  public abstract Enchantment getLinkedEnchantment (ItemStack stack);
}
