package com.teamacronymcoders.essence.util.tier;

import com.teamacronymcoders.essence.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.item.Rarity;

import java.util.function.Supplier;

public enum EssenceItemTiers implements IEssenceBaseTier {
    ESSENCE("tier.essence.basic", Rarity.COMMON, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL),
    EMPOWERED_ESSENCE("tier.essence.empowered", Rarity.UNCOMMON, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED),
    SUPREME_ESSENCE("tier.essence.supreme", Rarity.RARE, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME),
    GODLY_ESSENCE("tier.essence.godly", Rarity.EPIC, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY);

    private final String localString;
    private final Rarity rarity;
    private final Supplier<EssenceNuggetItem> nugget;
    private final Supplier<EssenceIngotItem> ingot;

    EssenceItemTiers(String localString, Rarity rarity, Supplier<EssenceNuggetItem> nugget, Supplier<EssenceIngotItem> ingot) {
        this.localString = localString;
        this.rarity = rarity;
        this.nugget = nugget;
        this.ingot = ingot;
    }

    @Override
    public String getLocaleString() {
        return localString;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public int getFreeModifiers() {
        return 0;
    }

    public Supplier<EssenceNuggetItem> getNugget() {
        return nugget;
    }

    public Supplier<EssenceIngotItem> getIngot() {
        return ingot;
    }
}
