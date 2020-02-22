package com.teamacronymcoders.essence.impl.serializable;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import com.teamacronymcoders.essence.impl.serializable.recipe.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class EssenceSerializableProvider extends TitaniumSerializableProvider {

    public EssenceSerializableProvider(DataGenerator generatorIn) {
        super(generatorIn, EssenceReferences.MODID);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> map) {
        InfusionTableSerializableRecipe.RECIPES.forEach(infusionTableSerializableRecipe -> map.put(infusionTableSerializableRecipe, infusionTableSerializableRecipe));
    }
}
