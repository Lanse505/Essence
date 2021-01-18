package com.teamacronymcoders.essence.item.essence;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EssenceNuggetItem extends Item {

  private final EssenceItemTiers tier;

  public EssenceNuggetItem (Properties properties, EssenceItemTiers tier) {
    super(properties.rarity(tier.getRarity()));
    this.tier = tier;
  }

  @Override
  public void addInformation (ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent("tooltip.essence.tool.tier").mergeStyle(TextFormatting.GRAY).append(new TranslationTextComponent(tier.getLocaleString()).mergeStyle(tier.getRarity().color)));
  }
}
