package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AttackDamageModifier extends ItemAttributeModifier {

  public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("dc3a5e97-4bbb-4b0f-9698-43fe2babb628");

  public AttackDamageModifier () {
    super(Attributes.ATTACK_DAMAGE, "attack_damage_modifier", ATTACK_DAMAGE_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
  }

  @Override
  public boolean canApplyOnObject (ItemStack object) {
    return true;
  }

  @Override
  public ITextComponent getTextComponentName (int level) {
    if (level == -1) {
      return new TranslationTextComponent("modifier.essence.attribute", new TranslationTextComponent("attribute.essence.attack_damage"));
    }
    return super.getTextComponentName(level);
  }

}
