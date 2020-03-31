package com.teamacronymcoders.essence.item.misc;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;

public class TomeOfKnowledgeItem extends Item {

    public TomeOfKnowledgeItem() {
        super(new Item.Properties().group(Essence.CORE_TAB).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.getCapability(EssenceCapability.KNOWLEDGE)
                .ifPresent(playerKnowledge -> stack.getCapability(EssenceCapability.KNOWLEDGE)
                    .ifPresent(itemKnowledge -> itemKnowledge.deserializeNBT(playerKnowledge.serializeNBT())));
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

}
