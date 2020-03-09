package com.teamacronymcoders.essence.utils.tiers;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;

public enum EssenceBackpackTiers {
    ESSENCE("tier.essence.basic", Rarity.COMMON, 9, 3, 3),
    EMPOWERED_ESSENCE("tier.essence.empowered", Rarity.UNCOMMON, 18, 6, 3),
    EXALTED_ESSENCE("tier.essence.exalted", Rarity.RARE, 27, 9, 3),
    GODLY_ESSENCE("tier.essence.godly", Rarity.EPIC, 36, 9, 4);

    private final String localString;
    private final Rarity rarity;
    private final int backpackSlots;
    private final int backpackX;
    private final int backpackY;

    EssenceBackpackTiers(String localString, Rarity rarity, int backpackSlots, int backpackX, int backpackY) {
        this.localString = localString;
        this.rarity = rarity;
        this.backpackSlots = backpackSlots;
        this.backpackX = backpackX;
        this.backpackY = backpackY;
    }

    public String getLocalString() {
        return localString;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getBackpackSlots() {
        return backpackSlots;
    }

    public int getBackpackX() {
        return backpackX;
    }

    public int getBackpackY() {
        return backpackY;
    }
}
