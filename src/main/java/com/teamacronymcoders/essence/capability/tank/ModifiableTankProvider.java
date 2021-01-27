package com.teamacronymcoders.essence.capability.tank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ModifiableTankProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

  public static final String MODIFIABLE_TANK = "Modifiable_Tank";

  private final ItemStack stack;
  private ModifiableTank tank;
  private final LazyOptional<ModifiableTank> optional = LazyOptional.of(() -> tank);

  public ModifiableTankProvider(ItemStack stack, Fluid fluid) {
    this.stack = stack;
    tank = new ModifiableTank(16000, stack, fluidStack -> fluidStack.getFluid().isEquivalentTo(fluid));
  }

  public ModifiableTankProvider(ItemStack stack, CompoundNBT nbt, Fluid fluid) {
    this.stack = stack;
    tank = new ModifiableTank(16000, stack, fluidStack -> fluidStack.getFluid().isEquivalentTo(fluid));
    CompoundNBT stackNBT = stack.getOrCreateTag();
    stackNBT.put(MODIFIABLE_TANK, nbt);
    tank.readFromNBT(stackNBT.getCompound(MODIFIABLE_TANK));
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? optional.cast() : LazyOptional.empty();
  }

  @Override
  public CompoundNBT serializeNBT() {
    if (stack.getTag() != null && stack.getTag().contains(MODIFIABLE_TANK)) {
      return tank.writeToNBT(stack.getTag().getCompound(MODIFIABLE_TANK));
    }
    return tank.writeToNBT(new CompoundNBT());
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    tank.readFromNBT(nbt);
  }
}
