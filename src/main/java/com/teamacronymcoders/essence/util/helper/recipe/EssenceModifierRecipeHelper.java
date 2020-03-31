package com.teamacronymcoders.essence.util.helper.recipe;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.item.ItemStack;

public class EssenceModifierRecipeHelper {

    public static ItemStack resolveRecipe(ItemStack stack, SerializableModifier[] modifiers) {
        for (SerializableModifier modifier : modifiers) {
            resolveOperationBehaviour(stack, modifier);
        }
        return stack;
    }

    public static void resolveOperationBehaviour(ItemStack stack, SerializableModifier modifier) {
        switch (modifier.getOperation()) {
            case ADD:
                EssenceItemstackModifierHelpers.addModifier(stack, modifier.getModifier(), modifier.getLevel(), modifier.getModifierData());
            case REMOVE:
                EssenceItemstackModifierHelpers.removeModifiers(stack, modifier.getModifier());
            case INCREMENT:
                EssenceItemstackModifierHelpers.increaseModifierLevel(stack, new ModifierInstance<ItemStack>(ItemStack.class, modifier::getModifier, modifier.getLevel(), modifier.getModifierData()), modifier.getLevel());
            case DECREMENT:
                EssenceItemstackModifierHelpers.decreaseModifierLevel(stack, new ModifierInstance<ItemStack>(ItemStack.class, modifier::getModifier, modifier.getLevel(), modifier.getModifierData()), modifier.getLevel());
            case MERGE_TAGS:
                EssenceItemstackModifierHelpers.mergeModifierTags(stack, new ModifierInstance<ItemStack>(ItemStack.class, modifier::getModifier, modifier.getLevel(), modifier.getModifierData()));
        }
    }

}
