package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ArmorModifier extends ItemAttributeModifier {

  public static final UUID ARMOR_UUID = UUID.fromString("fbe6f3d9-80bc-4160-b54c-5020b5a914bc");

  public ArmorModifier() {
    super(Attributes.ARMOR, "armor_modifier", ARMOR_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
  }

  @Override
  public Component getTextComponentName(int level) {
    if (level == -1) {
      return new TranslatableComponent("modifier.essence.attribute", new TranslatableComponent("attribute.essence.armor"));
    }
    return super.getTextComponentName(level);
  }
}
