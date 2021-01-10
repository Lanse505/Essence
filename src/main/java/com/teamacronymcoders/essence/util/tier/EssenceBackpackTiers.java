package com.teamacronymcoders.essence.util.tier;

import net.minecraft.item.Rarity;

public enum EssenceBackpackTiers implements IEssenceBaseTier {
  ESSENCE("tier.essence.basic", Rarity.COMMON, 3, 9, 3, 3),
  EMPOWERED_ESSENCE("tier.essence.empowered", Rarity.UNCOMMON, 4, 18, 6, 3),
  SUPREME_ESSENCE("tier.essence.supreme", Rarity.RARE, 5, 27, 9, 3),
  DIVINE_ESSENCE("tier.essence.divine", Rarity.EPIC, 6, 36, 9, 4);

  private final String localString;
  private final Rarity rarity;
  private final int freeModifiers;
  private final int backpackSlots;
  private final int backpackX;
  private final int backpackY;

  EssenceBackpackTiers (String localString, Rarity rarity, int freeModifiers, int backpackSlots, int backpackX, int backpackY) {
    this.localString = localString;
    this.rarity = rarity;
    this.freeModifiers = freeModifiers;
    this.backpackSlots = backpackSlots;
    this.backpackX = backpackX;
    this.backpackY = backpackY;
  }

  public String getLocalString () {
    return localString;
  }

  @Override
  public String getLocaleString () {
    return localString;
  }

  public Rarity getRarity () {
    return rarity;
  }

  @Override
  public int getFreeModifiers () {
    return freeModifiers;
  }

  public int getBackpackSlots () {
    return backpackSlots;
  }

  public int getBackpackX () {
    return backpackX;
  }

  public int getBackpackY () {
    return backpackY;
  }
}
