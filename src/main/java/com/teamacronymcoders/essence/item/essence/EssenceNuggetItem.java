package com.teamacronymcoders.essence.item.essence;

import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class EssenceNuggetItem extends Item {

  private final EssenceItemTiers tier;

  public EssenceNuggetItem(Properties properties, EssenceItemTiers tier) {
    super(properties.rarity(tier.getRarity()));
    this.tier = tier;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
    tooltip.add(new TranslatableComponent("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent(tier.getLocaleString()).withStyle(tier.getRarity().color)));
  }
}
