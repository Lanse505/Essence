package lanse505.essence.impl.items.tools;

import com.google.common.collect.Multimap;
import lanse505.essence.api.modifier.CoreModifier;
import lanse505.essence.utils.EssenceHelpers;
import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssencePickaxe extends PickaxeItem {
    public EssencePickaxe(ResourceLocation resourceLocation) {
        super(EssenceItemTiers.ESSENCE, 1, -2.8f, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
                .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedDurability(stack, modifierPair.getRight(), ESSENCE.getMaxUses())).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
                .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedEfficiency(stack, modifierPair.getRight(), super.getDestroySpeed(stack, state))).reduce(0f, Float::sum);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
                .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedHarvestLevel(stack, modifierPair.getRight(), harvestLevel)).reduce(0, Integer::sum);
    }

    //TODO: Investigate how stable this will be, AttributeModifiers apparently can break quite easily if they bottom-out from negative modifiers.
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
        EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
                .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getAttributeModifiers(stack, null, modifierPair.getRight()).entries().stream().map(entry -> multimap.put(entry.getKey(), entry.getValue())));
        return multimap;
    }
}