package com.teamacronymcoders.essence.datagen.recipe;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceItemTags;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.List;
import java.util.function.Consumer;

public class EssenceVanillaRecipeProvider extends TitaniumRecipeProvider {

    private final NonNullLazy<List<Block>> blocks;

    public EssenceVanillaRecipeProvider(DataGenerator generatorIn, NonNullLazy<List<Block>> blocks) {
        super(generatorIn);
        this.blocks = blocks;
    }

    @Override
    public void register(Consumer<IFinishedRecipe> consumer) {
        this.blocks.get().stream()
            .filter(block -> block instanceof BasicBlock)
            .map(block -> (BasicBlock) block)
            .forEach(block -> block.registerRecipe(consumer));
        TitaniumShapelessRecipeBuilder.shapelessRecipe(Items.STONE_BRICKS).addIngredient(EssenceItemTags.ESSENCE_BRICKS).build(consumer, new ResourceLocation(Essence.MODID, "essence_brick_to_stone_brick"));
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BRICKS_CYAN)
            .patternLine("bbb").patternLine("bnb").patternLine("bbb")
            .key('b', ItemTags.STONE_BRICKS).key('n', EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET)
            .build(consumer, new ResourceLocation(Essence.MODID));
    }

    @Override
    public String getName() {
        return "Essence Recipes - Generic";
    }
}
