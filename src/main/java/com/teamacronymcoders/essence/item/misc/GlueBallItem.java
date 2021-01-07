package com.teamacronymcoders.essence.item.misc;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.entity.impl.GlueBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class GlueBallItem extends Item {

    public GlueBallItem() {
        super(new Item.Properties().group(Essence.CORE_TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            GlueBallEntity entity = Util.make(new GlueBallEntity(world, player), glueball -> glueball.setItem(stack));
            world.addEntity(entity);
        }

        player.addStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.isCreativeMode) {
            stack.shrink(1);
        }

        return ActionResult.resultSuccess(stack);
    }
}
