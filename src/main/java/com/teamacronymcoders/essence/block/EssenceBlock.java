package com.teamacronymcoders.essence.block;

import com.hrznstudio.titanium.api.IFactory;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EssenceBlock extends Block {
  private final EssenceItemTiers tier;

  public EssenceBlock (Block.Properties properties, EssenceItemTiers tier) {
    super(properties);
    this.tier = tier;
  }

  public IFactory<BlockItem> getBlockItem(Item.Properties properties) {
    return () -> (BlockItem) new BlockItem(this, properties.rarity(tier.getRarity())) {
      @Override
      public void addInformation (@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flagIn) {
        list.add(new TranslationTextComponent("tooltip.essence.tool.tier").mergeStyle(TextFormatting.GRAY).append(new TranslationTextComponent(tier.getLocaleString()).mergeStyle(tier.getRarity().color)));
      }
    };
  }
}
