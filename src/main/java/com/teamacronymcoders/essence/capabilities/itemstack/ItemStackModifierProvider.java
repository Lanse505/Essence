package com.teamacronymcoders.essence.capabilities.itemstack;

import com.teamacronymcoders.essence.capabilities.EssenceCoreCapabilities;
import com.teamacronymcoders.essence.utils.helpers.EssenceItemstackModifierHelpers;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemStackModifierProvider implements ICapabilityProvider, ICapabilitySerializable<ListNBT> {

    private ItemStackModifierHolder modifierHolder;
    private LazyOptional<ItemStackModifierHolder> optional = LazyOptional.of(() -> modifierHolder);

    public ItemStackModifierProvider(ItemStack stack) {
        modifierHolder = new ItemStackModifierHolder(stack);
        modifierHolder.serializeNBT();
    }

    public ItemStackModifierProvider(ItemStack stack, CompoundNBT nbt) {
        modifierHolder = new ItemStackModifierHolder(stack);
        modifierHolder.deserializeNBT(nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
        modifierHolder.serializeNBT();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCoreCapabilities.ITEMSTACK_MODIFIER_HOLDER) {
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
