package com.teamacronymcoders.essence.util;

import com.teamacronymcoders.essence.item.tool.EssenceBow;
import net.minecraft.client.renderer.item.ItemPropertyFunction;

public class EssenceItemProperties {
  public static final ItemPropertyFunction PULL = (stack, world, living, seed) -> {
    if (living == null) {
      return 0.0F;
    } else {
      return !(living.getUseItem().getItem() instanceof EssenceBow) ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
    }
  };
  public static final ItemPropertyFunction PULLING = (stack, world, living, seed) -> living != null && living.isUsingItem() && living.getUseItem().getItem() instanceof EssenceBow ? 1.0F : 0.0F;
  public static final ItemPropertyFunction TOGGLED = (stack, world, living, seed) -> stack.hasTag() && stack.getTag() != null && stack.getTag().contains("enabled") && stack.getTag().getBoolean("enabled") ? 1f : 0f;
}
