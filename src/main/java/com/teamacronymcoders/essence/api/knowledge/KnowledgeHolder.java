package com.teamacronymcoders.essence.api.knowledge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class KnowledgeHolder implements IKnowledgeHolder, INBTSerializable<ListNBT> {

    private List<Knowledge> knowledges;

    public KnowledgeHolder() {
        this.knowledges = new ArrayList<>();
    }

    @Override
    public void addKnowledge(Knowledge... knowledge) {
        for (Knowledge instance : knowledge) {
            if (!this.knowledges.contains(instance)) {
                this.knowledges.add(instance);
            }
        }
    }

    @Override
    public void removeKnowledge(Knowledge... knowledge) {
        for (Knowledge instance : knowledge) {
            this.knowledges.remove(instance);
        }
    }

    @Override
    public void clearKnowledges() {
        this.knowledges.clear();
    }


    @Override
    public ListNBT serializeNBT() {
        final ListNBT listNBT = new ListNBT();
        for (Knowledge knowledge : knowledges) {
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
            if (knowledges.stream().noneMatch(tracked -> tracked == knowledge)) {
                knowledges.add(knowledge);
            }
        }
    }
}
