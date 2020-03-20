package com.teamacronymcoders.essence.modifier.item.rewrite.enchantment.strengthened;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.text.TextFormatting;

public enum StrengthenedType {
    BANE_OF_ARTHROPODS(0, "arthropod", Enchantments.BANE_OF_ARTHROPODS, TextFormatting.DARK_PURPLE, TextFormatting.ITALIC),
    SHARPNESS(1, "sharpness", Enchantments.SHARPNESS, TextFormatting.WHITE, TextFormatting.ITALIC),
    SMITE(2, "smite", Enchantments.SMITE, TextFormatting.YELLOW, TextFormatting.ITALIC),
    POWER(3, "power", Enchantments.POWER, TextFormatting.GOLD, TextFormatting.ITALIC);

    private static final StrengthenedType[] VALUES = new StrengthenedType[]{BANE_OF_ARTHROPODS, SHARPNESS, SMITE, POWER};
    private final int id;
    private final String name;
    private final Enchantment enchantment;
    private final TextFormatting[] textFormatting;

    StrengthenedType(int id, String name, Enchantment enchantment, TextFormatting... textFormatting) {
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

    public TextFormatting[] getTextFormatting() {
        return textFormatting;
    }
}
