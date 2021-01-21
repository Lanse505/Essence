package com.teamacronymcoders.essence.block.infusion.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;

public class InfusionPedestalTile extends ActiveTile<InfusionPedestalTile> {

  @Save
  private Integer ticksExisted = 0;

  @Save
  private final InventoryComponent<InfusionPedestalTile> inventory;

  public InfusionPedestalTile () {
    super(EssenceBlockRegistrate.INFUSION_PEDESTAL.get());
    addInventory(inventory = new InventoryComponent<InfusionPedestalTile>("inventory", 0, 0, 1)
            .setComponentHarness(this)
            .setOnSlotChanged((stack, integer) -> markComponentForUpdate(false))
            .setSlotLimit(1)
    );
  }

  @Override
  public void tick () {
    super.tick();
    ticksExisted++;
  }

  @Nonnull
  @Override
  public InfusionPedestalTile getSelf () {
    return this;
  }

  public void addItem (ItemStack stack) {
    this.inventory.insertItem(0, stack, false);
  }

  public ItemStack getStack () {
    return this.inventory.getStackInSlot(0);
  }

  public Integer getTicksExisted () {
    return ticksExisted;
  }
}
