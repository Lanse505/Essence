package com.teamacronymcoders.essence.common.item.tome.experience;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.api.modified.rewrite.tank.ModifiableTank;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExperienceTomeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static final String MODIFIABLE_TANK = "Modifiable_Tank";

    private final ItemStack stack;
    private FluidTank tank;
    private ItemStackModifierHolder modifierHolder;

    private final LazyOptional<ItemStackModifierHolder> optional_holder = LazyOptional.of(() -> modifierHolder);
    private final LazyOptional<FluidTank> optional_tank = LazyOptional.of(() -> tank);

    public ExperienceTomeProvider(ItemStack stack, Fluid fluid) {
        this.stack = stack;
        tank = new ModifiableTank(10252, stack, fluidStack -> fluidStack.getFluid().isSame(fluid));
        modifierHolder = new ItemStackModifierHolder(stack);

        CompoundTag nbt = stack.getOrCreateTag();
        nbt.put(MODIFIABLE_TANK, tank.writeToNBT(new CompoundTag()));
        nbt.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierHolder.serializeNBT());
        tank.readFromNBT(nbt.getCompound(MODIFIABLE_TANK));
        modifierHolder.deserializeNBT(nbt.getCompound(EssenceItemstackModifierHelpers.HOLDER));
        this.stack.setTag(nbt);
    }

    public ExperienceTomeProvider(ItemStack stack, Fluid fluid, CompoundTag inputNBT) {
        this.stack = stack;
        tank = new ModifiableTank(10252, stack, fluidStack -> fluidStack.getFluid().isSame(fluid));
        modifierHolder = new ItemStackModifierHolder(stack);

        CompoundTag nbt = stack.getOrCreateTag();
        modifierHolder.deserializeNBT(inputNBT.getCompound(EssenceItemstackModifierHelpers.HOLDER));
        nbt.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierHolder.serializeNBT());
        tank.readFromNBT(inputNBT.getCompound(MODIFIABLE_TANK));
        nbt.put(MODIFIABLE_TANK, tank.writeToNBT(new CompoundTag()));
        this.stack.setTag(nbt);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
            return optional_tank.cast();
        }
        if (cap == EssenceCapability.ITEMSTACK_MODIFIER_HOLDER) {
            return optional_holder.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.put(MODIFIABLE_TANK, tank.writeToNBT(new CompoundTag()));
        nbt.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierHolder.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        tank.readFromNBT(nbt.getCompound(MODIFIABLE_TANK));
        modifierHolder.deserializeNBT(nbt.getCompound(EssenceItemstackModifierHelpers.HOLDER));
    }
}
