package com.teamacronymcoders.essence.api.knowledge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeHolder implements IKnowledgeHolder, INBTSerializable<ListNBT> {

    private List<Knowledge> knowledge;

    public KnowledgeHolder() {
        this.knowledge = new ArrayList<>();
    }

    @Override
    public void addKnowledge(Knowledge... knowledge) {
        for (Knowledge instance : knowledge) {
            if (!this.knowledge.contains(instance)) {
                this.knowledge.add(instance);
            }
        }
    }

    @Override
    public void removeKnowledge(Knowledge... knowledge) {
        for (Knowledge instance : knowledge) {
            this.knowledge.remove(instance);
        }
    }

    @Override
    public void clearKnowledge() {
        this.knowledge.clear();
    }


    @Override
    public ListNBT serializeNBT() {
        final ListNBT listNBT = new ListNBT();
        for (Knowledge knowledge : knowledge) {
            listNBT.add(knowledge.serializeNBT());
        }
        return listNBT;
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        for (int i = 0; i < nbt.size(); i++) {
            final CompoundNBT compoundNBT = nbt.getCompound(i);
            final Knowledge knowledge = new Knowledge();
            knowledge.deserializeNBT(compoundNBT);
            if (this.knowledge.stream().noneMatch(tracked -> tracked == knowledge)) {
                this.knowledge.add(knowledge);
            }
        }
    }
}
