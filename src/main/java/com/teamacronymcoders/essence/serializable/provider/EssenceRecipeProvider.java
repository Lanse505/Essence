package com.teamacronymcoders.essence.serializable.provider;

import com.teamacronymcoders.essence.serializable.provider.recipe.EssenceSerializableProvider;
import com.teamacronymcoders.essence.serializable.provider.recipe.EssenceToolRecipeProvider;
import com.teamacronymcoders.essence.serializable.provider.recipe.EssenceVanillaRecipeProvider;
import net.minecraft.data.DataGenerator;

public class EssenceRecipeProvider {

    public static void addRecipeProviders(DataGenerator generator) {
        generator.addProvider(new EssenceVanillaRecipeProvider(generator));
        generator.addProvider(new EssenceToolRecipeProvider(generator));
        generator.addProvider(new EssenceSerializableProvider(generator));
    }
}
