package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.recipe.EssenceSerializableProvider;
import com.teamacronymcoders.essence.datagen.recipe.EssenceToolRecipeProvider;
import com.teamacronymcoders.essence.datagen.recipe.EssenceVanillaRecipeProvider;
import net.minecraft.data.DataGenerator;

public class EssenceRecipeProvider {

    public static void addRecipeProviders(DataGenerator generator) {
        generator.addProvider(new EssenceVanillaRecipeProvider(generator));
        generator.addProvider(new EssenceToolRecipeProvider(generator));
        generator.addProvider(new EssenceSerializableProvider(generator));
    }
}
