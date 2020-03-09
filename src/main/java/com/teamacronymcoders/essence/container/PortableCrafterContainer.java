package com.teamacronymcoders.essence.container;

import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.container.impl.BasicInventoryContainer;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.items.misc.EssencePortableCrafter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class PortableCrafterContainer extends BasicInventoryContainer {
    @ObjectHolder("essence:portable_crafter")
    public static ContainerType<PortableCrafterContainer> type;

    private EssencePortableCrafter portableCrafter;

    public PortableCrafterContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        super(id, inventory, buffer);
    }

    public PortableCrafterContainer(EssencePortableCrafter portableCrafter, PlayerInventory inventory, int id) {
        super(type, inventory, id);
        Essence.LOGGER.info(type.getRegistryName());
        this.portableCrafter = portableCrafter;
        initInventory();
    }

    public void addGridSlots() {
        InventoryComponent<?> grid = this.portableCrafter.getGrid();
        for (int i = 0; i < grid.getSlots(); i++)
            addSlot(new SlotItemHandler(
                grid, i,
                grid.getXPos() + grid.getSlotPosition().apply(i).getLeft(),
                grid.getYPos() + grid.getSlotPosition().apply(i).getRight())
            );
    }

    public void addOutputSlot() {
        InventoryComponent<?> output = this.portableCrafter.getOutput();
        addSlot(new SlotItemHandler(output, 0, output.getXPos() + output.getSlotPosition().apply(0).getLeft(), output.getYPos() + output.getSlotPosition().apply(0).getRight()));
    }

    public void updateSlotPosition() {}

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void addExtraSlots() {
        super.addExtraSlots();
        addGridSlots();
        addOutputSlot();
    }

    public EssencePortableCrafter getPortableCrafter() {
        return portableCrafter;
    }
}
