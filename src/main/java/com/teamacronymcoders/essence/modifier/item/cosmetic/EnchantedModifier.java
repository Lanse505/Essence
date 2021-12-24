package com.teamacronymcoders.essence.modifier.item.cosmetic;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemCosmeticCoreModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EnchantedModifier extends ItemCosmeticCoreModifier {

  public EnchantedModifier() {
    super();
  }

  @Override
  public List<Component> getRenderedText(ModifierInstance instance) {
    super.getRenderedText(instance).add(0, super.getRenderedText(instance).get(0).copy().withStyle(ChatFormatting.LIGHT_PURPLE));
    return super.getRenderedText(instance);
  }

}
