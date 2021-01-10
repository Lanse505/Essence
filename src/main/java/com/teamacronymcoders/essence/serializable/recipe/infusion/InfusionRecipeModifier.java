package com.teamacronymcoders.essence.serializable.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.util.helper.recipe.EssenceModifierRecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class InfusionRecipeModifier extends ExtendableInfusionRecipe {

    public static GenericSerializer<InfusionRecipeModifier> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "infusion_modifier"), InfusionRecipeModifier.class);
    public static List<InfusionRecipeModifier> RECIPES = new ArrayList<>();

    public Ingredient infusable;
    public Ingredient.IItemList[] inputIngredients;
    public SerializableModifier[] modifiers;
    public int duration;

    public InfusionRecipeModifier(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public InfusionRecipeModifier(ResourceLocation id, Ingredient infusable, Ingredient.IItemList[] inputIngredients, SerializableModifier[] modifiers, int duration) {
        super(id, infusable, inputIngredients, duration);
        this.modifiers = modifiers;
    }

    @Override
    public boolean isValid(NonNullList<ItemStack> stacks) {
        return super.isValid(stacks);
    }

    @Override
    public ItemStack resolveRecipe(ItemStack stack) {
        EssenceModifierRecipeHelper.resolveRecipe(stack, modifiers);
        return stack;
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return SERIALIZER.getRecipeType();
    }

}
