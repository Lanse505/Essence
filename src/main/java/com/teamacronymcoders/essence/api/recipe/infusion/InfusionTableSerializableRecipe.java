package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import java.util.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfusionTableSerializableRecipe extends SerializableRecipe {

  public static GenericSerializer<InfusionTableSerializableRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "modifier_infusion"), InfusionTableSerializableRecipe.class);
  public static List<InfusionTableSerializableRecipe> RECIPES = new ArrayList<>();


  public Ingredient.IItemList[] inputList;
  public SerializableModifier[] modifiers;
  public int duration;

  public InfusionTableSerializableRecipe (ResourceLocation id) {
    super(id);
  }

  public InfusionTableSerializableRecipe (ResourceLocation id, Ingredient.IItemList[] inputList, SerializableModifier[] modifiers, int duration) {
    super(id);
    this.inputList = inputList;
    this.modifiers = modifiers;
    this.duration = duration;
  }

  @Override
  public boolean matches (IInventory p_77569_1_, World p_77569_2_) {
    return false;
  }

  @Override
  public ItemStack getCraftingResult (IInventory p_77572_1_) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canFit (int p_194133_1_, int p_194133_2_) {
    return false;
  }

  @Override
  public ItemStack getRecipeOutput () {
    return ItemStack.EMPTY;
  }

  @Override
  public GenericSerializer<? extends SerializableRecipe> getSerializer () {
    return SERIALIZER;
  }

  @Override
  public IRecipeType<?> getType () {
    return SERIALIZER.getRecipeType();
  }

  public boolean isValid (ItemStack stack) {
    return Arrays.stream(getInputList()).map(list -> list.getStacks().stream().map(tagStack -> tagStack.isItemEqual(stack))).anyMatch(booleanStream -> booleanStream.findAny().orElse(false));
  }

  private Ingredient.IItemList[] getInputList () {
    return this.inputList;
  }

  public Set<ItemStack> getCollectedStacks () {
    Set<ItemStack> stacks = new HashSet<>();
    for (Ingredient.IItemList itemList : getInputList()) {
      stacks.addAll(itemList.getStacks());
    }
    return stacks;
  }

  public SerializableModifier[] getModifiers () {
    return this.modifiers;
  }


  public int getDuration () {
    return duration;
  }
}
