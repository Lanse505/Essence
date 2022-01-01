package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.data.loading.DatagenModLoader;

import java.util.ArrayList;
import java.util.List;

public class InfusionRecipeConversion extends ExtendableInfusionRecipe {

    public static GenericSerializer<InfusionRecipeConversion> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "infusion/infusion_conversion"), InfusionRecipeConversion.class);
    public static List<InfusionRecipeConversion> RECIPES = new ArrayList<>();

    static {
        if (DatagenModLoader.isRunningDataGen()) {
            RECIPES.add(
                    new InfusionRecipeConversion(
                            new ResourceLocation(Essence.MOD_ID, "test_conversion_ingot"),
                            Ingredient.of(Tags.Items.INGOTS_IRON),
                            new Ingredient[]{
                                    Ingredient.of(Tags.Items.GEMS_DIAMOND), Ingredient.of(Tags.Items.GEMS_DIAMOND),
                                    Ingredient.of(Tags.Items.GEMS_DIAMOND), Ingredient.of(Tags.Items.GEMS_DIAMOND)
                            },
                            new ItemStack(EssenceItemRegistrate.ESSENCE_INGOT.get()),
                            100
                    )
            );
        }
    }

    public ItemStack output;

    public InfusionRecipeConversion(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public InfusionRecipeConversion(ResourceLocation id, Ingredient infusable, Ingredient[] inputIngredients, ItemStack output, int duration) {
        super(id, infusable, inputIngredients, duration);
        this.output = output;
    }

    @Override
    public boolean isValid(ItemStack infusable, NonNullList<ItemStack> stacks) {
        return super.isValid(infusable, stacks);
    }

    @Override
    public ItemStack resolveRecipe(InfusionTableBlockEntity be, ItemStack stack) {
        be.getInfusable().setStackInSlot(0, ItemStack.EMPTY);
        be.getPedestals().forEach(pedestal -> pedestal.markComponentForUpdate(false));
        be.getPedestalStacks().forEach(pedestalItem -> pedestalItem.shrink(1));
        return output;
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
