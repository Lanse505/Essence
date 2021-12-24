package com.teamacronymcoders.essence.modifier.item.enchantment.strengthened;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public enum StrengthenedType {
  BANE_OF_ARTHROPODS(0, "arthropod", Enchantments.BANE_OF_ARTHROPODS, ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC),
  SHARPNESS(1, "sharpness", Enchantments.SHARPNESS, ChatFormatting.WHITE, ChatFormatting.ITALIC),
  SMITE(2, "smite", Enchantments.SMITE, ChatFormatting.YELLOW, ChatFormatting.ITALIC),
  POWER(3, "power", Enchantments.POWER_ARROWS, ChatFormatting.GOLD, ChatFormatting.ITALIC);

  private static final StrengthenedType[] VALUES = new StrengthenedType[] {BANE_OF_ARTHROPODS, SHARPNESS, SMITE, POWER};
  private final int id;
  private final String name;
  private final Enchantment enchantment;
  private final ChatFormatting[] textFormatting;

  StrengthenedType(int id, String name, Enchantment enchantment, ChatFormatting... textFormatting) {
    this.id = id;
    this.name = name;
    this.enchantment = enchantment;
    this.textFormatting = textFormatting;
  }

  public static StrengthenedType byID(int id) {
    if (id >= 0 && id < VALUES.length) {
      return VALUES[id];
    } else {
      throw new IllegalArgumentException("No type with value " + id);
    }
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Enchantment getEnchantment() {
    return enchantment;
  }

  public ChatFormatting[] getTextFormatting() {
    return textFormatting;
  }
}
