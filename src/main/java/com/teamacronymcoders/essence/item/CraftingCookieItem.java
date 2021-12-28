package com.teamacronymcoders.essence.item;

import com.teamacronymcoders.essence.client.container.PortableWorkbenchMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class CraftingCookieItem extends Item implements MenuProvider {

  public CraftingCookieItem(Item.Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
    if (!pLevel.isClientSide()) {
      NetworkHooks.openGui((ServerPlayer) pLivingEntity, this);
      pStack.shrink(1);
    }
    return super.finishUsingItem(pStack, pLevel, pLivingEntity);
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerEntity) {
    return new PortableWorkbenchMenu(id, playerInventory, ContainerLevelAccess.create(playerEntity.getLevel(), playerEntity.blockPosition()));
  }

  @Override
  public Component getDisplayName() {
    return new TranslatableComponent("container.essence.portable.crafter");
  }
}
