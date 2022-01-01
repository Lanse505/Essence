package com.teamacronymcoders.essence.common.block.infusion.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class InfusionPedestalBlockEntity extends ActiveTile<InfusionPedestalBlockEntity> {

    @Save
    private Integer ticksExisted = 0;

    @Save
    private final InventoryComponent<InfusionPedestalBlockEntity> inventory;

    public InfusionPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(EssenceBlockRegistrate.INFUSION_PEDESTAL.get(), pos, state);
        addInventory(inventory = new InventoryComponent<InfusionPedestalBlockEntity>("inventory", 0, 0, 1)
                .setComponentHarness(this)
                .setOnSlotChanged((stack, integer) -> markComponentForUpdate(false))
                .setSlotLimit(1)
        );
    }

    @Override
    public void clientTick(Level level, BlockPos pos, BlockState state, InfusionPedestalBlockEntity blockEntity) {
        super.clientTick(level, pos, state, blockEntity);
        ticksExisted++;
    }

    @Nonnull
    @Override
    public InfusionPedestalBlockEntity getSelf() {
        return this;
    }

    public void addItem(ItemStack stack) {
        this.inventory.insertItem(0, stack, false);
    }

    public ItemStack getStack() {
        return this.inventory.getStackInSlot(0);
    }

    public Integer getTicksExisted() {
        return ticksExisted;
    }

}
