package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.recipe.EssenceSerializableProvider;
import net.minecraft.data.DataGenerator;

public class EssenceRecipeProvider {

  public static void addRecipeProviders (DataGenerator generator) {
    generator.addProvider(new EssenceSerializableProvider(generator));
  }
}
