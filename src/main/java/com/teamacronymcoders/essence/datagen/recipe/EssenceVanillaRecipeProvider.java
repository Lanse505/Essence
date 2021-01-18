package com.teamacronymcoders.essence.datagen.recipe;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceItemTags;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullLazy;

public class EssenceVanillaRecipeProvider extends TitaniumRecipeProvider {

  private final NonNullLazy<List<Block>> blocks;

  public EssenceVanillaRecipeProvider (DataGenerator generatorIn, NonNullLazy<List<Block>> blocks) {
    super(generatorIn);
    this.blocks = blocks;
  }

  @Override
  public void register (Consumer<IFinishedRecipe> consumer) {
    this.blocks.get().stream()
            .filter(block -> block instanceof BasicBlock)
            .map(block -> (BasicBlock) block)
            .forEach(block -> block.registerRecipe(consumer));
  }

  @Override
  public String getName () {
    return "Essence Recipes - Generic";
  }
}
