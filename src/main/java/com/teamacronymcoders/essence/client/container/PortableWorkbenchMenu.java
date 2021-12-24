package com.teamacronymcoders.essence.client.container;

import com.teamacronymcoders.essence.registrate.EssenceGUIRegistrate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class PortableWorkbenchMenu extends RecipeBookMenu<CraftingContainer> {

  private final CraftingContainer craftMatrix = new CraftingContainer(this, 3, 3);
  private final ResultContainer craftResult = new ResultContainer();
  private final ContainerLevelAccess access;
  private final Player player;

  public PortableWorkbenchMenu(int id, Inventory playerInventory) {
    this(id, playerInventory, ContainerLevelAccess.NULL);
  }

  public PortableWorkbenchMenu(int id, Inventory playerInventory, ContainerLevelAccess callable) {
    super(EssenceGUIRegistrate.PORTABLE_WORKBENCH.get(), id);
    this.access = callable;
    this.player = playerInventory.player;
    this.addSlot(new ResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
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

  public <T extends AbstractContainerMenu> PortableWorkbenchMenu(MenuType<T> tContainerType, int id, Inventory playerInventory, FriendlyByteBuf buffer) {
    this(id, playerInventory);
  }

  public PortableWorkbenchMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
    this(id, inventory);
  }

  protected static void slotChangedCraftingGrid(AbstractContainerMenu id, Level level, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer) {
    if (!level.isClientSide()) {
      ServerPlayer serverplayerentity = (ServerPlayer) player;
      ItemStack itemstack = ItemStack.EMPTY;
      Optional<CraftingRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingContainer, level);
      if (optional.isPresent()) {
        CraftingRecipe craftingRecipe = optional.get();
        if (resultContainer.setRecipeUsed(level, serverplayerentity, craftingRecipe)) {
          itemstack = craftingRecipe.getResultItem();
        }
      }

      resultContainer.setItem(0, itemstack);
      serverplayerentity.connection.send(new ClientboundContainerSetSlotPacket(id.containerId, id.incrementStateId(), 0, itemstack));
    }
  }

  @Override
  public void slotsChanged(Container container) {
    this.access.execute((world, pos) -> {
      slotChangedCraftingGrid(this, world, this.player, this.craftMatrix, this.craftResult);
    });
  }

  @Override
  public void removed(Player player) {
    super.removed(player);
    this.access.execute((world, pos) -> {
      this.clearContainer(player, this.craftMatrix);
    });
  }

  @Override
  public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
    return slot.container != this.craftResult && super.canTakeItemForPickAll(stack, slot);
  }

  public ItemStack quickMoveStack(Player player, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(index);
    if (slot.hasItem()) {
      ItemStack slotItem = slot.getItem();
      itemstack = slotItem.copy();
      if (index == 0) {
        this.access.execute((world, pos) -> {
          slotItem.getItem().onCraftedBy(slotItem, world, player);
        });
        if (!this.moveItemStackTo(slotItem, 10, 46, true)) {
          return ItemStack.EMPTY;
        }

        slot.onQuickCraft(slotItem, itemstack);
      } else if (index >= 10 && index < 46) {
        if (!this.moveItemStackTo(slotItem, 1, 10, false)) {
          if (index < 37) {
            if (!this.moveItemStackTo(slotItem, 37, 46, false)) {
              return ItemStack.EMPTY;
            }
          } else if (!this.moveItemStackTo(slotItem, 10, 37, false)) {
            return ItemStack.EMPTY;
          }
        }
      } else if (!this.moveItemStackTo(slotItem, 10, 46, false)) {
        return ItemStack.EMPTY;
      }

      if (slotItem.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }

      if (slotItem.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }

      slot.onTake(player, slotItem);
      if (index == 0) {
        player.drop(slotItem, false);
      }
    }

    return itemstack;
  }


  @Override
  public void fillCraftSlotsStackedContents(StackedContents contents) {
    this.craftMatrix.fillStackedContents(contents);
  }

  @Override
  public void clearCraftingContent() {
    this.craftMatrix.clearContent();
    this.craftResult.clearContent();
  }

  @Override
  public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
    return recipe.matches(this.craftMatrix, this.player.level);
  }

  @Override
  public int getResultSlotIndex() {
    return 0;
  }

  @Override
  public int getGridWidth() {
    return this.craftMatrix.getWidth();
  }

  @Override
  public int getGridHeight() {
    return this.craftMatrix.getHeight();
  }

  @Override
  public int getSize() {
    return 10;
  }

  @Override
  public RecipeBookType getRecipeBookType() {
    return RecipeBookType.CRAFTING;
  }

  @Override
  public boolean shouldMoveToInventory(int slotId) {
    return false;
  }

  @Override
  public boolean stillValid(Player player) {
    return true;
  }
}
