package com.teamacronymcoders.essence.utils.tiers;

import net.minecraft.item.Rarity;

public interface IEssenceBaseTier {
    String getLocaleString();
    Rarity getRarity();
    int getFreeModifiers();
}
