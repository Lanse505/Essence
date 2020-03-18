package com.teamacronymcoders.essence.api.tool.modifierholder;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapabilities;
import com.teamacronymcoders.essence.utils.tiers.IEssenceBaseTier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModifierProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    private IModifierHolder modifierHolder;
    private final LazyOptional<IModifierHolder> optional = LazyOptional.of(() -> modifierHolder);

    public ModifierProvider(IEssenceBaseTier tier) {
        modifierHolder = new ModifierHolder(tier);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCapabilities.MODIFIER_HOLDER) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return modifierHolder.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        modifierHolder.deserializeNBT(nbt);
    }
}
