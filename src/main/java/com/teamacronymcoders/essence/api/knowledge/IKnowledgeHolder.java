package com.teamacronymcoders.essence.api.knowledge;

import java.util.List;
import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IKnowledgeHolder extends INBTSerializable<CompoundNBT> {
  void addPlayerUUID (UUID player);

  void addPlayerUUID (PlayerEntity player);

  void addKnowledge (UUID player, Knowledge<?>... knowledge);

  void addKnowledge (PlayerEntity player, Knowledge<?>... knowledge);

  void removeKnowledge (UUID player, Knowledge<?>... knowledge);

  void removeKnowledge (PlayerEntity player, Knowledge<?>... knowledge);

  void clearKnowledge (UUID player);

  void clearKnowledge (PlayerEntity player);

  List<Knowledge<?>> getKnowledgeAsList (UUID uuid);

  List<Knowledge<?>> getKnowledgeAsList (PlayerEntity player);

  Knowledge<?>[] getKnowledgeAsArray (UUID uuid);

  Knowledge<?>[] getKnowledgeAsArray (PlayerEntity player);
}
