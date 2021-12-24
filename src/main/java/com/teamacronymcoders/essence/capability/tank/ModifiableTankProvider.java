package com.teamacronymcoders.essence.capability.tank;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModifiableTankProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

  public static final String MODIFIABLE_TANK = "Modifiable_Tank";

  private final ItemStack stack;
  private ModifiableTank tank;
  private final LazyOptional<ModifiableTank> optional = LazyOptional.of(() -> tank);

  public ModifiableTankProvider(ItemStack stack, Fluid fluid) {
    this.stack = stack;
    tank = new ModifiableTank(16000, stack, fluidStack -> fluidStack.getFluid().isSame(fluid));
  }

  public ModifiableTankProvider(ItemStack stack, CompoundTag nbt, Fluid fluid) {
    this.stack = stack;
    tank = new ModifiableTank(16000, stack, fluidStack -> fluidStack.getFluid().isSame(fluid));
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
