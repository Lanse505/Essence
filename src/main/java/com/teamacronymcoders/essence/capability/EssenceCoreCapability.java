package com.teamacronymcoders.essence.capability;

import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCoreCapability {
  @CapabilityInject(ItemStackModifierHolder.class)
  public static Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER;
}
