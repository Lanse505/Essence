package com.teamacronymcoders.essence.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public class KeenModifier extends ItemArrowCoreModifier {

  public KeenModifier() {
    super(4);
  }

  @Override
  public void onCollide(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {}

  @Override
  public void alterArrowEntity(ModifiableArrowEntity modifiableArrowEntity, Player shooter, float velocity, ModifierInstance instance) {
    if (velocity >= (1f - (0.25f * instance.getLevel()))) {
      modifiableArrowEntity.setCritArrow(true);
    }
  }

  @Override
  public boolean isCompatibleWith(IModifier modifier) {
    return !(modifier instanceof KeenModifier);
  }

}
