package com.teamacronymcoders.essence.serializable.loot;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameter;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.NoteBlockEvent.Play;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FieryLootModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected FieryLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    private static ItemStack smelt(ItemStack stack, LootContext context) {
        if (stack.isEmpty()) {
            return stack;
        }

        World world = context.getWorld();
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld())
            .map(recipe -> {
                if (recipe.getRecipeOutput().isEmpty()) {
                    return stack;
                }
                if (entity instanceof PlayerEntity) {
                    ((PlayerEntity) entity).giveExperiencePoints(Math.round(recipe.getExperience()));
                }
                return recipe.getRecipeOutput();
            })
            .filter(itemStack -> !itemStack.isEmpty())
            .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
            .orElse(stack);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot.stream().map(stack -> smelt(stack, context)).collect(Collectors.toList());
    }

    public static class Serializer extends GlobalLootModifierSerializer<FieryLootModifier> {
        @Override
        public FieryLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            return new FieryLootModifier(ailootcondition);
        }
    }
}
