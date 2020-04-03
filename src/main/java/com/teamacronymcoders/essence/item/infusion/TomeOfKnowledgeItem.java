package com.teamacronymcoders.essence.item.infusion;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;

public class TomeOfKnowledgeItem extends Item {

    private static final String playerUUID = "PLAYER_UUID";

    public TomeOfKnowledgeItem() {
        super(new Item.Properties().group(Essence.CORE_TAB).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        PlayerEntity player = context.getPlayer();
        CompoundNBT compoundNBT = stack.getOrCreateTag();
        if (player != null && !compoundNBT.contains(playerUUID)) {
            compoundNBT.putUniqueId(playerUUID, player.getUniqueID());
        }
        return super.onItemUse(context);
    }

}
