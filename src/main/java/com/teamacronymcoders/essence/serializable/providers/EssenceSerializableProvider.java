package com.teamacronymcoders.essence.serializable.providers;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.serializable.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.serializable.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.serializable.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.serializable.recipe.tool.ShovelPathingRecipe;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class EssenceSerializableProvider extends TitaniumSerializableProvider {

    public EssenceSerializableProvider(DataGenerator generatorIn) {
        super(generatorIn, Essence.MODID);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> map) {
        AxeStrippingRecipe.RECIPES.forEach(axeStrippingRecipe -> map.put(axeStrippingRecipe, axeStrippingRecipe));
        HoeTillingRecipe.RECIPES.forEach(hoeTillingRecipe -> map.put(hoeTillingRecipe, hoeTillingRecipe));
        InfusionTableSerializableRecipe.RECIPES.forEach(infusionTableSerializableRecipe -> map.put(infusionTableSerializableRecipe, infusionTableSerializableRecipe));
        ShovelPathingRecipe.RECIPES.forEach(shovelPathingRecipe -> map.put(shovelPathingRecipe, shovelPathingRecipe));
    }
}
