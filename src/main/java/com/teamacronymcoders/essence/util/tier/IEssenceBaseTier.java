package com.teamacronymcoders.essence.util.tier;

import net.minecraft.item.Rarity;

public interface IEssenceBaseTier {
  String getLocaleString();

  Rarity getRarity();

  int getFreeModifiers();
}
