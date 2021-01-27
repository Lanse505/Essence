package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import java.util.Arrays;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class ExtendableInfusionRecipe extends SerializableRecipe {

  public Ingredient infusable;
  public List<Ingredient> inputIngredients;
  public int duration;

  public ExtendableInfusionRecipe(ResourceLocation id) {
    super(id);
  }

  public ExtendableInfusionRecipe(ResourceLocation id, Ingredient infusable, List<Ingredient> inputIngredients, int duration) {
    super(id);
    this.infusable = infusable;
    this.inputIngredients = inputIngredients;
    this.duration = duration;
  }

  @Override
  public boolean matches(IInventory inv, World worldIn) {
    return false;
  }

  @Override
  public ItemStack getCraftingResult(IInventory inv) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canFit(int width, int height) {
    return false;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return ItemStack.EMPTY;
  }

  public boolean isValid(NonNullList<ItemStack> stacks) {
    return inputIngredients.stream().map(list -> Arrays.stream(list.getMatchingStacks()).map(tagStack -> stacks.stream().map(tagStack::isItemEqual))).anyMatch(booleanStream -> booleanStream.findAny().isPresent() && booleanStream.findAny().get().anyMatch(Boolean::booleanValue));
  }

  public ItemStack resolveRecipe(ItemStack stack) {
    return stack;
  }
}
