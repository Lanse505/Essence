package lanse505.essence.core.items.tools;

import com.google.common.collect.Multimap;
import lanse505.essence.api.modifier.ToolStatCoreModifier;
import lanse505.essence.utils.EssenceHelpers;
import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceHoe extends HoeItem {

    public EssenceHoe(ResourceLocation resourceLocation) {
        super(ESSENCE, -1.0F, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolStatCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolStatCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedDurability(stack, modifierPair.getRight(), ESSENCE.getMaxUses())).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolStatCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolStatCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedEfficiency(stack, modifierPair.getRight(), super.getDestroySpeed(stack, state))).reduce(0f, Float::sum);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolStatCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolStatCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedHarvestLevel(stack, modifierPair.getRight(), harvestLevel)).reduce(0, Integer::sum);
    }

    //TODO: Investigate how stable this will be, AttributeModifiers apparently can break quite easily if they bottom-out from negative modifiers.
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
        EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolStatCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolStatCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> multimap.put("tool_modifier_" + modifierPair.getLeft().getRegistryName(), new AttributeModifier(modifierPair.getLeft().getRegistryName().toString(), modifierPair.getLeft().getModifiedAttackDamage(stack, modifierPair.getRight(), ESSENCE.getAttackDamage()), AttributeModifier.Operation.ADDITION)));
        return multimap;
    }
}
