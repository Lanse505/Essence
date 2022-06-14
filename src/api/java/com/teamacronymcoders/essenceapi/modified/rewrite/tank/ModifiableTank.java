package com.teamacronymcoders.essenceapi.modified.rewrite.tank;

import com.teamacronymcoders.essenceapi.modified.rewrite.IModifiedItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ModifiableTank extends FluidTank implements IFluidHandlerItem {

    private final ItemStack stack;
    private final int originalCapacity;

    public ModifiableTank(int capacity, ItemStack stack) {
        super(capacity);
        this.originalCapacity = capacity;
        this.stack = stack;
    }

    public ModifiableTank(int capacity, ItemStack stack, Predicate<FluidStack> validator) {
        super(capacity, validator);
        this.originalCapacity = capacity;
        this.stack = stack;
    }

    @Override
    public int getCapacity() {
        return stack.getItem() instanceof IModifiedItem ? Math.min(originalCapacity + ((IModifiedItem) stack.getItem()).getMaxCapacityFromModifiers(originalCapacity, stack), Integer.MAX_VALUE) : capacity;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) {
            return 0;
        }
        if (action.simulate()) {
            if (fluid.isEmpty()) {
                return Math.min(getCapacity(), resource.getAmount());
            }
            if (!fluid.isFluidEqual(resource)) {
                return 0;
            }
            return Math.min(getCapacity() - fluid.getAmount(), resource.getAmount());
        }
        if (fluid.isEmpty()) {
            fluid = new FluidStack(resource, Math.min(getCapacity(), resource.getAmount()));
            onContentsChanged();
            return fluid.getAmount();
        }
        if (!fluid.isFluidEqual(resource)) {
            return 0;
        }
        int filled = getCapacity() - fluid.getAmount();

        if (resource.getAmount() < filled) {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        } else {
            fluid.setAmount(getFluidAmount());
        }
        if (filled > 0) {
            onContentsChanged();
        }
        return filled;
    }

    @NotNull
    @Override
    public ItemStack getContainer() {
        return this.stack;
    }

    @Override
    public int getSpace() {
        return Math.max(0, getCapacity() - fluid.getAmount());
    }

    public static class ModifiableTankProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

        public static final String MODIFIABLE_TANK = "Modifiable_Tank";

        private final ItemStack stack;
        private ModifiableTank tank;
        private final LazyOptional<ModifiableTank> optional = LazyOptional.of(() -> tank);

        public ModifiableTankProvider(int capacity, ItemStack stack, Fluid fluid) {
            this.stack = stack;
            tank = new ModifiableTank(capacity, stack, fluidStack -> fluidStack.getFluid().isSame(fluid));
        }

        public ModifiableTankProvider(int capacity, ItemStack stack, CompoundTag nbt, Fluid fluid) {
            this.stack = stack;
            tank = new ModifiableTank(capacity, stack, fluidStack -> fluidStack.getFluid().isSame(fluid));
            CompoundTag stackNBT = stack.getOrCreateTag();
            stackNBT.put(MODIFIABLE_TANK, nbt);
            tank.readFromNBT(stackNBT.getCompound(MODIFIABLE_TANK));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? optional.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            if (stack.getTag() != null && stack.getTag().contains(MODIFIABLE_TANK)) {
                return tank.writeToNBT(stack.getTag().getCompound(MODIFIABLE_TANK));
            }
            return tank.writeToNBT(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            tank.readFromNBT(nbt);
        }
    }
}
