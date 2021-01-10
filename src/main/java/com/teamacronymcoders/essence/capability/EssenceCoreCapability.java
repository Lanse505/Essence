package com.teamacronymcoders.essence.capability;

import com.teamacronymcoders.essence.capability.block.BlockModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.wrench.EntityStorageCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCoreCapability {
  @CapabilityInject(ItemStackModifierHolder.class)
  public static Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER;

  @CapabilityInject(BlockModifierHolder.class)
  public static Capability<BlockModifierHolder> BLOCK_MODIFIER_HOLDER;

  @CapabilityInject(EntityStorageCapability.class)
  public static Capability<EntityStorageCapability> ENTITY_STORAGE;
}
