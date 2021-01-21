package com.teamacronymcoders.essence.item.tome;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;

public class TomeOfKnowledgeItem extends TomeItem {

  private static final String playerUUID = "PLAYER_UUID";

  public TomeOfKnowledgeItem (Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onItemUse (ItemUseContext context) {
    ItemStack stack = context.getItem();
    PlayerEntity player = context.getPlayer();
    CompoundNBT compoundNBT = stack.getOrCreateTag();
    if (player != null && !compoundNBT.contains(playerUUID)) {
      compoundNBT.putUniqueId(playerUUID, player.getUniqueID());
    }
    return super.onItemUse(context);
  }

}
