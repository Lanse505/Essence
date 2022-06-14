package com.teamacronymcoders.essence.common.item;

import com.teamacronymcoders.essence.client.container.PortableWorkbenchMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class CraftingBiscuitItem extends Item implements MenuProvider {

    public CraftingBiscuitItem(Item.Properties properties) {
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
        return new PortableWorkbenchMenu(id, playerInventory, ContainerLevelAccess.create(playerEntity.level, playerEntity.m_20183_()));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.essence.crafting.biscuit");
    }
}
