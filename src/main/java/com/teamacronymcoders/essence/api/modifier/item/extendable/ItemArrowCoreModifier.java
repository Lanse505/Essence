package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;

public abstract class ItemArrowCoreModifier extends ItemCoreModifier {

  public ItemArrowCoreModifier() {
    this(1);
  }

  public ItemArrowCoreModifier(int maxLevel) {
    this(maxLevel, 0);
  }

  public ItemArrowCoreModifier(int minLevel, int maxLevel) {
    super(minLevel, maxLevel);
  }

  public abstract void onCollide(ModifiableArrowEntity abstractArrowEntity, PlayerEntity shooter, BlockRayTraceResult result, ModifierInstance instance);

  public abstract void alterArrowEntity(ModifiableArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance instance);
}
