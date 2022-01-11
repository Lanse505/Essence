package com.teamacronymcoders.essence.api.capabilities;

import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class EssenceCapability {
    public static final Capability<IKnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER = CapabilityManager.get(new CapabilityToken<>() {
    });
}
