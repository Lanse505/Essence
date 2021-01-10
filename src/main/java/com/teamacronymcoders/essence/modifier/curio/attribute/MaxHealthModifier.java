package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MaxHealthModifier extends ItemAttributeModifier {

  public static final UUID MAX_HEALTH_UUID = UUID.fromString("baa36be4-749d-42f0-8e4f-a89959a36edf");

  public MaxHealthModifier () {
    super(Attributes.MAX_HEALTH, "max_health_modifier", MAX_HEALTH_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
  }

  @Override
  public boolean canApplyOnObject (ItemStack object) {
    return true;
  }

  @Override
  public ITextComponent getTextComponentName (int level) {
    if (level == -1) {
      return new TranslationTextComponent("modifier.essence.attribute", new TranslationTextComponent("attribute.essence.max_health"));
    }
    return super.getTextComponentName(level);
  }

}
