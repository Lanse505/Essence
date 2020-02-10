package lanse505.essence.utils.module;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import lanse505.essence.core.blocks.essence.*;
import lanse505.essence.core.items.essence.EssenceCrystal;
import lanse505.essence.core.items.essence.EssenceIngotItem;
import lanse505.essence.core.items.essence.EssenceNuggetItem;
import lanse505.essence.core.items.essence.EssenceStickItem;
import lanse505.essence.core.items.tools.EssenceToolItem;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidAttributes;

import static lanse505.essence.utils.EssenceReferences.MODID;

public class ModuleObjects {
    public static EssenceBlock ESSENCE_INFUSED_METAL_BLOCK = new EssenceBlock();
    public static EssenceCrystalOre ESSENCE_CRYSTAL_ORE = new EssenceCrystalOre();
    public static EssenceLeavesBlock ESSENCE_WOOD_LEAVES = new EssenceLeavesBlock();
    public static EssenceLogBlock ESSENCE_WOOD_LOG = new EssenceLogBlock();
    public static EssenceOre ESSENCE_ORE = new EssenceOre(new ResourceLocation(MODID, "essence_ore"));
    public static EssencePlankBlock ESSENCE_WOOD_PLANKS = new EssencePlankBlock();
    public static EssenceSapling ESSENCE_WOOD_SAPLING = new EssenceSapling();
    public static EssenceSlabBlock ESSENCE_WOOD_SLAB = new EssenceSlabBlock();

    public static EssenceCrystal ESSENCE_INFUSED_CRYSTAL = new EssenceCrystal();
    public static EssenceIngotItem ESSENCE_INFUSED_METAL = new EssenceIngotItem();
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_NUGGET = new EssenceNuggetItem();
    public static EssenceStickItem ESSENCE_INFUSED_STICK = new EssenceStickItem();

    public static EssenceToolItem ESSENCE_AXE = new EssenceToolItem(new ResourceLocation(MODID, "essence_axe"), new Item.Properties().addToolType(ToolType.AXE, 2));
    public static EssenceToolItem ESSENCE_PICKAXE = new EssenceToolItem(new ResourceLocation(MODID, "essence_pickaxe"), new Item.Properties().addToolType(ToolType.PICKAXE, 2));
    public static EssenceToolItem ESSENCE_SHOVEL = new EssenceToolItem(new ResourceLocation(MODID, "essence_shovel"), new Item.Properties().addToolType(ToolType.SHOVEL, 2));
    public static EssenceToolItem ESSENCE_OMNITOOL = new EssenceToolItem(new ResourceLocation(MODID, "essence_omnitool"), new Item.Properties().addToolType(ToolType.AXE, 2).addToolType(ToolType.PICKAXE, 2).addToolType(ToolType.SHOVEL, 2));

    public static TitaniumFluidInstance ESSENCE_FLUID = new TitaniumFluidInstance(MODID, "essence", FluidAttributes.builder(new ResourceLocation(MODID, "blocks/fluids/essence_still"), new ResourceLocation(MODID, "blocks/fluids/essence_flowing")), true, EssenceReferences.CORE_TAB);
}
