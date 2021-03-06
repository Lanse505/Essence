package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MovementSpeedModifier extends ItemAttributeModifier {

  public static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("90742179-0f40-4ab8-8254-70ea451c9afb");

  public MovementSpeedModifier() {
    super(Attributes.MOVEMENT_SPEED, "movement_speed_modifier", MOVEMENT_SPEED_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
  }

  @Override
  public boolean canApplyOnObject(ItemStack object) {
    return true;
  }

  @Override
  public ITextComponent getTextComponentName(int level) {
    if (level == -1) {
      return new TranslationTextComponent("modifier.essence.attribute", new TranslationTextComponent("attribute.essence.movement_speed"));
    }
    return super.getTextComponentName(level);
  }

}
