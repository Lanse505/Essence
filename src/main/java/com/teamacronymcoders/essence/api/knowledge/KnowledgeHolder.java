package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.knowledge.event.KnowledgeEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class KnowledgeHolder implements IKnowledgeHolder, INBTSerializable<CompoundNBT> {

    private static final String PLAYER_UUID = "Player_UUID";
    private static final String PLAYER_KNOWLEDGE = "Player_Knowledge";
    private static final String PLAYER_INSTANCES = "Player_Knowledge_Instances";

    private Map<UUID, List<Knowledge<?>>> playerToKnowledgeMap;
    private List<Knowledge<?>> knowledge;

    public KnowledgeHolder() {
        this.playerToKnowledgeMap = new HashMap<>();
    }

    @Override
    public void addKnowledge(PlayerEntity player, Knowledge<?>... knowledge) {
        ServerPlayerEntity serverPlayer = null;
        if (player instanceof ServerPlayerEntity) {
            serverPlayer = (ServerPlayerEntity) player;
        }
        for (Knowledge<?> instance : knowledge) {
            boolean notCancelled = false;
            if (!MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.addPre(player, instance)) && !this.knowledge.contains(instance)) {
                List<Knowledge<?>> knowledges = playerToKnowledgeMap.get(player.getUniqueID());
                knowledges.add(instance);
                notCancelled = true;
            }
            if (notCancelled) {
                if (serverPlayer != null) {
                    MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.addPost(serverPlayer, instance));
                }
            }
        }
    }

    @Override
    public void removeKnowledge(PlayerEntity player, Knowledge<?>... knowledge) {
        for (Knowledge<?> instance : knowledge) {
            MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.remove(player, instance));
            this.knowledge.remove(instance);
        }
    }

    @Override
    public Knowledge<?>[] getKnowledge() {
        return (Knowledge<?>[]) this.knowledge.toArray();
    }

    public List<Knowledge<?>> getKnowledgeAsList() {
        return this.knowledge;
    }

    @Override
    public void clearKnowledge() {
        this.knowledge.clear();
    }

    @Override
    public CompoundNBT serializeNBT() {
        final CompoundNBT nbt = new CompoundNBT();
        final ListNBT listNBT = new ListNBT();
        for (Map.Entry<UUID, List<Knowledge<?>>> entry : playerToKnowledgeMap.entrySet()) {
            final CompoundNBT entryNBT = new CompoundNBT();
            final ListNBT knowledge = new ListNBT();
            entryNBT.putUniqueId(PLAYER_UUID, entry.getKey());
            for (Knowledge<?> knowledges : entry.getValue()) {
                listNBT.add(knowledges.serializeNBT());
            }
            entryNBT.put(PLAYER_KNOWLEDGE, knowledge);
            listNBT.add(entryNBT);
        }
        nbt.put(PLAYER_INSTANCES, listNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        final ListNBT instances = nbt.getList(PLAYER_INSTANCES, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < instances.size(); i++) {
            final CompoundNBT compoundNBT = instances.getCompound(i);
            final UUID uuid = compoundNBT.getUniqueId(PLAYER_UUID);
            final ListNBT knowledges = compoundNBT.getList(PLAYER_KNOWLEDGE, Constants.NBT.TAG_COMPOUND);
            for (int j = 0; j < knowledges.size(); j++) {
                final Knowledge<?> knowledge = new Knowledge<>();
                knowledge.deserializeNBT(knowledges.getCompound(j));
                if (playerToKnowledgeMap.computeIfAbsent(uuid, entry -> new ArrayList<>()).stream().noneMatch(tracked -> tracked == knowledge)) {
                    playerToKnowledgeMap.get(uuid).add(knowledge);
                }
            }
        }
    }
}
