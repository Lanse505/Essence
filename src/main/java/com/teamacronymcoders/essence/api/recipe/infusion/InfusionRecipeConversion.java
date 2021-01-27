package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class InfusionRecipeConversion extends ExtendableInfusionRecipe {

  public static GenericSerializer<InfusionRecipeConversion> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "infusion_conversion"), InfusionRecipeConversion.class);
  public static List<InfusionRecipeConversion> RECIPES = new ArrayList<>();

  static {
    //RECIPES.add(new InfusionRecipeConversion(new ResourceLocation(Essence.MOD_ID, "test_conversion_ingot"), Ingredient.fromTag(Tags.Items.INGOTS_IRON), Lists.newArrayList(Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), Ingredient.fromTag(Tags.Items.GEMS_DIAMOND), Ingredient.fromTag(Tags.Items.GEMS_DIAMOND)), new ItemStack(EssenceItemRegistrate.ESSENCE_INGOT.get()), 100));
  }

  public Ingredient infusable;
  public List<Ingredient> inputIngredients;
  public ItemStack output;
  public int duration;

  public InfusionRecipeConversion(ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public InfusionRecipeConversion(ResourceLocation id, Ingredient infusable, List<Ingredient> inputIngredients, ItemStack output, int duration) {
    super(id, infusable, inputIngredients, duration);
    this.output = output;
  }

  @Override
  public boolean isValid(NonNullList<ItemStack> stacks) {
    return super.isValid(stacks);
  }

  @Override
  public ItemStack resolveRecipe(ItemStack stack) {
    return output;
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
