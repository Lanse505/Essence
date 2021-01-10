package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;

public abstract class ItemArrowCoreModifier extends ItemCoreModifier {

  public ItemArrowCoreModifier () {
    this(1);
  }

  public ItemArrowCoreModifier (int maxLevel) {
    this(maxLevel, 0);
  }

  public ItemArrowCoreModifier (int maxLevel, int minLevel) {
    super(maxLevel, minLevel);
  }

  public abstract void alterArrowEntity (AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance<ItemStack> instance);
}
