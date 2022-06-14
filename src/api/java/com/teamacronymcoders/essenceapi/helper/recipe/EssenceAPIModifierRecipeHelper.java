package com.teamacronymcoders.essenceapi.helper.recipe;

import com.teamacronymcoders.essenceapi.helper.EssenceAPIItemstackModifierHelpers;
import com.teamacronymcoders.essenceapi.modifier.ModifierInstance;
import com.teamacronymcoders.essenceapi.recipe.infusion.SerializableModifier;
import net.minecraft.world.item.ItemStack;

public class EssenceAPIModifierRecipeHelper {

    public static ItemStack resolveRecipe(ItemStack stack, SerializableModifier[] modifiers) {
        for (SerializableModifier modifier : modifiers) {
            resolveOperationBehaviour(stack, modifier);
        }
        return stack;
    }

    public static void resolveOperationBehaviour(ItemStack stack, SerializableModifier modifier) {
        switch (modifier.getOperation()) {
            case ADD -> EssenceAPIItemstackModifierHelpers.addModifier(stack, modifier.getModifier(), modifier.getLevel(), modifier.getModifierData());
            case REMOVE -> EssenceAPIItemstackModifierHelpers.removeModifiers(stack, modifier.getModifier());
            case INCREMENT -> EssenceAPIItemstackModifierHelpers.increaseModifierLevel(stack, new ModifierInstance(modifier::getModifier, modifier.getLevel(), modifier.getModifierData()), modifier.getLevel());
            case DECREMENT -> EssenceAPIItemstackModifierHelpers.decreaseModifierLevel(stack, new ModifierInstance(modifier::getModifier, modifier.getLevel(), modifier.getModifierData()), modifier.getLevel());
            case MERGE_TAGS -> EssenceAPIItemstackModifierHelpers.mergeModifierTags(stack, new ModifierInstance(modifier::getModifier, modifier.getLevel(), modifier.getModifierData()));
        }
    }

}
