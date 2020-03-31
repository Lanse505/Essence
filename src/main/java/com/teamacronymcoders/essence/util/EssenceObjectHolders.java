package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.EssenceBlock;
import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.block.EssenceCrystalOreBlock;
import com.teamacronymcoders.essence.block.EssenceOreBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionTableBlock;
import com.teamacronymcoders.essence.block.wood.*;
import com.teamacronymcoders.essence.block.worker.EssenceFurnaceWorkerBlock;
import com.teamacronymcoders.essence.item.essence.EssenceCrystal;
import com.teamacronymcoders.essence.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.item.essence.EssenceStickItem;
import com.teamacronymcoders.essence.item.misc.PortableCrafterItem;
import com.teamacronymcoders.essence.item.misc.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.item.misc.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.tool.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.ObjectHolder;

public class EssenceObjectHolders {
    @ObjectHolder("essence:essence_infused_block")
    public static EssenceBlock ESSENCE_INFUSED_METAL_BLOCK;
    @ObjectHolder("essence:essence_infused_block_empowered")
    public static EssenceBlock ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK;
    @ObjectHolder("essence:essence_infused_block_exalted")
    public static EssenceBlock ESSENCE_INFUSED_METAL_EXALTED_BLOCK;
    @ObjectHolder("essence:essence_infused_block_godly")
    public static EssenceBlock ESSENCE_INFUSED_METAL_GODLY_BLOCK;
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

    @ObjectHolder("essence:essence_bricks_white")
    public static EssenceBrickBlock ESSENCE_BRICKS_WHITE;
    @ObjectHolder("essence:essence_bricks_orange")
    public static EssenceBrickBlock ESSENCE_BRICKS_ORANGE;
    @ObjectHolder("essence:essence_bricks_magenta")
    public static EssenceBrickBlock ESSENCE_BRICKS_MAGENTA;
    @ObjectHolder("essence:essence_bricks_light_blue")
    public static EssenceBrickBlock ESSENCE_BRICKS_LIGHT_BLUE;
    @ObjectHolder("essence:essence_bricks_yellow")
    public static EssenceBrickBlock ESSENCE_BRICKS_YELLOW;
    @ObjectHolder("essence:essence_bricks_lime")
    public static EssenceBrickBlock ESSENCE_BRICKS_LIME;
    @ObjectHolder("essence:essence_bricks_pink")
    public static EssenceBrickBlock ESSENCE_BRICKS_PINK;
    @ObjectHolder("essence:essence_bricks_gray")
    public static EssenceBrickBlock ESSENCE_BRICKS_GRAY;
    @ObjectHolder("essence:essence_bricks_light_gray")
    public static EssenceBrickBlock ESSENCE_BRICKS_LIGHT_GRAY;
    @ObjectHolder("essence:essence_bricks_cyan")
    public static EssenceBrickBlock ESSENCE_BRICKS_CYAN;
    @ObjectHolder("essence:essence_bricks_purple")
    public static EssenceBrickBlock ESSENCE_BRICKS_PURPLE;
    @ObjectHolder("essence:essence_bricks_blue")
    public static EssenceBrickBlock ESSENCE_BRICKS_BLUE;
    @ObjectHolder("essence:essence_bricks_brown")
    public static EssenceBrickBlock ESSENCE_BRICKS_BROWN;
    @ObjectHolder("essence:essence_bricks_green")
    public static EssenceBrickBlock ESSENCE_BRICKS_GREEN;
    @ObjectHolder("essence:essence_bricks_red")
    public static EssenceBrickBlock ESSENCE_BRICKS_RED;
    @ObjectHolder("essence:essence_bricks_black")
    public static EssenceBrickBlock ESSENCE_BRICKS_BLACK;

    @ObjectHolder("essence:furnace_worker")
    public static EssenceFurnaceWorkerBlock FURNACE_WORKER_BLOCK;

    @ObjectHolder("essence:essence_infusion_table")
    public static InfusionTableBlock INFUSION_TABLE;
    @ObjectHolder("essence:essence_infusion_pedestal")
    public static InfusionPedestalBlock INFUSION_PEDESTAL;

    @ObjectHolder("essence:essence_crystal")
    public static EssenceCrystal ESSENCE_INFUSED_CRYSTAL;
    @ObjectHolder("essence:essence_wood_sticks")
    public static EssenceStickItem ESSENCE_INFUSED_STICK;

    @ObjectHolder("essence:essence_ingot")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL;
    @ObjectHolder("essence:essence_nugget")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_NUGGET;

    @ObjectHolder("essence:essence_ingot_empowered")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL_EMPOWERED;
    @ObjectHolder("essence:essence_nugget_empowered")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET;

