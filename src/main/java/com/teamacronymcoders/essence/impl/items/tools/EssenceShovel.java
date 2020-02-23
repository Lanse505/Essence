package com.teamacronymcoders.essence.impl.items.tools;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import com.teamacronymcoders.essence.utils.EssenceRegistration;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.teamacronymcoders.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceShovel extends ShovelItem implements IModifiedTool {
    public EssenceShovel(ResourceLocation resourceLocation) {
        super(ESSENCE, 1.5f, -3.0f, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EssenceModifierHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
            .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
            .map(modifierPair -> modifierPair.getLeft().getModifiedDurability(stack, modifierPair.getRight(), ESSENCE.getMaxUses())).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return EssenceModifierHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
            .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
            .map(modifierPair -> modifierPair.getLeft().getModifiedEfficiency(stack, modifierPair.getRight(), super.getDestroySpeed(stack, state))).reduce(0f, Float::sum);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceModifierHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
            .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
            .map(modifierPair -> modifierPair.getLeft().getModifiedHarvestLevel(stack, modifierPair.getRight(), harvestLevel)).reduce(0, Integer::sum);
    }

    //TODO: Investigate how stable this will be, AttributeModifiers apparently can break quite easily if they bottom-out from negative modifiers.
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
        EssenceModifierHelpers.getModifiers(stack).entrySet()
            .stream()
            .map(entry -> entry.getKey().getAttributeModifiers(stack, null, entry.getValue()))
            .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> multimap.put(entry.getKey(), entry.getValue())));
        return multimap;
    }

    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        if (isRecursive) {
            return super.onItemUse(context);
        }
        return onItemUse(context);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType superResult = super.onItemUse(context);
        Optional<ActionResultType> modifierResult = EssenceModifierHelpers.getModifiers(context.getItem())
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .map(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onItemUse(context, modifierEntry.getValue()))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst();
        return superResult == ActionResultType.SUCCESS ? superResult : modifierResult.orElse(superResult);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity entity, LivingEntity player) {
        EssenceModifierHelpers.getModifiers(stack)
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .forEach(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onHitEntity(stack, entity, player, modifierEntry.getValue()));
        return super.hitEntity(stack, entity, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        EssenceModifierHelpers.getModifiers(stack)
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .forEach(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onBlockDestroyed(stack, world, state, pos, miner, modifierEntry.getValue()));
        return super.onBlockDestroyed(stack, world, state, pos, miner);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack);
        EssenceModifierHelpers.getModifiers(stack)
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .forEach(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, modifierEntry.getValue()));
        super.inventoryTick(stack, world, entity, inventorySlot, isCurrentItem);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return EssenceModifierHelpers.getModifiers(stack).containsKey(EssenceRegistration.ENCHANTED_MODIFIER.get());
    }
}
