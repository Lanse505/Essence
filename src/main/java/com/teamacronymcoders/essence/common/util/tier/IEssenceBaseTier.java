package com.teamacronymcoders.essence.common.util.tier;

import net.minecraft.world.item.Rarity;

public interface IEssenceBaseTier {
    String getLocaleString();

    Rarity getRarity();

    int getFreeModifiers();
}
