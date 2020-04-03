package com.teamacronymcoders.essence.serializable.provider.recipe;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class EssenceVanillaRecipeProvider extends TitaniumRecipeProvider {
    public EssenceVanillaRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void register(Consumer<IFinishedRecipe> consumer) {
        BasicBlock.BLOCKS.stream()
            .filter(basicBlock -> basicBlock.getRegistryName().getNamespace().equals(Essence.MODID))
            .forEach(basicBlock -> basicBlock.registerRecipe(consumer));
        TitaniumShapelessRecipeBuilder.shapelessRecipe(Items.STONE_BRICKS).addIngredient(EssenceTags.Items.ESSENCE_BRICKS).build(consumer, new ResourceLocation(Essence.MODID, "essence_brick_to_stone_brick"));
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BRICKS_CYAN)
            .patternLine("bbb").patternLine("bnb").patternLine("bbb")
            .key('b', ItemTags.STONE_BRICKS).key('n', EssenceTags.Items.ESSENCE_INFUSED_METAL_NUGGET)
            .build(consumer, new ResourceLocation(Essence.MODID));
    }
}
