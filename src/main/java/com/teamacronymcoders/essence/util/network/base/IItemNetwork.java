package com.teamacronymcoders.essence.util.network.base;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.IWorld;


/**
 * Credit for most of this code goes to Mekanism.
 */
public interface IItemNetwork {
  void handlePacketData(IWorld world, ItemStack stack, PacketBuffer dataStream);
}
