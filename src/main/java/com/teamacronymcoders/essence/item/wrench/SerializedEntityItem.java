package com.teamacronymcoders.essence.item.wrench;

import com.teamacronymcoders.essence.client.render.tesr.itemstack.SerializableMobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class SerializedEntityItem extends Item {

    public SerializedEntityItem(Properties properties) {
        super(new Item.Properties().setISTER(() -> SerializableMobRenderer::new));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return null;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return null;
    }

    public static Entity getSerializedEntity(ItemStack stack) {
        // TODO:
        return null;
    }

}
