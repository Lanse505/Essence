package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.recipe.EssenceSerializableProvider;
import com.teamacronymcoders.essence.datagen.recipe.EssenceToolRecipeProvider;
import com.teamacronymcoders.essence.datagen.recipe.EssenceVanillaRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.List;

public class EssenceRecipeProvider {

    public static void addRecipeProviders(DataGenerator generator, NonNullLazy<List<Block>> blocks) {
        generator.addProvider(new EssenceVanillaRecipeProvider(generator, blocks));
        generator.addProvider(new EssenceToolRecipeProvider(generator));
        generator.addProvider(new EssenceSerializableProvider(generator));
    }
}
