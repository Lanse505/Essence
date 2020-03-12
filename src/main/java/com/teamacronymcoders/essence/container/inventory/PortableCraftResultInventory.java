package com.teamacronymcoders.essence.container.inventory;

import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PortableCraftResultInventory extends CraftResultInventory {
    private final InventoryComponent<?> component;
    private final NonNullList<ItemStack> result;
    private IRecipe<?> recipeUsed;

    public PortableCraftResultInventory(InventoryComponent<?> component) {
        this.component = component;
        this.result = NonNullList.withSize(component.getSlots(), ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return component.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.result) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.component.getStackInSlot(index);
    }

    public List<ItemStack> getStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < component.getSlots(); i++) {
            stacks.add(component.getStackInSlot(i));
        }
        return stacks;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndRemove(this.getStacks(), index);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.getStacks(), index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.component.setStackInSlot(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.result.clear();
    }

    @Override
    public void setRecipeUsed(IRecipe<?> recipeUsed) {
        this.recipeUsed = recipeUsed;
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return recipeUsed;
    }
}
