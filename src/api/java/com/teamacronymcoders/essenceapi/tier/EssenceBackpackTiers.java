package com.teamacronymcoders.essenceapi.tier;

import net.minecraft.world.item.Rarity;

public enum EssenceBackpackTiers implements IEssenceBaseTier {
    ESSENCE("tier.essence.basic", Rarity.COMMON, 3),
    EMPOWERED_ESSENCE("tier.essence.empowered", Rarity.UNCOMMON, 4),
    SUPREME_ESSENCE("tier.essence.supreme", Rarity.RARE, 5),
    DIVINE_ESSENCE("tier.essence.divine", Rarity.EPIC, 6);

    private final String localString;
    private final Rarity rarity;
    private final int freeModifiers;

    EssenceBackpackTiers(String localString, Rarity rarity, int freeModifiers) {
        this.localString = localString;
        this.rarity = rarity;
        this.freeModifiers = freeModifiers;
    }

    public String getLocalString() {
        return localString;
    }

    @Override
    public String getLocaleString() {
        return localString;
    }

    public Rarity getRarity() {
        return rarity;
    }

}
