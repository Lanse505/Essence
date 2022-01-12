package com.teamacronymcoders.essence.common.item.tome;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class TomeOfKnowledgeItem extends Item {

    private static final String playerUUID = "PLAYER_UUID";

    public TomeOfKnowledgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        CompoundTag compoundNBT = stack.getOrCreateTag();
        if (player != null && !compoundNBT.contains(playerUUID)) {
            compoundNBT.putUUID(playerUUID, player.getUUID());
        }
        return super.useOn(context);
    }

}
