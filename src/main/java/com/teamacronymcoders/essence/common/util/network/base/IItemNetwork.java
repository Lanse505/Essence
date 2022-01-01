package com.teamacronymcoders.essence.common.util.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;


/**
 * Credit for most of this code goes to Mekanism.
 */
public interface IItemNetwork {
    void handlePacketData(LevelAccessor accessor, ItemStack stack, FriendlyByteBuf dataStream);
}
