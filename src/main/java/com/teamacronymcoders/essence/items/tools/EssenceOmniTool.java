package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.items.base.EssenceToolItem;
import com.teamacronymcoders.essence.serializable.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.serializable.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.*;

public class EssenceOmniTool extends EssenceToolItem {

    private static final Set<Block> EFFECTIVE_ON = mergeSet(EssenceAxe.EFFECTIVE_ON, EssencePickaxe.EFFECTIVE_ON, EssenceShovel.EFFECTIVE_ON);
    private static final List<Item> tools = new ArrayList<>(Arrays.asList(EssenceObjectHolders.ESSENCE_AXE, EssenceObjectHolders.ESSENCE_PICKAXE, EssenceObjectHolders.ESSENCE_SHOVEL));

    public static Set<Block> mergeSet(Set<Block> a, Set<Block> b, Set<Block> c) {
        return new HashSet<Block>() {{
                addAll(a);
                addAll(b);
                addAll(c);
            }
        };
    }

    public EssenceOmniTool(EssenceToolTiers tier) {
        super(tier.getAttackDamage(), tier.getEfficiency(), tier, EFFECTIVE_ON, new Item.Properties().addToolType(ToolType.AXE, tier.getHarvestLevel()).addToolType(ToolType.PICKAXE, tier.getHarvestLevel()).addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        if (state.getHarvestTool() == ToolType.AXE || state.getHarvestTool() == ToolType.PICKAXE || state.getHarvestTool() == ToolType.SHOVEL) {
            return getTier().getHarvestLevel() >= state.getHarvestLevel();
        }
        return super.canHarvestBlock(state);
    }

    public ActionResultType onItemBehaviour(ItemUseContext context) {
        World world = context.getWorld();
        Block block = world.getBlockState(context.getPos()).getBlock();
        ActionResultType result = world.getRecipeManager().getRecipes().stream()
            .filter(iRecipe -> iRecipe.getType() == AxeStrippingRecipe.SERIALIZER.getRecipeType())
            .map(iRecipe -> (AxeStrippingRecipe) iRecipe)
            .filter(recipe -> recipe.matches(block))
            .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
        if (result == ActionResultType.PASS) {
            result = world.getRecipeManager().getRecipes().stream()
                .filter(iRecipe -> iRecipe.getType() == ShovelPathingRecipe.SERIALIZER.getRecipeType())
                .map(iRecipe -> (ShovelPathingRecipe) iRecipe)
                .filter(recipe -> recipe.matches(block))
                .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
        }
        return result;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType resultType = onItemBehaviour(context);
        Optional<ActionResultType> modifierResult = EssenceModifierHelpers.getModifiers(context.getItem()).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .map(instance -> ((InteractionCoreModifier) instance.getModifier()).onItemUse(context, instance))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst();
        return resultType == ActionResultType.SUCCESS ? resultType : modifierResult.orElse(resultType);
    }

    @Override
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        if (isRecursive) {
            onItemBehaviour(context);
        }
        return onItemUse(context);
    }

}
