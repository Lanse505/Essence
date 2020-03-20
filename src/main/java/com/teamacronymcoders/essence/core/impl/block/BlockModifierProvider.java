package com.teamacronymcoders.essence.core.impl.block;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapabilities;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.core.impl.itemstack.ItemStackModifierHolder;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockModifierProvider implements ICapabilityProvider, ICapabilitySerializable<ListNBT> {

    private BlockModifierHolder modifierHolder = new BlockModifierHolder();
    private LazyOptional<BlockModifierHolder> optional = LazyOptional.of(() -> modifierHolder);

    public BlockModifierProvider() {}

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCapabilities.BLOCK_MODIFIER_HOLDER) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public ListNBT serializeNBT() {
        return modifierHolder.serializeNBT();
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        modifierHolder.deserializeNBT(nbt);
    }
}
