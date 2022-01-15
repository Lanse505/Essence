package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceAxe;
import com.teamacronymcoders.essence.common.item.tool.EssenceOmniTool;
import com.teamacronymcoders.essence.common.item.tool.EssencePickaxe;
import com.teamacronymcoders.essence.common.item.tool.EssenceShovel;
import net.minecraft.world.item.ItemStack;

public class MagneticModifier extends ItemInteractionModifier {

  @Override
  public boolean canApplyTogether(ItemStack target, IModifier<ItemStack> modifier) {
    return !(modifier instanceof MagneticModifier);
  }

  @Override
  public boolean canApplyOnObject(ItemStack target) {
    return target.getItem() instanceof EssenceAxe || target.getItem() instanceof EssenceOmniTool ||
            target.getItem() instanceof EssencePickaxe || target.getItem() instanceof EssenceShovel;
  }
}
