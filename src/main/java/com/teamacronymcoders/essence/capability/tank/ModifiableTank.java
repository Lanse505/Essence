package com.teamacronymcoders.essence.capability.tank;

import com.teamacronymcoders.essence.api.modified.IModifiedTank;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ModifiableTank extends FluidTank implements IFluidHandlerItem {

  private final ItemStack stack;
  private final int originalCapacity;

  public ModifiableTank (int capacity, ItemStack stack) {
    super(capacity);
    this.originalCapacity = capacity;
    this.stack = stack;
  }

  public ModifiableTank (int capacity, ItemStack stack, Predicate<FluidStack> validator) {
    super(capacity, validator);
    this.originalCapacity = capacity;
    this.stack = stack;
  }

  @Override
  public int getCapacity () {
    return stack.getItem() instanceof IModifiedTank ? Math.min(originalCapacity + ((IModifiedTank) stack.getItem()).getMaxCapacityFromModifiers(originalCapacity, stack), Integer.MAX_VALUE) : capacity;
  }

  @Override
  public int fill (FluidStack resource, FluidAction action) {
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

  @Override
  public int getSpace () {
    return Math.max(0, getCapacity() - fluid.getAmount());
  }

  @Nonnull
  @Override
  public ItemStack getContainer () {
    return stack;
  }
}
