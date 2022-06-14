package com.teamacronymcoders.essenceapi;

import com.teamacronymcoders.essenceapi.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essenceapi.modified.rewrite.itemstack.ItemStackModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class EssenceCapabilities {
  public static final Capability<IKnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {});
  public static final Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER = CapabilityManager.get(new CapabilityToken<>() {});
}
