package com.teamacronymcoders.essence.block;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EssenceBlock extends BasicBlock {
  private final EssenceItemTiers tier;

  public EssenceBlock (EssenceItemTiers tier) {
    super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).speedFactor(1.25f));
    setItemGroup(Essence.CORE_TAB);
    this.tier = tier;
  }

  @Override
  public IFactory<BlockItem> getItemBlockFactory () {
    return () -> (BlockItem) new BlockItem(this, new Item.Properties().group(this.getItemGroup()).rarity(tier.getRarity())) {
      @Override
      public void addInformation (ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(new TranslationTextComponent("tooltip.essence.tool.tier").mergeStyle(TextFormatting.GRAY).append(new TranslationTextComponent(tier.getLocaleString()).mergeStyle(tier.getRarity().color)));
      }
    }.setRegistryName(Objects.requireNonNull(this.getRegistryName()));
  }

  @Override
  public void registerRecipe (Consumer<IFinishedRecipe> consumer) {
    ResourceLocation rl = getRegistryName();
    TitaniumShapedRecipeBuilder.shapedRecipe(tier.getIngot().get())
            .patternLine("nnn").patternLine("nnn").patternLine("nnn")
            .key('n', tier.getNugget().get()).build(consumer, new ResourceLocation(rl.getNamespace(), rl.getPath() + "_nugget_to_ingot"));
    TitaniumShapelessRecipeBuilder.shapelessRecipe(tier.getNugget().get(), 9).addIngredient(Ingredient.fromItems(tier.getIngot().get())).build(consumer, new ResourceLocation(rl.getNamespace(), rl.getPath() + "_ingot_to_nuggets"));
    TitaniumShapedRecipeBuilder.shapedRecipe(this)
            .patternLine("iii").patternLine("iii").patternLine("iii")
            .key('i', tier.getIngot().get()).build(consumer, new ResourceLocation(rl.getNamespace(), rl.getPath() + "_ingot_to_block"));
    TitaniumShapelessRecipeBuilder.shapelessRecipe(tier.getIngot().get(), 9).addIngredient(Ingredient.fromItems(this)).build(consumer, new ResourceLocation(rl.getNamespace(), rl.getPath() + "_block_to_ingots"));
  }
}
