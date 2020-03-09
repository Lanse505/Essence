package com.teamacronymcoders.essence.serializable.providers;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.serializable.recipe.InfusionTableSerializableRecipe;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class EssenceSerializableProvider extends TitaniumSerializableProvider {

    public EssenceSerializableProvider(DataGenerator generatorIn) {
        super(generatorIn, Essence.MODID);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> map) {
        InfusionTableSerializableRecipe.RECIPES.forEach(infusionTableSerializableRecipe -> map.put(infusionTableSerializableRecipe, infusionTableSerializableRecipe));
    }
}