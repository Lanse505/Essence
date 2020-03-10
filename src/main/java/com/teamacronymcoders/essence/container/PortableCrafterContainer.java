package com.teamacronymcoders.essence.container;

import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.container.impl.BasicInventoryContainer;
import com.teamacronymcoders.essence.container.inventory.PortableCraftResultInventory;
import com.teamacronymcoders.essence.container.inventory.PortableCraftingInventory;
import com.teamacronymcoders.essence.items.misc.PortableCrafter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Optional;

public class PortableCrafterContainer extends BasicInventoryContainer {
    @ObjectHolder("essence:portable_crafter")
    public static ContainerType<PortableCrafterContainer> type;

    private PlayerInventory inventory;
    private PortableCrafter portableCrafter;
    private IWorldPosCallable callable;
    private PortableCraftingInventory craftingInventory;
    private PortableCraftResultInventory resultInventory;
    private Optional<ICraftingRecipe> optional;

    public PortableCrafterContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        this((PortableCrafter) inventory.player.getHeldItem(Hand.valueOf(buffer.readString())).getItem(), inventory, id);
    }

    public PortableCrafterContainer(PortableCrafter portableCrafter, PlayerInventory inventory, int id) {
        super(type, inventory, id);
        this.inventory = inventory;
        this.portableCrafter = portableCrafter;
        this.callable = IWorldPosCallable.of(inventory.player.world, inventory.player.getPosition());
        this.craftingInventory = new PortableCraftingInventory(this, portableCrafter.getGrid());
        this.resultInventory = new PortableCraftResultInventory(portableCrafter.getOutput());
        initInventory();
    }

    public void onCraft(int windowId, World world, PlayerEntity crafter, CraftingInventory inventory, CraftResultInventory result) {
        if (!world.isRemote) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) crafter;
            ItemStack stack = ItemStack.EMPTY;
            if (optional != null && optional.isPresent()) {
                ICraftingRecipe recipe = optional.get();
                if (result.canUseRecipe(world, serverPlayer, recipe)) {
                    stack = recipe.getCraftingResult(inventory);
                }
            } else {
                optional = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, inventory, world);
                if (optional.isPresent()) {
                    ICraftingRecipe recipe = optional.get();
                    if (result.canUseRecipe(world, serverPlayer, recipe)) {
                        stack = recipe.getCraftingResult(inventory);
                    }
                }
            }
            result.setInventorySlotContents(0, stack);
            serverPlayer.connection.sendPacket(new SSetSlotPacket(windowId, 0, stack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.callable.consume((world, pos) -> onCraft(this.windowId, world, this.inventory.player, this.craftingInventory, this.resultInventory));
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.callable.consume((world, pos) -> this.clearContainer(playerIn, world, this.craftingInventory));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.resultInventory && super.canMergeSlot(stack, slotIn);
    }

    public void addGridSlots() {
        InventoryComponent<?> grid = this.portableCrafter.getGrid();
        for (int i = 0; i < grid.getSlots(); i++)
            addSlot(new Slot(
                this.craftingInventory, i,
                grid.getXPos() + grid.getSlotPosition().apply(i).getLeft(),
                grid.getYPos() + grid.getSlotPosition().apply(i).getRight())
            );
    }

    public void addOutputSlot() {
        InventoryComponent<?> output = this.portableCrafter.getOutput();
        addSlot(new CraftingResultSlot(inventory.player, this.craftingInventory, this.resultInventory, 0,
            output.getXPos() + output.getSlotPosition().apply(0).getLeft(),
            output.getYPos() + output.getSlotPosition().apply(0).getRight()));
    }

    @Override
    public void addExtraSlots() {
        super.addExtraSlots();
        addGridSlots();
        addOutputSlot();
    }

    public PortableCrafter getPortableCrafter() {
        return portableCrafter;
    }


}
