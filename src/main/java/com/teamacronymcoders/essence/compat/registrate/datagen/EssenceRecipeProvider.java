package com.teamacronymcoders.essence.compat.registrate.datagen;

import com.teamacronymcoders.essence.compat.registrate.datagen.recipe.EssenceSerializableProvider;
import net.minecraft.data.DataGenerator;

public class EssenceRecipeProvider {

    public static void addRecipeProviders(DataGenerator generator) {
        generator.addProvider(new EssenceSerializableProvider(generator));
    }
}
