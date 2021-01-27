package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.helper.recipe.EssenceModifierRecipeHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class InfusionRecipeModifier extends ExtendableInfusionRecipe {

  public static GenericSerializer<InfusionRecipeModifier> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "infusion_modifier"), InfusionRecipeModifier.class);
  public static List<InfusionRecipeModifier> RECIPES = new ArrayList<>();

  static {
    //RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_add"), Ingredient.fromItems(EssenceItemRegistrate.ESSENCE_SWORD.get()), Lists.newArrayList(Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL)), SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.ADD)), 100));
    //RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_remove"), Ingredient.fromItems(EssenceItemRegistrate.ESSENCE_SWORD.get()), Lists.newArrayList(Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL)), SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.REMOVE)), 100));
    //RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_increment"), Ingredient.fromItems(EssenceItemRegistrate.ESSENCE_SWORD.get()), Lists.newArrayList(Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED)), SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.INCREMENT)), 100));
    //RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_decrement"), Ingredient.fromItems(EssenceItemRegistrate.ESSENCE_SWORD.get()), Lists.newArrayList(Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(Tags.Items.GEMS_PRISMARINE), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED)), SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.INCREMENT)), 100));
    //RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_merge_tags"), Ingredient.fromItems(EssenceItemRegistrate.ESSENCE_BOW.get()), Lists.newArrayList(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.fromTag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED)), SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.BREWED_MODIFIER.get(), 1, EssenceBowHelper.createEffectInstanceNBT(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 5)), InfusionOperation.MERGE_TAGS)), 100));
  }

  public Ingredient infusable;
  public List<Ingredient> inputIngredients;
  public List<SerializableModifier> modifiers;
  public int duration;

  public InfusionRecipeModifier(ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public InfusionRecipeModifier(ResourceLocation id, Ingredient infusable, List<Ingredient> inputIngredients, List<SerializableModifier> modifiers, int duration) {
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
