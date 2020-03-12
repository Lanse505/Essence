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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ChunkHolder;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Optional;

public class PortableCrafterContainer extends BasicInventoryContainer {
    @ObjectHolder("essence:portable_crafter")
    public static ContainerType<PortableCrafterContainer> type;

    private PlayerInventory inventory;
    private PortableCrafter portableCrafter;
    private IWorldPosCallable callable;
    private RecipeWrapper grid;
    private RecipeWrapper result;
    private Optional<ICraftingRecipe> optional;
    private IRecipe<?> recipeUsed;

    public PortableCrafterContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        this((PortableCrafter) inventory.player.getHeldItem(Hand.valueOf(buffer.readString())).getItem(), inventory, id);
    }

    public PortableCrafterContainer(PortableCrafter portableCrafter, PlayerInventory inventory, int id) {
        super(type, inventory, id);
        this.inventory = inventory;
        this.portableCrafter = portableCrafter;
        this.callable = IWorldPosCallable.of(inventory.player.world, inventory.player.getPosition());
        this.grid = new RecipeWrapper(portableCrafter.getGrid());
        this.result = new RecipeWrapper(portableCrafter.getOutput());
        initInventory();
    }

    public void onCraft(int windowId, World world, PlayerEntity crafter) {
        if (!world.isRemote) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) crafter;
            ItemStack stack = ItemStack.EMPTY;
            PortableCraftingInventory grid = new PortableCraftingInventory(this, portableCrafter.getGrid());
            if (optional != null && optional.isPresent()) {
                ICraftingRecipe recipe = optional.get();
                if (canUseRecipe(world, serverPlayer, recipe)) {
                    stack = recipe.getCraftingResult(grid);
                }
            } else {
                optional = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, grid, world);
                if (optional.isPresent()) {
                    ICraftingRecipe recipe = optional.get();
                    if (canUseRecipe(world, serverPlayer, recipe)) {
                        stack = recipe.getCraftingResult(grid);
                    }
                }
            }
            result.setInventorySlotContents(0, stack);
            serverPlayer.connection.sendPacket(new SSetSlotPacket(windowId, 0, stack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.callable.consume((world, pos) -> onCraft(this.windowId, world, this.inventory.player));
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.callable.consume((world, pos) -> this.clearContainer(playerIn, world, grid));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.result && super.canMergeSlot(stack, slotIn);
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
        addSlot(new SlotItemHandler( output, 0,
            output.getXPos() + output.getSlotPosition().apply(0).getLeft(),
            output.getYPos() + output.getSlotPosition().apply(0).getRight()));
    }

    @Override
    public void addExtraSlots() {
        super.addExtraSlots();
        addOutputSlot();
        addGridSlots();
    }

    public PortableCrafter getPortableCrafter() {
        return portableCrafter;
    }

    public void setRecipeUsed(IRecipe<?> recipeUsed) {
        this.recipeUsed = recipeUsed;
    }

    public IRecipe<?> getRecipeUsed() {
        return recipeUsed;
    }

    public boolean canUseRecipe(World worldIn, ServerPlayerEntity player, IRecipe<?> recipe) {
        if (!recipe.isDynamic() && worldIn.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING) && !player.getRecipeBook().isUnlocked(recipe)) {
            return false;
        } else {
            this.setRecipeUsed(recipe);
            return true;
        }
    }

}

/*
public class PortableCrafterContainer extends BasicContainer {
    @ObjectHolder("essence:portable_crafter")
    public static ContainerType<PortableCrafterContainer> type;

    private PlayerEntity player;
    private PlayerInventory inventory;
    private PortableCrafter portableCrafter;
    private PortableCraftingInventory grid;
    private PortableCraftResultInventory result;
    private IWorldPosCallable callable;
    private boolean hasPlayerInventory;
    private boolean isDisabled = false;
    private Optional<ICraftingRecipe> optional;

    private int i = 0;

    public PortableCrafterContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        this((PortableCrafter) inventory.player.getHeldItem(Hand.valueOf(buffer.readString())).getItem(), inventory, id);
    }

    public PortableCrafterContainer(PortableCrafter portableCrafter, PlayerInventory inventory, int id) {
        super(type, id);
        this.player = inventory.player;
        this.inventory = inventory;
        this.portableCrafter = portableCrafter;
        this.grid = new PortableCraftingInventory(this, portableCrafter.getGrid());
        this.result = new PortableCraftResultInventory(portableCrafter.getOutput());
        this.callable = IWorldPosCallable.of(inventory.player.world, inventory.player.getPosition());
        addResultSlot();
        addGridSlots();
        addPlayerChestInventory();
        addHotbarSlots();
        this.i = 0;
    }

    public PortableCrafterContainer(PortableCrafter portableCrafter, PlayerInventory inventory, int id, IAssetProvider provider) {
        super(type, id, provider);
        this.player = inventory.player;
        this.inventory = inventory;
        this.portableCrafter = portableCrafter;
        this.grid = new PortableCraftingInventory(this, portableCrafter.getGrid());
        this.result = new PortableCraftResultInventory(portableCrafter.getOutput());
        this.callable = IWorldPosCallable.of(inventory.player.world, inventory.player.getPosition());
        addResultSlot();
        addGridSlots();
        addPlayerChestInventory();
        addHotbarSlots();
        this.i = 0;
    }

    public void addResultSlot() {
        InventoryComponent<?> component = this.portableCrafter.getOutput();
        this.addSlot(new CraftingResultSlot(player, this.grid, this.result, i,
            component.getXPos() + component.getSlotPosition().apply(0).getLeft(),
            component.getYPos() + component.getSlotPosition().apply(0).getRight()));
            i++;
    }

    public void addGridSlots() {
        InventoryComponent<?> component = this.portableCrafter.getOutput();
        for (int j = 0; j < component.getSlots(); j++) {
            addSlot(new Slot(
                this.grid, i,
                component.getXPos() + component.getSlotPosition().apply(i - 1).getLeft(),
                component.getYPos() + component.getSlotPosition().apply(i - 1).getRight())
            );
            i++;
        }
    }

    public void addPlayerChestInventory() {
        Point invPos = IAssetProvider.getAsset(getAssetProvider(), AssetTypes.BACKGROUND).getInventoryPosition();
        if (!hasPlayerInventory) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    addSlot(new PortableCrafterDisableableSlot(this.inventory, i, invPos.x + j * 18, invPos.y + i * 18, this));
                    i++;
                }
            }
            hasPlayerInventory = true;
        }
    }

    public void addHotbarSlots() {
        Point hotbarPos = IAssetProvider.getAsset(getAssetProvider(), AssetTypes.BACKGROUND).getHotbarPosition();
        for (int k = 0; k < 9; k++) {
            addSlot(new Slot(this.inventory, i, hotbarPos.x + k * 18, hotbarPos.y));
            i++;
        }
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
        this.callable.consume((world, pos) -> onCraft(this.windowId, world, this.inventory.player, this.grid, this.result));
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.callable.consume((world, pos) -> this.clearContainer(playerIn, world, this.grid));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.grid && super.canMergeSlot(stack, slotIn);
    }

    public PlayerInventory getPlayerInventory() {
        return this.inventory;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public PortableCrafter getPortableCrafter() {
        return portableCrafter;
    }
}
*/
