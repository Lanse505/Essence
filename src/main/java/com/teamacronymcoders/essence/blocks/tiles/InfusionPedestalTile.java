package com.teamacronymcoders.essence.blocks.tiles;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class InfusionPedestalTile extends ActiveTile<InfusionPedestalTile> {

    @Save
    private InventoryComponent<InfusionPedestalTile> inventory;

    public InfusionPedestalTile() {
        super(EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL);
        addInventory(inventory = new InventoryComponent<InfusionPedestalTile>("inventory", 0, 0, 1).setComponentHarness(this));
    }

    @Override
    public void tick() {
        //Essence.LOGGER.info("Has " + this.inventory.getStackInSlot(0).getDisplayName().getString() + " in Slot 0");
    }

    @Nonnull
    @Override
    public InfusionPedestalTile getSelf() {
        return this;
    }

    public void addItem(ItemStack stack) {
        this.inventory.insertItem(0, stack, false);
    }

    public ItemStack getStack() {
        return this.inventory.getStackInSlot(0);
    }
}
