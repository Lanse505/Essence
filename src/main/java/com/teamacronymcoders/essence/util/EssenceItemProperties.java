package com.teamacronymcoders.essence.util;

import com.teamacronymcoders.essence.item.tool.EssenceBow;
import net.minecraft.item.IItemPropertyGetter;

public class EssenceItemProperties {
  public static final IItemPropertyGetter PULL = (stack, world, living) -> {
    if (living == null) {
      return 0.0F;
    } else {
      return !(living.getActiveItemStack().getItem() instanceof EssenceBow) ? 0.0F : (float) (stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
    }
  };
  public static final IItemPropertyGetter PULLING = (stack, world, living) -> living != null && living.isHandActive() && living.getActiveItemStack().getItem() instanceof EssenceBow ? 1.0F : 0.0F;
  public static final IItemPropertyGetter TOGGLED = (stack, world, living) -> stack.hasTag() && stack.getTag() != null && stack.getTag().contains("enabled") && stack.getTag().getBoolean("enabled") ? 1f : 0f;
}
