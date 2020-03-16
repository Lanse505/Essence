package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.items.base.EssenceToolItem;
import com.teamacronymcoders.essence.serializable.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Optional;
import java.util.Set;

public class EssenceAxe extends EssenceToolItem {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE);

    public EssenceAxe(EssenceToolTiers tier) {
        super(tier.getAttackDamageAxeMod(), tier.getAttackSpeedAxeMod(), tier, EFFECTIVE_ON, new Item.Properties().addToolType(ToolType.AXE, tier.getHarvestLevel()));
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        if (state.getHarvestTool() == ToolType.AXE) {
            return getTier().getHarvestLevel() >= state.getHarvestLevel();
        }
        return super.canHarvestBlock(state);
    }

    public ActionResultType onItemBehaviour(ItemUseContext context) {
        World world = context.getWorld();
        Block block = world.getBlockState(context.getPos()).getBlock();
        return world.getRecipeManager().getRecipes().stream()
            .filter(iRecipe -> iRecipe.getType() == AxeStrippingRecipe.SERIALIZER.getRecipeType())
            .map(iRecipe -> (AxeStrippingRecipe) iRecipe)
            .filter(recipe -> recipe.matches(block))
            .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
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
