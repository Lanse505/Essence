package lanse505.essence.core.items.tools;

import com.google.common.collect.Sets;
import lanse505.essence.utils.EssenceReferences;
import lanse505.essence.utils.module.ModuleObjects;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

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
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return tools.stream().anyMatch(tool -> tool.canHarvestBlock(state)) ? super.getDestroySpeed(stack, state) : this.efficiency;
    }

    @Override
    public boolean canHarvestBlock(BlockState state) {
        int harvestLevel = this.getTier().getHarvestLevel();
        if (state.getHarvestTool() == ToolType.AXE || state.getHarvestTool() == ToolType.PICKAXE || state.getHarvestTool() == ToolType.SHOVEL) {
            return harvestLevel >= state.getHarvestLevel();
        }
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
}
