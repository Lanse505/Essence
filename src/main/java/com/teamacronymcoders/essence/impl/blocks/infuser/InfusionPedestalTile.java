package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public class InfusionPedestalTile extends ActiveTile<InfusionPedestalTile> {

    @Save
    private InventoryComponent<InfusionPedestalTile> inventory;

    public InfusionPedestalTile() {
        super(EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL);
        addInventory(inventory = new InventoryComponent<InfusionPedestalTile>("inventory", 0, 0, 1).setComponentHarness(this));
    }

    @Override
    public void openGui(PlayerEntity player) {}


    @Nonnull
    @Override
    public InfusionPedestalTile getSelf() {
        return this;
    }

    public void addItem(ItemStack stack) {
        this.inventory.setStackInSlot(0, stack);
    }

    public ItemStack getStack() {
        return this.inventory.getStackInSlot(0);
    }
}
