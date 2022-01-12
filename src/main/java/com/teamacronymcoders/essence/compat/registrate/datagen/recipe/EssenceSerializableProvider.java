package com.teamacronymcoders.essence.compat.registrate.datagen.recipe;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionRecipeConversion;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionRecipeModifier;
import com.teamacronymcoders.essence.api.recipe.tool.EssenceShearingRecipe;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class EssenceSerializableProvider extends TitaniumSerializableProvider {

    public EssenceSerializableProvider(DataGenerator generatorIn) {
        super(generatorIn, Essence.MOD_ID);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> map) {
        InfusionRecipeConversion.RECIPES.forEach(infusionRecipeConversion -> map.put(infusionRecipeConversion, infusionRecipeConversion));
        InfusionRecipeModifier.RECIPES.forEach(infusionRecipeModifier -> map.put(infusionRecipeModifier, infusionRecipeModifier));
        EssenceShearingRecipe.RECIPES.forEach(essenceShearingRecipe -> map.put(essenceShearingRecipe, essenceShearingRecipe));
    }

    @Override
    public String getName() {
        return "Essence Recipes - Custom";
    }
}
