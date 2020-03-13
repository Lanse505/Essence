package com.teamacronymcoders.essence.container.slots;

import com.hrznstudio.titanium.container.impl.DisableableSlot;
import com.teamacronymcoders.essence.container.PortableCrafterContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.function.BooleanSupplier;

public class PortableCrafterDisableableSlot extends DisableableSlot {
    private final BooleanSupplier isDisabled;

    public PortableCrafterDisableableSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, PortableCrafterContainer portableCrafterContainer) {
        this(inventoryIn, index, xPosition, yPosition, portableCrafterContainer::isDisabled);
    }

    public PortableCrafterDisableableSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, BooleanSupplier isDisabled) {
        super(inventoryIn, index, xPosition, yPosition, isDisabled);
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDisabled.getAsBoolean();
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return !this.isDisabled.getAsBoolean();
    }
}
