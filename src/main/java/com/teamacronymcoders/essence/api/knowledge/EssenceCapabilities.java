package com.teamacronymcoders.essence.api.knowledge;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCapabilities {
    @CapabilityInject(KnowledgeHolder.class)
    public static Capability<KnowledgeHolder> KNOWLEDGE;
}
