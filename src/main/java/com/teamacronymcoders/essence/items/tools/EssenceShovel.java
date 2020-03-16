package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.items.base.EssenceToolItem;
import com.teamacronymcoders.essence.serializable.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Optional;
import java.util.Set;

public class EssenceShovel extends EssenceToolItem {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);

    public EssenceShovel(EssenceToolTiers tier) {
        super(tier.getAttackDamageShovelMod(), tier.getAttackSpeedShovelMod(), tier, EFFECTIVE_ON, new Item.Properties().addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        if (state.getHarvestTool() == ToolType.SHOVEL) {
            return getTier().getHarvestLevel() >= state.getHarvestLevel();
        }
        return super.canHarvestBlock(state);
    }

    public ActionResultType onItemBehaviour(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(context.getPos());
        if (context.getFace() == Direction.DOWN) return ActionResultType.PASS;
        if (state.getBlock() instanceof CampfireBlock && state.get(CampfireBlock.LIT)) {
            PlayerEntity playerentity = context.getPlayer();
            world.playEvent(null, 1009, pos, 0);
            BlockState newState = state.with(CampfireBlock.LIT, Boolean.FALSE);
            if (!world.isRemote) {
                world.setBlockState(pos, newState, 11);
                if (playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (p_220041_1_) -> {
                        p_220041_1_.sendBreakAnimation(context.getHand());
                    });
                }
            }
            return ActionResultType.SUCCESS;
        } else {
            return world.getRecipeManager().getRecipes().stream()
                .filter(iRecipe -> iRecipe.getType() == ShovelPathingRecipe.SERIALIZER.getRecipeType())
                .map(iRecipe -> (ShovelPathingRecipe) iRecipe)
                .filter(recipe -> recipe.matches(state.getBlock()))
                .findFirst().map(recipe -> recipe.resolveRecipe(context)).orElse(ActionResultType.PASS);
        }
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
