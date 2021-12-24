package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.*;

public class InfusionTableSerializableRecipe extends SerializableRecipe {

  public static GenericSerializer<InfusionTableSerializableRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "modifier_infusion"), InfusionTableSerializableRecipe.class);
  public static List<InfusionTableSerializableRecipe> RECIPES = new ArrayList<>();

  public Ingredient.ItemValue[] inputList;
  public SerializableModifier[] modifiers;
  public int duration;

  public InfusionTableSerializableRecipe(ResourceLocation id) {
    super(id);
  }

  public InfusionTableSerializableRecipe(ResourceLocation id, Ingredient.ItemValue[] inputList, SerializableModifier[] modifiers, int duration) {
    super(id);
    this.inputList = inputList;
    this.modifiers = modifiers;
    this.duration = duration;
  }

  @Override
  public boolean matches(Container container, Level level) {
    return false;
  }

  @Override
  public ItemStack assemble(Container pContainer) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return false;
  }

  @Override
  public ItemStack getResultItem() {
    return ItemStack.EMPTY;
  }

  @Override
  public GenericSerializer<? extends SerializableRecipe> getSerializer() {
    return SERIALIZER;
  }

  @Override
  public RecipeType<?> getType() {
    return SERIALIZER.getRecipeType();
  }

  public boolean isValid(ItemStack stack) {
    return Arrays.stream(getInputList()).map(list -> list.getItems().stream().map(tagStack -> tagStack.sameItem(stack))).anyMatch(booleanStream -> booleanStream.findAny().orElse(false));
  }

  private Ingredient.ItemValue[] getInputList() {
    return this.inputList;
  }

  public Set<ItemStack> getCollectedStacks() {
    Set<ItemStack> stacks = new HashSet<>();
    for (Ingredient.ItemValue itemList : getInputList()) {
      stacks.addAll(itemList.getItems());
    }
    return stacks;
  }

  public SerializableModifier[] getModifiers() {
    return this.modifiers;
  }


  public int getDuration() {
    return duration;
  }
}
