package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.knowledge.event.KnowledgeEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KnowledgeHolder implements IKnowledgeHolder, INBTSerializable<CompoundNBT> {

    private static final String PLAYER_UUID = "Player_UUID";
    private static final String PLAYER_KNOWLEDGE = "Player_Knowledge";
    private static final String PLAYER_INSTANCES = "Player_Knowledge_Instances";

    private Map<UUID, List<Knowledge<?>>> playerToKnowledgeMap;

    public KnowledgeHolder() {
        this.playerToKnowledgeMap = new HashMap<>();
    }

    @Override
    public void addPlayerUUID(PlayerEntity player) {
        if (!playerToKnowledgeMap.containsKey(player.getUniqueID())) {
            playerToKnowledgeMap.put(player.getUniqueID(), new ArrayList<>());
        }
    }

    @Override
    public void addPlayerUUID(UUID player) {
        if (!playerToKnowledgeMap.containsKey(player)) {
            playerToKnowledgeMap.put(player, new ArrayList<>());
        }
    }

    /**
     * This should only be called from code where you don't have a player input context for the input!
     * THIS VERSION DOES NOT FIRE EVENTS!
     *
     * @param player    UUID to remove Knowledge from
     * @param knowledge Knowledge VarArg to remove.
     */
    @Override
    public void addKnowledge(UUID player, Knowledge<?>... knowledge) {
        List<Knowledge<?>> knowledges = playerToKnowledgeMap.get(player);
        for (Knowledge<?> instance : knowledge) {
            if (!knowledges.contains(instance)) {
                knowledges.add(instance);
            }
        }
    }

    @Override
    public void addKnowledge(PlayerEntity player, Knowledge<?>... knowledge) {
        ServerPlayerEntity serverPlayer = null;
        List<Knowledge<?>> knowledges = playerToKnowledgeMap.get(player.getUniqueID());
        if (player instanceof ServerPlayerEntity) {
            serverPlayer = (ServerPlayerEntity) player;
        }
        for (Knowledge<?> instance : knowledge) {
            boolean notCancelled = false;
            if (!MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.addPre(player, instance)) && !knowledges.contains(instance)) {
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

    /**
     * This should only be called from code where you don't have a player input context for the input!
     *
     * @param player    UUID to remove Knowledge from
     * @param knowledge Knowledge VarArg to remove.
     *                  THIS VERSION DOES NOT FIRE EVENTS!
     */
    @Override
    public void removeKnowledge(UUID player, Knowledge<?>... knowledge) {
        List<Knowledge<?>> knowledges = playerToKnowledgeMap.get(player);
        for (Knowledge<?> instance : knowledge) {
            knowledges.remove(instance);
        }
    }

    @Override
    public void removeKnowledge(PlayerEntity player, Knowledge<?>... knowledge) {
        List<Knowledge<?>> knowledges = playerToKnowledgeMap.get(player.getUniqueID());
        for (Knowledge<?> instance : knowledge) {
            MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.remove(player, instance));
            knowledges.remove(instance);
        }
    }

    @Override
    public List<Knowledge<?>> getKnowledgeAsList(UUID uuid) {
        return playerToKnowledgeMap.get(uuid);
    }

    public List<Knowledge<?>> getKnowledgeAsList(PlayerEntity player) {
        return playerToKnowledgeMap.get(player.getUniqueID());
    }

    @Override
    public Knowledge<?>[] getKnowledgeAsArray(UUID uuid) {
        return (Knowledge<?>[]) playerToKnowledgeMap.get(uuid).toArray();
    }

    @Override
    public Knowledge<?>[] getKnowledgeAsArray(PlayerEntity player) {
        return (Knowledge<?>[]) playerToKnowledgeMap.get(player.getUniqueID()).toArray();
    }

    @Override
    public void clearKnowledge(UUID player) {
        playerToKnowledgeMap.get(player).clear();
    }

    @Override
    public void clearKnowledge(PlayerEntity player) {
        playerToKnowledgeMap.get(player.getUniqueID()).clear();
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
