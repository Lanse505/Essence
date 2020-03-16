package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.items.base.EssenceToolItem;
import com.teamacronymcoders.essence.serializable.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

import java.util.Optional;

public class EssenceHoe extends EssenceToolItem {

    public EssenceHoe(EssenceToolTiers tier) {
        super(0, tier.getAttackSpeedHoeMod(), tier, Sets.newHashSet(), new Item.Properties());
    }

    public ActionResultType onItemBehaviour(ItemUseContext context) {
        World world = context.getWorld();
        Block block = world.getBlockState(context.getPos()).getBlock();
        return world.getRecipeManager().getRecipes().stream()
            .filter(iRecipe -> iRecipe.getType() == HoeTillingRecipe.SERIALIZER.getRecipeType())
            .map(iRecipe -> (HoeTillingRecipe) iRecipe)
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
