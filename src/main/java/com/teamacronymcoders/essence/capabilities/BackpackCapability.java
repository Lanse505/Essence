package com.teamacronymcoders.essence.capabilities;

import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.items.misc.EssenceBackpackItem;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BackpackCapability implements ICapabilityProvider {
    private final LazyOptional<IItemHandler> optional;

    public BackpackCapability(InventoryComponent<EssenceBackpackItem> inventory) {
        this.optional = LazyOptional.of(() -> inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }
}
