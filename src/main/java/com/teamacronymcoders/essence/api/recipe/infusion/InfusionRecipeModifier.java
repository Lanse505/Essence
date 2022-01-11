package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.common.util.helper.EssenceBowHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceJsonHelper;
import com.teamacronymcoders.essence.common.util.helper.recipe.EssenceModifierRecipeHelper;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.data.ingredient.TierIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.data.loading.DatagenModLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InfusionRecipeModifier extends ExtendableInfusionRecipe {

    public static GenericSerializer<InfusionRecipeModifier> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "infusion/infusion_modifier"), InfusionRecipeModifier.class);
    public static List<InfusionRecipeModifier> RECIPES = new ArrayList<>();

    static {
        if (DatagenModLoader.isRunningDataGen()) {
            RECIPES.add(
                    new InfusionRecipeModifier(
                            new ResourceLocation(Essence.MOD_ID, "test_modifier_add"),
                            TierIngredient.of(EssenceTags.EssenceItemTags.ESSENCE_SWORD, EssenceToolTiers.ESSENCE, Optional.empty()),
                            new Ingredient[]{
                                    Ingredient.of(Tags.Items.GEMS_QUARTZ), Ingredient.of(Tags.Items.GEMS_QUARTZ),
                                    Ingredient.of(Tags.Items.GEMS_QUARTZ), Ingredient.of(Tags.Items.GEMS_QUARTZ),
                                    Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL),
                                    Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL)
                            },
                            SerializableModifier.getSerializableModifiers(
                                    new SerializableModifier(
                                            EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(),
                                            1,
                                            null,
                                            InfusionOperation.ADD
                                    )
                            ),
                            100
                    )
            );
            RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_remove"), TierIngredient.of(EssenceTags.EssenceItemTags.ESSENCE_SWORD, EssenceToolTiers.ESSENCE, Optional.empty()), new Ingredient[]{Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL)}, SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.REMOVE)), 100));
            RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_increment"), TierIngredient.of(EssenceTags.EssenceItemTags.ESSENCE_SWORD, EssenceToolTiers.ESSENCE, Optional.empty()), new Ingredient[]{Ingredient.of(Tags.Items.GEMS_QUARTZ), Ingredient.of(Tags.Items.GEMS_QUARTZ), Ingredient.of(Tags.Items.GEMS_QUARTZ), Ingredient.of(Tags.Items.GEMS_QUARTZ), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED)}, SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.INCREMENT)), 100));
            RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_decrement"), TierIngredient.of(EssenceTags.EssenceItemTags.ESSENCE_SWORD, EssenceToolTiers.ESSENCE, Optional.empty()), new Ingredient[]{Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(Tags.Items.GEMS_PRISMARINE), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED)}, SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null, InfusionOperation.DECREMENT)), 100));
            RECIPES.add(new InfusionRecipeModifier(new ResourceLocation(Essence.MOD_ID, "test_modifier_merge_tags"), TierIngredient.of(EssenceTags.EssenceItemTags.ESSENCE_BOW, EssenceToolTiers.ESSENCE, Optional.empty()), new Ingredient[]{EssenceJsonHelper.getNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), EssenceJsonHelper.getNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), EssenceJsonHelper.getNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), EssenceJsonHelper.getNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), Ingredient.of(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED)}, SerializableModifier.getSerializableModifiers(new SerializableModifier(EssenceModifierRegistrate.BREWED_MODIFIER.get(), 1, EssenceBowHelper.createEffectInstanceNBT(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 5)), InfusionOperation.MERGE_TAGS)), 100));
        }
    }

    public SerializableModifier[] modifiers;

    public InfusionRecipeModifier(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public InfusionRecipeModifier(ResourceLocation id, TierIngredient infusable, Ingredient[] inputIngredients, SerializableModifier[] modifiers, int duration) {
        super(id, infusable, inputIngredients, duration);
        this.modifiers = modifiers;
    }

    @Override
    public boolean isValid(ItemStack infusable, NonNullList<ItemStack> stacks) {
        return super.isValid(infusable, stacks);
    }

    @Override
    public ItemStack resolveRecipe(InfusionTableBlockEntity be, ItemStack stack) {
        EssenceModifierRecipeHelper.resolveRecipe(stack, modifiers);
        //be.getPedestals().forEach(pedestal -> pedestal.markComponentForUpdate(false));
        //be.getPedestalStacks().forEach(pedestalItem -> pedestalItem.shrink(1));
        return stack;
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SERIALIZER.getRecipeType();
    }

}
