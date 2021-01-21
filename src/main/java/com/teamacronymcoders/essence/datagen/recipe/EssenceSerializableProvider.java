package com.teamacronymcoders.essence.datagen.recipe;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.ShovelPathingRecipe;
import java.util.Map;
import net.minecraft.data.DataGenerator;

public class EssenceSerializableProvider extends TitaniumSerializableProvider {

  public EssenceSerializableProvider (DataGenerator generatorIn) {
    super(generatorIn, Essence.MOD_ID);
  }

  @Override
  public void add (Map<IJsonFile, IJSONGenerator> map) {
    AxeStrippingRecipe.RECIPES.forEach(axeStrippingRecipe -> map.put(axeStrippingRecipe, axeStrippingRecipe));
    HoeTillingRecipe.RECIPES.forEach(hoeTillingRecipe -> map.put(hoeTillingRecipe, hoeTillingRecipe));
    InfusionTableSerializableRecipe.RECIPES.forEach(infusionTableSerializableRecipe -> map.put(infusionTableSerializableRecipe, infusionTableSerializableRecipe));
    ShovelPathingRecipe.RECIPES.forEach(shovelPathingRecipe -> map.put(shovelPathingRecipe, shovelPathingRecipe));
  }

  @Override
  public String getName () {
    return "Essence Recipes - Custom";
  }
}
