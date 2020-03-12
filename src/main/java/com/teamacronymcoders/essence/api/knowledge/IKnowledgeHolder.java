package com.teamacronymcoders.essence.api.knowledge;

import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IKnowledgeHolder extends INBTSerializable<ListNBT> {
    void addKnowledge(Knowledge... knowledge);
    void removeKnowledge(Knowledge... knowledge);
    void clearKnowledge();
}
