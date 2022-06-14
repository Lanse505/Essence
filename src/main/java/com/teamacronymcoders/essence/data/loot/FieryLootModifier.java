package com.teamacronymcoders.essence.data.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class FieryLootModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is com.teamacronymcoders.essenceapi.modified.
     */
    protected FieryLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    private static ItemStack smelt(ItemStack stack, LootContext context) {
        if (stack.isEmpty()) {
            return stack;
        }

        Level level = context.getLevel();
        Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player) {
            return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel())
                    .map(recipe -> {
                        if (recipe.getResultItem().isEmpty()) {
                            return stack;
                        }
                        player.giveExperiencePoints(Math.round(recipe.getExperience()));
                        return recipe.getResultItem();
                    })
                    .filter(itemStack -> !itemStack.isEmpty())
                    .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                    .orElse(stack);
        }
        return stack;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot.stream().map(stack -> smelt(stack, context)).collect(Collectors.toList());
    }

    public static class Serializer extends GlobalLootModifierSerializer<FieryLootModifier> {
        @Override
        public FieryLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new FieryLootModifier(ailootcondition);
        }

        @Override
        public JsonObject write(FieryLootModifier instance) {
            return new JsonObject();
        }
    }
}
