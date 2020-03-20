package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.knowledge.event.KnowledgeEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeHolder implements IKnowledgeHolder, INBTSerializable<ListNBT> {

    private List<Knowledge<?>> knowledge;

    public KnowledgeHolder() {
        this.knowledge = new ArrayList<>();
    }

    @Override
    public void addKnowledge(PlayerEntity player, Knowledge<?>... knowledge) {
        ServerPlayerEntity serverPlayer = null;
        if (player instanceof ServerPlayerEntity) {
            serverPlayer = (ServerPlayerEntity) player;
        }
        for (Knowledge instance : knowledge) {
            boolean notCancelled = false;
            if (!MinecraftForge.EVENT_BUS.post(new KnowledgeEvent.addPre(player, instance)) && !this.knowledge.contains(instance)) {
                this.knowledge.add(instance);
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
    public ListNBT serializeNBT() {
        final ListNBT listNBT = new ListNBT();
        for (Knowledge<?> knowledge : knowledge) {
            listNBT.add(knowledge.serializeNBT());
        }
        return listNBT;
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        for (int i = 0; i < nbt.size(); i++) {
            final CompoundNBT compoundNBT = nbt.getCompound(i);
            final Knowledge<?> knowledge = new Knowledge();
            knowledge.deserializeNBT(compoundNBT);
            if (this.knowledge.stream().noneMatch(tracked -> tracked == knowledge)) {
                this.knowledge.add(knowledge);
            }
        }
    }
}
