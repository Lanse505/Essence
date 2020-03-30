package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KnowledgeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    private IKnowledgeHolder knowledgeHolder = EssenceCapabilities.KNOWLEDGE.getDefaultInstance();
    private final LazyOptional<IKnowledgeHolder> optional = LazyOptional.of(() -> knowledgeHolder);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCapabilities.KNOWLEDGE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return knowledgeHolder.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        knowledgeHolder.deserializeNBT(nbt);
    }
}
