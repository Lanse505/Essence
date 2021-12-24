package com.teamacronymcoders.essence.api.modifier.item.extendable;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

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

  public ItemEnchantmentCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, operation);
  }

  public ItemEnchantmentCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, maxLevel, operation);
  }

  public ItemEnchantmentCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, int minLevel, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, maxLevel, minLevel, operation);
  }

  public abstract Enchantment getLinkedEnchantment(ItemStack stack);
}
