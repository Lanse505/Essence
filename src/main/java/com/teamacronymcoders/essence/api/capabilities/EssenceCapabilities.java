package com.teamacronymcoders.essence.api.capabilities;

import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essence.core.impl.block.BlockModifierHolder;
import com.teamacronymcoders.essence.core.impl.itemstack.ItemStackModifierHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EssenceCapabilities {
    @CapabilityInject(IKnowledgeHolder.class)
    public static Capability<IKnowledgeHolder> KNOWLEDGE;

    @CapabilityInject(ItemStackModifierHolder.class)
    public static Capability<ItemStackModifierHolder> ITEMSTACK_MODIFIER_HOLDER;

    @CapabilityInject(BlockModifierHolder.class)
    public static Capability<BlockModifierHolder> BLOCK_MODIFIER_HOLDER;
}
