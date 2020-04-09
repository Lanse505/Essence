package com.teamacronymcoders.essence.capability.itemstack;

import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
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
    }

    public ItemStackModifierProvider(ItemStack stack, CompoundNBT nbt) {
        modifierHolder = new ItemStackModifierHolder(stack);
        modifierHolder.deserializeNBT(nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER) {
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
