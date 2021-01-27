package com.teamacronymcoders.essence.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;

public class KeenModifier extends ItemArrowCoreModifier {

  public KeenModifier() {
    super(4);
  }

  @Override
  public void onCollide(ModifiableArrowEntity modifiableArrowEntity, PlayerEntity shooter, BlockRayTraceResult result, ModifierInstance instance) {}

  @Override
  public void alterArrowEntity(ModifiableArrowEntity modifiableArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance instance) {
    if (velocity >= (1f - (0.25f * instance.getLevel()))) {
      modifiableArrowEntity.setIsCritical(true);
    }
  }

  @Override
  public boolean isCompatibleWith(IModifier modifier) {
    return !(modifier instanceof KeenModifier);
  }

}
