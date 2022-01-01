package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.knowledge.event.KnowledgeEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class KnowledgeHolder implements IKnowledgeHolder, INBTSerializable<CompoundTag> {

    private static final String PLAYER_UUID = "Player_UUID";
    private static final String PLAYER_KNOWLEDGE = "Player_Knowledge";
    private static final String PLAYER_INSTANCES = "Player_Knowledge_Instances";

    private final Map<UUID, List<Knowledge>> playerToKnowledgeMap;

    public KnowledgeHolder() {
        this.playerToKnowledgeMap = new HashMap<>();
    }

    @Override
    public void addPlayerUUID(Player player) {
        if (!playerToKnowledgeMap.containsKey(player.getUUID())) {
            playerToKnowledgeMap.put(player.getUUID(), new ArrayList<>());
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
    public void addKnowledge(UUID player, Knowledge... knowledge) {
        List<Knowledge> knowledges = playerToKnowledgeMap.get(player);
        for (Knowledge instance : knowledge) {
            if (!knowledges.contains(instance)) {
                knowledges.add(instance);
            }
        }
    }

    @Override
    public void addKnowledge(Player player, Knowledge... knowledge) {
        ServerPlayer serverPlayer = null;
        List<Knowledge> knowledges = playerToKnowledgeMap.get(player.getUUID());
        if (player instanceof ServerPlayer) {
            serverPlayer = (ServerPlayer) player;
        }
        for (Knowledge instance : knowledge) {
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
    public void removeKnowledge(UUID player, Knowledge... knowledge) {
        List<Knowledge> knowledges = playerToKnowledgeMap.get(player);
        for (Knowledge instance : knowledge) {
            knowledges.remove(instance);
        }
    }

    @Override
    public void removeKnowledge(Player player, Knowledge... knowledge) {
        List<Knowledge> knowledges = playerToKnowledgeMap.get(player.getUUID());
        for (Knowledge instance : knowledge) {
            MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.remove(player, instance));
            knowledges.remove(instance);
        }
    }

    @Override
    public List<Knowledge> getKnowledgeAsList(UUID uuid) {
        return playerToKnowledgeMap.get(uuid);
    }

    public List<Knowledge> getKnowledgeAsList(Player player) {
        return playerToKnowledgeMap.get(player.getUUID());
    }

    @Override
    public Knowledge[] getKnowledgeAsArray(UUID uuid) {
        return (Knowledge[]) playerToKnowledgeMap.get(uuid).toArray();
    }

    @Override
    public Knowledge[] getKnowledgeAsArray(Player player) {
        return (Knowledge[]) playerToKnowledgeMap.get(player.getUUID()).toArray();
    }

    @Override
    public void clearKnowledge(UUID player) {
        playerToKnowledgeMap.get(player).clear();
    }

    @Override
    public void clearKnowledge(Player player) {
        playerToKnowledgeMap.get(player.getUUID()).clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag nbt = new CompoundTag();
        final ListTag listNBT = new ListTag();
        for (Map.Entry<UUID, List<Knowledge>> entry : playerToKnowledgeMap.entrySet()) {
            final CompoundTag entryNBT = new CompoundTag();
            final ListTag knowledge = new ListTag();
            entryNBT.putUUID(PLAYER_UUID, entry.getKey());
            for (Knowledge knowledges : entry.getValue()) {
                listNBT.add(knowledges.serializeNBT());
            }
            entryNBT.put(PLAYER_KNOWLEDGE, knowledge);
            listNBT.add(entryNBT);
        }
        nbt.put(PLAYER_INSTANCES, listNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final ListTag instances = nbt.getList(PLAYER_INSTANCES, Tag.TAG_COMPOUND);
        for (int i = 0; i < instances.size(); i++) {
            final CompoundTag compoundNBT = instances.getCompound(i);
            final UUID uuid = compoundNBT.getUUID(PLAYER_UUID);
            final ListTag knowledges = compoundNBT.getList(PLAYER_KNOWLEDGE, Tag.TAG_COMPOUND);
            for (int j = 0; j < knowledges.size(); j++) {
                final Knowledge knowledge = new Knowledge();
                knowledge.deserializeNBT(knowledges.getCompound(j));
                if (playerToKnowledgeMap.computeIfAbsent(uuid, entry -> new ArrayList<>()).stream().noneMatch(tracked -> tracked == knowledge)) {
                    playerToKnowledgeMap.get(uuid).add(knowledge);
                }
            }
        }
    }

}
