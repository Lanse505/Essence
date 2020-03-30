package com.teamacronymcoders.essence.container.portablecrafter;

import com.teamacronymcoders.essence.items.misc.PortableCrafterItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;

public class PortableCrafterContainer extends WorkbenchContainer {

    private final ItemStack stack;

    public PortableCrafterContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        this(inventory.player.getHeldItem(Hand.valueOf(buffer.readString())).getItem() instanceof PortableCrafterItem ?
                inventory.player.getHeldItem(Hand.valueOf(buffer.readString())) : ItemStack.EMPTY,
            id, inventory, IWorldPosCallable.of(inventory.player.world, inventory.player.getPosition()));
    }

    public PortableCrafterContainer(ItemStack stack, int id, PlayerInventory inventory, IWorldPosCallable callable) {
        super(id, inventory, callable);
        this.stack = stack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
    }

    @Override
    public boolean canInteractWith(PlayerEntity p_75145_1_) {
        return true;
    }
}
