package com.teamacronymcoders.essence.impl.serializable.loot_modifier;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FieryLootModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected FieryLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        List<ItemStack> list = new ArrayList<>();
        generatedLoot.forEach(stack -> list.add(smelt(stack, context)));
        return list;
    }

    private static ItemStack smelt(ItemStack stack, LootContext context) {
        return context.getWorld().getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld())
            .map(AbstractCookingRecipe::getRecipeOutput)
            .filter(itemStack -> !itemStack.isEmpty())
            .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
            .orElse(stack);
    }

    public static class Serializer extends GlobalLootModifierSerializer<FieryLootModifier> {
        @Override
        public FieryLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            return new FieryLootModifier(ailootcondition);
        }
    }
}
