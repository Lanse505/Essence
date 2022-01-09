package com.teamacronymcoders.essence.api.recipe.infusion;

import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public abstract class ExtendableInfusionRecipe extends SerializableRecipe {

    public Ingredient infusable;
    public Ingredient[] inputIngredients;
    public int duration;

    public ExtendableInfusionRecipe(ResourceLocation id) {
        super(id);
    }

    public ExtendableInfusionRecipe(ResourceLocation id, Ingredient infusable, Ingredient[] inputIngredients, int duration) {
        super(id);
        this.infusable = infusable;
        this.inputIngredients = inputIngredients;
        this.duration = duration;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container) {
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

    public boolean isValid(ItemStack infusable, NonNullList<ItemStack> pedestalContents) {
        if (!(this.infusable.test(infusable))) return false;
        NonNullList<ItemStack> copy = NonNullList.of(ItemStack.EMPTY, pedestalContents.toArray(new ItemStack[]{}));
        int approved = 0;
        int foundIndex = -1;
        for (Ingredient testable : inputIngredients) {
            for (ItemStack stack : copy) {
                if (testable.test(stack)) {
                    foundIndex = copy.indexOf(stack);
                    approved++;
                    break;
                }
            }
            if (foundIndex != -1) {
                copy.set(foundIndex, ItemStack.EMPTY);
                foundIndex = -1;
            }
        }
        return inputIngredients.length == approved;
    }

    public ItemStack resolveRecipe(InfusionTableBlockEntity be, ItemStack stack) {
        return stack;
    }
}
