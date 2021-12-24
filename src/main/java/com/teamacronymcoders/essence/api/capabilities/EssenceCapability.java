package com.teamacronymcoders.essence.api.capabilities;

import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class EssenceCapability {
  public static Capability<IKnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {});
}
