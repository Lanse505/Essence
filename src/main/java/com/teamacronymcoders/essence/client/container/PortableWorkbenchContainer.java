package com.teamacronymcoders.essence.client.container;

import com.teamacronymcoders.essence.registrate.EssenceGUIRegistrate;
import java.util.Optional;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public class PortableWorkbenchContainer extends RecipeBookContainer<CraftingInventory> {

  private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
  private final CraftResultInventory craftResult = new CraftResultInventory();
  private final IWorldPosCallable worldPosCallable;
  private final PlayerEntity player;

  public PortableWorkbenchContainer(int id, PlayerInventory playerInventory) {
    this(id, playerInventory, IWorldPosCallable.DUMMY);
  }

  public PortableWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable callable) {
    super(EssenceGUIRegistrate.PORTABLE_WORKBENCH.get(), id);
    this.worldPosCallable = callable;
    this.player = playerInventory.player;
    this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        this.addSlot(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
      }
    }
    for (int k = 0; k < 3; ++k) {
      for (int i1 = 0; i1 < 9; ++i1) {
        this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
      }
    }
    for (int l = 0; l < 9; ++l) {
      this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
    }
  }

  public <T extends Container> PortableWorkbenchContainer(ContainerType<T> tContainerType, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
    this(id, playerInventory);
  }

  public PortableWorkbenchContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
    this(id, playerInventory);
  }

  protected static void updateCraftingResult(int id, World world, PlayerEntity player, CraftingInventory inventory, CraftResultInventory inventoryResult) {
    if (!world.isRemote) {
      ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
      ItemStack itemstack = ItemStack.EMPTY;
      Optional<ICraftingRecipe> optional = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, inventory, world);
      if (optional.isPresent()) {
        ICraftingRecipe icraftingrecipe = optional.get();
        if (inventoryResult.canUseRecipe(world, serverplayerentity, icraftingrecipe)) {
          itemstack = icraftingrecipe.getCraftingResult(inventory);
        }
      }

      inventoryResult.setInventorySlotContents(0, itemstack);
      serverplayerentity.connection.sendPacket(new SSetSlotPacket(id, 0, itemstack));
    }
  }

  public void onCraftMatrixChanged(IInventory inventoryIn) {
    this.worldPosCallable.consume((world, pos) -> {
      updateCraftingResult(this.windowId, world, this.player, this.craftMatrix, this.craftResult);
    });
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return true;
  }

  public void onContainerClosed(PlayerEntity playerIn) {
    super.onContainerClosed(playerIn);
    this.worldPosCallable.consume((world, pos) -> {
      this.clearContainer(playerIn, world, this.craftMatrix);
    });
  }

  @Override
  public void fillStackedContents(RecipeItemHelper itemHelperIn) {
    this.craftMatrix.fillStackedContents(itemHelperIn);
  }

  public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
    return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
  }

  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(index);
    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (index == 0) {
        this.worldPosCallable.consume((world, pos) -> {
          itemstack1.getItem().onCreated(itemstack1, world, playerIn);
        });
        if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
          return ItemStack.EMPTY;
        }

        slot.onSlotChange(itemstack1, itemstack);
      } else if (index >= 10 && index < 46) {
        if (!this.mergeItemStack(itemstack1, 1, 10, false)) {
          if (index < 37) {
            if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
              return ItemStack.EMPTY;
            }
          } else if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
            return ItemStack.EMPTY;
          }
        }
      } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.onSlotChanged();
      }

      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }

      ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
      if (index == 0) {
        playerIn.dropItem(itemstack2, false);
      }
    }

    return itemstack;
  }

  @Override
  public void clear() {
    this.craftMatrix.clear();
    this.craftResult.clear();
  }

  @Override
  public boolean matches(IRecipe<? super CraftingInventory> recipeIn) {
    return recipeIn.matches(this.craftMatrix, this.player.world);
  }

  @Override
  public int getOutputSlot() {
    return 0;
  }

  @Override
  public int getWidth() {
    return this.craftMatrix.getWidth();
  }

  @Override
  public int getHeight() {
    return this.craftMatrix.getHeight();
  }

  @Override
  public int getSize() {
    return 10;
  }

  @Override
  // getBookCategory
  public RecipeBookCategory func_241850_m() {
    return RecipeBookCategory.CRAFTING;
  }
}
