package com.teamacronymcoders.essence.item.tome.knowledge;

import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capability.tank.ModifiableTank;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExperienceTomeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    public static final String MODIFIABLE_TANK = "Modifiable_Tank";

    private ItemStack stack;
    private FluidTank tank;
    private ItemStackModifierHolder modifierHolder;

    private LazyOptional<ItemStackModifierHolder> optional_holder = LazyOptional.of(() -> modifierHolder);
    private LazyOptional<FluidTank> optional_tank = LazyOptional.of(() -> tank);

    public ExperienceTomeProvider(ItemStack stack, Fluid fluid) {
        this.stack = stack;
        tank = new ModifiableTank(10252, stack, fluidStack -> fluidStack.getFluid().isEquivalentTo(fluid));
        modifierHolder = new ItemStackModifierHolder(stack);

        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.put(MODIFIABLE_TANK, tank.writeToNBT(new CompoundNBT()));
        nbt.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierHolder.serializeNBT());
        tank.readFromNBT(nbt.getCompound(MODIFIABLE_TANK));
        modifierHolder.deserializeNBT(nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
        this.stack.setTag(nbt);
    }

    public ExperienceTomeProvider(ItemStack stack, Fluid fluid, CompoundNBT inputNBT) {
        this.stack = stack;
        tank = new ModifiableTank(10252, stack, fluidStack -> fluidStack.getFluid().isEquivalentTo(fluid));
        modifierHolder = new ItemStackModifierHolder(stack);

        CompoundNBT nbt = stack.getOrCreateTag();
        modifierHolder.deserializeNBT(inputNBT.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
        nbt.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierHolder.serializeNBT());
        tank.readFromNBT(inputNBT.getCompound(MODIFIABLE_TANK));
        nbt.put(MODIFIABLE_TANK, tank.writeToNBT(new CompoundNBT()));
        this.stack.setTag(nbt);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) return optional_tank.cast();
        if (cap == EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER) return optional_holder.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.put(MODIFIABLE_TANK, tank.writeToNBT(new CompoundNBT()));
        nbt.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierHolder.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        tank.readFromNBT(nbt.getCompound(MODIFIABLE_TANK));
        modifierHolder.deserializeNBT(nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
    }
}
