package com.teamacronymcoders.essence.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class BaseCategory<T> implements IRecipeCategory<T> {

    private final ResourceLocation categoryId;
    private final String localizedName;
    private final IDrawable background;
    private final IDrawable icon;
    private final Class<? extends T> recipeClass;

    public BaseCategory(IGuiHelper guiHelper, ResourceLocation categoryId, String localizedName, IDrawable background, IDrawable icon, Class<? extends T> recipeClass) {
        this.categoryId = categoryId;
        this.localizedName = localizedName;
        this.background = background;
        this.icon = icon;
        this.recipeClass = recipeClass;
    }

    @Override
    public ResourceLocation getUid() {
        return categoryId;
    }

    @Override
    public Class<? extends T> getRecipeClass() {
        return recipeClass;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(T recipe, IIngredients ingredients) {
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T recipe, IIngredients ingredients) {
    }
}
