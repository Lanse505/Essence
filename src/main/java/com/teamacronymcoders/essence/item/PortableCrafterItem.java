package com.teamacronymcoders.essence.item;

import com.teamacronymcoders.essence.client.container.PortableWorkbenchMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class PortableCrafterItem extends Item implements MenuProvider {

  public PortableCrafterItem(Item.Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    ItemStack stack = playerIn.getItemInHand(handIn);
    if (!worldIn.isClientSide() && stack.getItem() == this) {
      NetworkHooks.openGui((ServerPlayer) playerIn, this);
    }
    return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
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
