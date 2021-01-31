package com.teamacronymcoders.essence.item;

import com.teamacronymcoders.essence.client.container.PortableWorkbenchContainer;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PortableCrafterItem extends Item implements INamedContainerProvider {

  public PortableCrafterItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack stack = playerIn.getHeldItem(handIn);
    if (!worldIn.isRemote && stack.getItem() == this) {
      NetworkHooks.openGui((ServerPlayerEntity) playerIn, this);
    }
    return new ActionResult<>(ActionResultType.SUCCESS, stack);
  }

  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
    return new PortableWorkbenchContainer(id, playerInventory, IWorldPosCallable.of(playerEntity.getEntityWorld(), playerEntity.getPosition()));
  }

  @Override
  public ITextComponent getDisplayName() {
    return new TranslationTextComponent("container.essence.portable.crafter");
  }
}
