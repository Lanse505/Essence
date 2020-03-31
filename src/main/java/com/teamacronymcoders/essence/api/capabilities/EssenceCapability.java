package com.teamacronymcoders.essence.api.capabilities;

import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCapability {
    @CapabilityInject(IKnowledgeHolder.class)
    public static Capability<IKnowledgeHolder> KNOWLEDGE;
}
