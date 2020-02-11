package lanse505.essence.utils.module;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import lanse505.essence.core.blocks.essence.*;
import lanse505.essence.core.items.essence.EssenceCrystal;
import lanse505.essence.core.items.essence.EssenceIngotItem;
import lanse505.essence.core.items.essence.EssenceNuggetItem;
import lanse505.essence.core.items.essence.EssenceStickItem;
import lanse505.essence.core.items.tools.*;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.ObjectHolder;

import static lanse505.essence.utils.EssenceReferences.MODID;

public class ModuleObjects {
    @ObjectHolder("essence:essence_infused_block")
    public static EssenceBlock ESSENCE_INFUSED_METAL_BLOCK;
    @ObjectHolder("essence:essence_crystal_ore")
    public static EssenceCrystalOre ESSENCE_CRYSTAL_ORE;
    @ObjectHolder("essence:essence_wood_leaves")
    public static EssenceLeavesBlock ESSENCE_WOOD_LEAVES;
    @ObjectHolder("essence:essence_wood_log")
    public static EssenceLogBlock ESSENCE_WOOD_LOG;
    @ObjectHolder("essence:essence_ore")
    public static EssenceOre ESSENCE_ORE;
    @ObjectHolder("essence:essence_wood_planks")
    public static EssencePlankBlock ESSENCE_WOOD_PLANKS;
    @ObjectHolder("essence:essence_wood_sapling")
    public static EssenceSapling ESSENCE_WOOD_SAPLING;
    @ObjectHolder("essence:essence_wood_slab")
    public static EssenceSlabBlock ESSENCE_WOOD_SLAB;

    @ObjectHolder("essence:essence_crystal")
    public static EssenceCrystal ESSENCE_INFUSED_CRYSTAL;
    @ObjectHolder("essence:essence_ingot")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL;
    @ObjectHolder("essence:essence_nugget")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_NUGGET;
    @ObjectHolder("essence:essence_wood_sticks")
    public static EssenceStickItem ESSENCE_INFUSED_STICK;

    @ObjectHolder("essence:essence_axe")
    public static EssenceAxe ESSENCE_AXE;
    @ObjectHolder("essence:essence_pickaxe")
    public static EssencePickaxe ESSENCE_PICKAXE;
    @ObjectHolder("essence:essence_shovel")
    public static EssenceShovel ESSENCE_SHOVEL;
    @ObjectHolder("essence:essence_sword")
    public static EssenceSword ESSENCE_SWORD;
    @ObjectHolder("essence:essence_hoe")
    public static EssenceHoe ESSENCE_HOE;
    @ObjectHolder("essence:essence_omnitool")
    public static EssenceOmniTool ESSENCE_OMNITOOL;

    public static TitaniumFluidInstance ESSENCE_FLUID = new TitaniumFluidInstance(MODID, "essence", FluidAttributes.builder(new ResourceLocation(MODID, "blocks/fluids/essence_still"), new ResourceLocation(MODID, "blocks/fluids/essence_flowing")), true, EssenceReferences.CORE_TAB);
}
