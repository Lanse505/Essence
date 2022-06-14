package com.teamacronymcoders.essenceapi.modified.rewrite.itemstack;

import com.teamacronymcoders.essenceapi.EssenceCapabilities;
import com.teamacronymcoders.essenceapi.helper.EssenceAPIItemstackModifierHelpers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemStackModifierProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    private ItemStackModifierHolder modifierHolder;
    private final LazyOptional<ItemStackModifierHolder> optional = LazyOptional.of(() -> modifierHolder);

    public ItemStackModifierProvider(ItemStack stack) {
        modifierHolder = new ItemStackModifierHolder(stack);
    }

    public ItemStackModifierProvider(ItemStack stack, CompoundTag nbt) {
        modifierHolder = new ItemStackModifierHolder(stack);
        modifierHolder.deserializeNBT(nbt.getCompound(EssenceAPIItemstackModifierHelpers.HOLDER));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCapabilities.ITEMSTACK_MODIFIER_HOLDER) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return modifierHolder.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        modifierHolder.deserializeNBT(nbt);
    }

}
