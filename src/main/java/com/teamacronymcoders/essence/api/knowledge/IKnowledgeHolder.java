package com.teamacronymcoders.essence.api.knowledge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IKnowledgeHolder extends INBTSerializable<ListNBT> {
    void addKnowledge(PlayerEntity player, Knowledge<?>... knowledge);

    void removeKnowledge(PlayerEntity player, Knowledge<?>... knowledge);

    void clearKnowledge();

    Knowledge<?>[] getKnowledge();
}
