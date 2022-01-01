package com.teamacronymcoders.essence.compat.jei.categories;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionRecipeConversion;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;

public class InfusionConversionCategory extends BaseCategory<InfusionRecipeConversion> {

    public static final ResourceLocation ID = new ResourceLocation(Essence.MOD_ID, "infusion_conversion");


    public InfusionConversionCategory(IGuiHelper guiHelper, ResourceLocation categoryId, String localizedName, IDrawable background, IDrawable icon, Class<? extends InfusionRecipeConversion> recipeClass) {
        super(guiHelper, categoryId, localizedName, background, icon, recipeClass);
    }
}
