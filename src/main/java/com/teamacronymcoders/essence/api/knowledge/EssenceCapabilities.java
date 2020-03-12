package com.teamacronymcoders.essence.api.knowledge;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCapabilities {
    @CapabilityInject(IKnowledgeHolder.class)
    public static Capability<IKnowledgeHolder> KNOWLEDGE;
}
