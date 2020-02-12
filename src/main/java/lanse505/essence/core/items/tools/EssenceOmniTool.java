package lanse505.essence.core.items.tools;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import lanse505.essence.api.modifier.ToolCoreModifier;
import lanse505.essence.utils.EssenceHelpers;
import lanse505.essence.utils.EssenceReferences;
import lanse505.essence.utils.module.ModuleObjects;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceOmniTool extends ToolItem {
    private static final List<Item> tools = new ArrayList<>(Arrays.asList(ModuleObjects.ESSENCE_AXE, ModuleObjects.ESSENCE_PICKAXE, ModuleObjects.ESSENCE_SHOVEL));

    public EssenceOmniTool(ResourceLocation resourceLocation) {
        super(ESSENCE.getAttackDamage(), ESSENCE.getEfficiency(), ESSENCE, Sets.newHashSet(), new Item.Properties().group(EssenceReferences.TOOL_TAB).addToolType(ToolType.AXE, ESSENCE.getHarvestLevel()).addToolType(ToolType.PICKAXE, ESSENCE.getHarvestLevel()).addToolType(ToolType.SHOVEL, ESSENCE.getHarvestLevel()));
        setRegistryName(resourceLocation);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedDurability(stack, modifierPair.getRight(), ESSENCE.getMaxUses())).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        boolean breakable = tools.stream().anyMatch(tool -> tool.canHarvestBlock(state));
        return breakable ? EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedEfficiency(stack, modifierPair.getRight(), super.getDestroySpeed(stack, state))).reduce(0f, Float::sum) : this.efficiency;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> modifierPair.getLeft().getModifiedHarvestLevel(stack, modifierPair.getRight(), harvestLevel)).reduce(0, Integer::sum);
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        return tools.stream().anyMatch(tool -> tool.canHarvestBlock(state));
    }

    @Override
    @MethodsReturnNonnullByDefault
    public ActionResultType onItemUse(ItemUseContext context) {
        if (tools.stream().anyMatch(tool -> onItemUse(context) == ActionResultType.SUCCESS)) {
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    //TODO: Investigate how stable this will be, AttributeModifiers apparently can break quite easily if they bottom-out from negative modifiers.
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
        EssenceHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof ToolCoreModifier)
                .map(modifierEntry -> Pair.of(((ToolCoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
                .map(modifierPair -> multimap.put("tool_modifier_" + modifierPair.getLeft().getRegistryName(), new AttributeModifier(modifierPair.getLeft().getRegistryName().toString(), modifierPair.getLeft().getModifiedAttackDamage(stack, modifierPair.getRight(), ESSENCE.getAttackDamage()), AttributeModifier.Operation.ADDITION)));
        return multimap;
    }
}
