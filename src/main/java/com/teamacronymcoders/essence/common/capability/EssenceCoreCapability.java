package com.teamacronymcoders.essence.common.capability;

import com.teamacronymcoders.essence.common.capability.itemstack.modifier.ItemStackModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class EssenceCoreCapability {
    public static Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER = CapabilityManager.get(new CapabilityToken<>() {
    });
}
