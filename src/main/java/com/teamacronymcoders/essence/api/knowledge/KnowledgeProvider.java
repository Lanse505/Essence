package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KnowledgeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    private final IKnowledgeHolder knowledgeHolder = new KnowledgeHolder();
    private final LazyOptional<IKnowledgeHolder> optional = LazyOptional.of(() -> knowledgeHolder);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCapability.KNOWLEDGE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return knowledgeHolder.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        knowledgeHolder.deserializeNBT(nbt);
    }
}
