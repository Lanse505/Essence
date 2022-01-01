package com.teamacronymcoders.essence.api.knowledge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public interface IKnowledgeHolder extends INBTSerializable<CompoundTag> {
    void addPlayerUUID(UUID player);

    void addPlayerUUID(Player player);

    void addKnowledge(UUID player, Knowledge... knowledge);

    void addKnowledge(Player player, Knowledge... knowledge);

    void removeKnowledge(UUID player, Knowledge... knowledge);

    void removeKnowledge(Player player, Knowledge... knowledge);

    void clearKnowledge(UUID player);

    void clearKnowledge(Player player);

    List<Knowledge> getKnowledgeAsList(UUID uuid);

    List<Knowledge> getKnowledgeAsList(Player player);

    Knowledge[] getKnowledgeAsArray(UUID uuid);

    Knowledge[] getKnowledgeAsArray(Player player);
}
