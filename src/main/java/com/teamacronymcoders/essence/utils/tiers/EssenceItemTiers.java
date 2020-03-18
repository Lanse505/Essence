package com.teamacronymcoders.essence.utils.tiers;

import com.teamacronymcoders.essence.items.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.items.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.item.Rarity;

import java.util.function.Supplier;

public enum EssenceItemTiers implements IEssenceBaseTier {
    ESSENCE("tier.essence.basic", Rarity.COMMON, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL),
    EMPOWERED_ESSENCE("tier.essence.empowered", Rarity.UNCOMMON, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED),
    EXALTED_ESSENCE("tier.essence.exalted", Rarity.RARE, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED_NUGGET, () -> EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED),
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
