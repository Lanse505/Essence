package com.teamacronymcoders.essence.capability.itemstack.wrench;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityStorageProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    private EntityStorageCapability cap;
    private LazyOptional<EntityStorageCapability> optional = LazyOptional.of(() -> cap);

    public EntityStorageProvider(ItemStack stack) {
        cap = new EntityStorageCapability(stack);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCoreCapability.ENTITY_STORAGE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return cap.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt, World world) {
        cap.deserializeNBT(nbt, world);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {}
}
