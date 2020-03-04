package com.teamacronymcoders.essence.utils;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.blocks.EssenceBlock;
import com.teamacronymcoders.essence.blocks.EssencePlankBlock;
import com.teamacronymcoders.essence.blocks.EssenceSlabBlock;
import com.teamacronymcoders.essence.blocks.EssenceCrystalOreBlock;
import com.teamacronymcoders.essence.blocks.EssenceOreBlock;
import com.teamacronymcoders.essence.blocks.EssenceLeavesBlock;
import com.teamacronymcoders.essence.blocks.EssenceLogBlock;
import com.teamacronymcoders.essence.blocks.EssenceSaplingBlock;
import com.teamacronymcoders.essence.blocks.InfusionPedestalBlock;
import com.teamacronymcoders.essence.blocks.InfusionTableBlock;
import com.teamacronymcoders.essence.items.essence.EssenceCrystal;
import com.teamacronymcoders.essence.items.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.items.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.items.essence.EssenceStickItem;
import com.teamacronymcoders.essence.items.tools.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.ObjectHolder;

public class EssenceObjectHolders {
    @ObjectHolder("essence:essence_infused_block")
    public static EssenceBlock ESSENCE_INFUSED_METAL_BLOCK;
    @ObjectHolder("essence:essence_crystal_ore")
    public static EssenceCrystalOreBlock ESSENCE_CRYSTAL_ORE;
    @ObjectHolder("essence:essence_wood_leaves")
    public static EssenceLeavesBlock ESSENCE_WOOD_LEAVES;
    @ObjectHolder("essence:essence_wood_log")
    public static EssenceLogBlock ESSENCE_WOOD_LOG;
    @ObjectHolder("essence:essence_ore")
    public static EssenceOreBlock ESSENCE_ORE;
    @ObjectHolder("essence:essence_wood_planks")
    public static EssencePlankBlock ESSENCE_WOOD_PLANKS;
    @ObjectHolder("essence:essence_wood_sapling")
    public static EssenceSaplingBlock ESSENCE_WOOD_SAPLING;
    @ObjectHolder("essence:essence_wood_slab")
    public static EssenceSlabBlock ESSENCE_WOOD_SLAB;

    @ObjectHolder("essence:essence_infusion_table")
    public static InfusionTableBlock ESSENCE_INFUSION_TABLE;
    @ObjectHolder("essence:essence_infusion_pedestal")
    public static InfusionPedestalBlock ESSENCE_INFUSION_PEDESTAL;

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
    @ObjectHolder("essence:essence_shear")
    public static EssenceShear ESSENCE_SHEAR;

    public static TitaniumFluidInstance ESSENCE_FLUID = new TitaniumFluidInstance(Essence.MODID, "essence", FluidAttributes.builder(new ResourceLocation(Essence.MODID, "blocks/fluids/essence_still"), new ResourceLocation(Essence.MODID, "blocks/fluids/essence_flowing")), true, Essence.CORE_TAB);
}
