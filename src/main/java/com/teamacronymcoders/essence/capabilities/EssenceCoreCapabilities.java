package com.teamacronymcoders.essence.capabilities;

import com.teamacronymcoders.essence.capabilities.block.BlockModifierHolder;
import com.teamacronymcoders.essence.capabilities.itemstack.ItemStackModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCoreCapabilities {
    @CapabilityInject(ItemStackModifierHolder.class)
    public static Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER;

    @CapabilityInject(BlockModifierHolder.class)
    public static Capability<BlockModifierHolder> BLOCK_MODIFIER_HOLDER;
}
