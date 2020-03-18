package com.teamacronymcoders.essence.api.capabilities;

import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essence.api.tool.modifierholder.IModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCapabilities {
    @CapabilityInject(IKnowledgeHolder.class)
    public static Capability<IKnowledgeHolder> KNOWLEDGE;

    @CapabilityInject(IModifierHolder.class)
    public static Capability<IModifierHolder> MODIFIER_HOLDER;
}
