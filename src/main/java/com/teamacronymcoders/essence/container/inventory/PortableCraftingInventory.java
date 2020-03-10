package com.teamacronymcoders.essence.container.inventory;

import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class PortableCraftingInventory extends CraftingInventory {

    private final InventoryComponent<?> component;
    private final IInventory inventory;
    private final NonNullList<ItemStack> stacks;
    private final Container eventHandler;

    public PortableCraftingInventory(Container eventHandlerIn, InventoryComponent<?> component) {
        super(eventHandlerIn, component.getXSize(), component.getYSize());
        this.eventHandler = eventHandlerIn;
        this.component = component;
        this.inventory = new RecipeWrapper(component);
        this.stacks = NonNullList.create();
        for (int i = 0; i < component.getSlots(); i++) {
            stacks.add(component.getStackInSlot(i));
        }
    }

    @Override
    public int getSizeInventory() {
        return this.component.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.component.getSlots() ; i++) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= this.inventory.getSizeInventory() ? ItemStack.EMPTY : this.stacks.get(index);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(this.stacks, index, count);
        if (!stack.isEmpty()) {
            this.eventHandler.onCraftMatrixChanged(this);
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);
        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    @Override
    public int getHeight() {
        return this.component.getYSize();
    }

    @Override
    public int getWidth() {
        return this.component.getXSize();
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack stack : this.stacks) {
            helper.accountPlainStack(stack);
        }
    }
}