    @ObjectHolder("essence:essence_ingot_exalted")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL_EXALTED;
    @ObjectHolder("essence:essence_nugget_exalted")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_EXALTED_NUGGET;

    @ObjectHolder("essence:essence_ingot_godly")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL_GODLY;
    @ObjectHolder("essence:essence_nugget_godly")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_GODLY_NUGGET;

    @ObjectHolder("essence:portable_crafter")
    public static PortableCrafterItem PORTABLE_CRAFTER;
    @ObjectHolder("essence:wrench")
    public static EssenceWrench WRENCH;
    @ObjectHolder("essence:tome_of_knowledge")
    public static TomeOfKnowledgeItem tomeOfKnowledge;

    // Tier 1 Tools
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
    @ObjectHolder("essence:essence_bow")
    public static EssenceBow ESSENCE_BOW;

    // Tier 2 Tools
    @ObjectHolder("essence:essence_axe_empowered")
    public static EssenceAxe ESSENCE_AXE_EMPOWERED;
    @ObjectHolder("essence:essence_pickaxe_empowered")
    public static EssencePickaxe ESSENCE_PICKAXE_EMPOWERED;
    @ObjectHolder("essence:essence_shovel_empowered")
    public static EssenceShovel ESSENCE_SHOVEL_EMPOWERED;
    @ObjectHolder("essence:essence_sword_empowered")
    public static EssenceSword ESSENCE_SWORD_EMPOWERED;
    @ObjectHolder("essence:essence_hoe_empowered")
    public static EssenceHoe ESSENCE_HOE_EMPOWERED;
    @ObjectHolder("essence:essence_omnitool_empowered")
    public static EssenceOmniTool ESSENCE_OMNITOOL_EMPOWERED;
    @ObjectHolder("essence:essence_shear_empowered")
    public static EssenceShear ESSENCE_SHEAR_EMPOWERED;
    @ObjectHolder("essence:essence_bow_empowered")
    public static EssenceBow ESSENCE_BOW_EMPOWERED;

    // Tier 3 Tools
    @ObjectHolder("essence:essence_axe_exalted")
    public static EssenceAxe ESSENCE_AXE_EXALTED;
    @ObjectHolder("essence:essence_pickaxe_exalted")
    public static EssencePickaxe ESSENCE_PICKAXE_EXALTED;
    @ObjectHolder("essence:essence_shovel_exalted")
    public static EssenceShovel ESSENCE_SHOVEL_EXALTED;
    @ObjectHolder("essence:essence_sword_exalted")
    public static EssenceSword ESSENCE_SWORD_EXALTED;
    @ObjectHolder("essence:essence_hoe_exalted")
    public static EssenceHoe ESSENCE_HOE_EXALTED;
    @ObjectHolder("essence:essence_omnitool_exalted")
    public static EssenceOmniTool ESSENCE_OMNITOOL_EXALTED;
    @ObjectHolder("essence:essence_shear_exalted")
    public static EssenceShear ESSENCE_SHEAR_EXALTED;
    @ObjectHolder("essence:essence_bow_exalted")
    public static EssenceBow ESSENCE_BOW_EXALTED;

    // Tier 4 Tools
    @ObjectHolder("essence:essence_axe_godly")
    public static EssenceAxe ESSENCE_AXE_GODLY;
    @ObjectHolder("essence:essence_pickaxe_godly")
    public static EssencePickaxe ESSENCE_PICKAXE_GODLY;
    @ObjectHolder("essence:essence_shovel_godly")
    public static EssenceShovel ESSENCE_SHOVEL_GODLY;
    @ObjectHolder("essence:essence_sword_godly")
    public static EssenceSword ESSENCE_SWORD_GODLY;
    @ObjectHolder("essence:essence_hoe_godly")
    public static EssenceHoe ESSENCE_HOE_GODLY;
    @ObjectHolder("essence:essence_omnitool_godly")
    public static EssenceOmniTool ESSENCE_OMNITOOL_GODLY;
    @ObjectHolder("essence:essence_shear_godly")
    public static EssenceShear ESSENCE_SHEAR_GODLY;
    @ObjectHolder("essence:essence_bow_godly")
    public static EssenceBow ESSENCE_BOW_GODLY;

    public static TitaniumFluidInstance ESSENCE_FLUID = new TitaniumFluidInstance(Essence.MODID, "essence", FluidAttributes.builder(new ResourceLocation(Essence.MODID, "blocks/fluids/essence_still"), new ResourceLocation(Essence.MODID, "blocks/fluids/essence_flowing")), true, Essence.CORE_TAB);
}
