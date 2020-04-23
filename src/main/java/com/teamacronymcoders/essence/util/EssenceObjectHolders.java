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
import com.teamacronymcoders.essence.entity.impl.GlueBallEntity;
import com.teamacronymcoders.essence.entity.impl.sheared.*;
import com.teamacronymcoders.essence.item.essence.EssenceCrystal;
import com.teamacronymcoders.essence.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.item.essence.EssenceStickItem;
import com.teamacronymcoders.essence.item.misc.CustomSpawnEggItem;
import com.teamacronymcoders.essence.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.item.tool.*;
import com.teamacronymcoders.essence.item.tool.misc.PortableCrafterItem;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.SerializedEntityItem;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class EssenceObjectHolders {
    @ObjectHolder("essence:essence_infused_block")
    public static EssenceBlock ESSENCE_INFUSED_METAL_BLOCK;
    @ObjectHolder("essence:essence_infused_block_empowered")
    public static EssenceBlock ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK;
    @ObjectHolder("essence:essence_infused_block_supreme")
    public static EssenceBlock ESSENCE_INFUSED_METAL_SUPREME_BLOCK;
    @ObjectHolder("essence:essence_infused_block_divine")
    public static EssenceBlock ESSENCE_INFUSED_METAL_DIVINE_BLOCK;

    public static final List<Supplier<EssenceBlock>> ESSENCE_BLOCKS = Arrays.asList(
        () -> ESSENCE_INFUSED_METAL_BLOCK,
        () -> ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK,
        () -> ESSENCE_INFUSED_METAL_SUPREME_BLOCK,
        () -> ESSENCE_INFUSED_METAL_DIVINE_BLOCK
    );

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

    public static final List<Supplier<EssenceBrickBlock>> BRICK_BLOCK_LIST = Arrays.asList(
        () -> ESSENCE_BRICKS_WHITE,
        () -> ESSENCE_BRICKS_ORANGE,
        () -> ESSENCE_BRICKS_MAGENTA,
        () -> ESSENCE_BRICKS_LIGHT_BLUE,
        () -> ESSENCE_BRICKS_YELLOW,
        () -> ESSENCE_BRICKS_LIME,
        () -> ESSENCE_BRICKS_PINK,
        () -> ESSENCE_BRICKS_GRAY,
        () -> ESSENCE_BRICKS_LIGHT_GRAY,
        () -> ESSENCE_BRICKS_CYAN,
        () -> ESSENCE_BRICKS_PURPLE,
        () -> ESSENCE_BRICKS_BLUE,
        () -> ESSENCE_BRICKS_BROWN,
        () -> ESSENCE_BRICKS_GREEN,
        () -> ESSENCE_BRICKS_RED,
        () -> ESSENCE_BRICKS_BLACK
    );

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

    @ObjectHolder("essence:essence_ingot_supreme")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL_SUPREME;
    @ObjectHolder("essence:essence_nugget_supreme")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_SUPREME_NUGGET;

    @ObjectHolder("essence:essence_ingot_divine")
    public static EssenceIngotItem ESSENCE_INFUSED_METAL_DIVINE;
    @ObjectHolder("essence:essence_nugget_divine")
    public static EssenceNuggetItem ESSENCE_INFUSED_METAL_DIVINE_NUGGET;

    @ObjectHolder("essence:portable_crafter")
    public static PortableCrafterItem PORTABLE_CRAFTER;
    @ObjectHolder("essence:wrench")
    public static EssenceWrench WRENCH;
    @ObjectHolder("essence:serialized_entity")
    public static SerializedEntityItem ENTITY_ITEM;
    @ObjectHolder("essence:tome_of_knowledge")
    public static TomeOfKnowledgeItem TOME_OF_KNOWLEDGE;
    @ObjectHolder("essence:tome_of_experience")
    public static TomeOfExperienceItem TOME_OF_EXPERIENCE;
    @ObjectHolder("essence:glue_ball")
    public static GlueBallItem GLUE_BALL;
    @ObjectHolder("essence:glue_ball")
    public static EntityType<GlueBallEntity> GLUE_BALL_ENTITY;

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
    @ObjectHolder("essence:essence_axe_supreme")
    public static EssenceAxe ESSENCE_AXE_SUPREME;
    @ObjectHolder("essence:essence_pickaxe_supreme")
    public static EssencePickaxe ESSENCE_PICKAXE_SUPREME;
    @ObjectHolder("essence:essence_shovel_supreme")
    public static EssenceShovel ESSENCE_SHOVEL_SUPREME;
    @ObjectHolder("essence:essence_sword_supreme")
    public static EssenceSword ESSENCE_SWORD_SUPREME;
    @ObjectHolder("essence:essence_hoe_supreme")
    public static EssenceHoe ESSENCE_HOE_SUPREME;
    @ObjectHolder("essence:essence_omnitool_supreme")
    public static EssenceOmniTool ESSENCE_OMNITOOL_SUPREME;
    @ObjectHolder("essence:essence_shear_supreme")
    public static EssenceShear ESSENCE_SHEAR_SUPREME;
    @ObjectHolder("essence:essence_bow_supreme")
    public static EssenceBow ESSENCE_BOW_SUPREME;

    // Tier 4 Tools
    @ObjectHolder("essence:essence_axe_divine")
    public static EssenceAxe ESSENCE_AXE_DIVINE;
    @ObjectHolder("essence:essence_pickaxe_divine")
    public static EssencePickaxe ESSENCE_PICKAXE_DIVINE;
    @ObjectHolder("essence:essence_shovel_divine")
    public static EssenceShovel ESSENCE_SHOVEL_DIVINE;
    @ObjectHolder("essence:essence_sword_divine")
    public static EssenceSword ESSENCE_SWORD_DIVINE;
    @ObjectHolder("essence:essence_hoe_divine")
    public static EssenceHoe ESSENCE_HOE_DIVINE;
    @ObjectHolder("essence:essence_omnitool_divine")
    public static EssenceOmniTool ESSENCE_OMNITOOL_DIVINE;
    @ObjectHolder("essence:essence_shear_divine")
    public static EssenceShear ESSENCE_SHEAR_DIVINE;
    @ObjectHolder("essence:essence_bow_divine")
    public static EssenceBow ESSENCE_BOW_DIVINE;

    public static TitaniumFluidInstance ESSENCE_FLUID = new TitaniumFluidInstance(Essence.MODID, "essence", FluidAttributes.builder(new ResourceLocation(Essence.MODID, "blocks/fluids/essence_still"), new ResourceLocation(Essence.MODID, "blocks/fluids/essence_flowing")), true, Essence.CORE_TAB);
    public static TitaniumFluidInstance EXP_FLUID = new TitaniumFluidInstance(Essence.MODID, "experience", FluidAttributes.builder(new ResourceLocation(Essence.MODID, "blocks/fluids/experience_still"), new ResourceLocation(Essence.MODID, "blocks/fluids/experience_flowing")), true, Essence.CORE_TAB);

    @ObjectHolder("essence:sheared_chicken")
    public static EntityType<ShearedChickenEntity> SHEARED_CHICKEN;
    @ObjectHolder("essence:sheared_cow")
    public static EntityType<ShearedCowEntity> SHEARED_COW;
    @ObjectHolder("essence:sheared_creeper")
    public static EntityType<ShearedCreeperEntity> SHEARED_CREEPER;
    @ObjectHolder("essence:sheared_ghast")
    public static EntityType<ShearedGhastEntity> SHEARED_GHAST;
    @ObjectHolder("essence:sheared_pig")
    public static EntityType<ShearedPigEntity> SHEARED_PIG;

    @ObjectHolder("essence:sheared_chicken_egg")
    public static CustomSpawnEggItem SHEARED_CHICKEN_EGG;
    @ObjectHolder("essence:sheared_cow_egg")
    public static CustomSpawnEggItem SHEARED_COW_EGG;
    @ObjectHolder("essence:sheared_creeper_egg")
    public static CustomSpawnEggItem SHEARED_CREEPER_EGG;
    @ObjectHolder("essence:sheared_ghast_egg")
    public static CustomSpawnEggItem SHEARED_GHAST_EGG;
    @ObjectHolder("essence:sheared_pig_egg")
    public static CustomSpawnEggItem SHEARED_PIG_EGG;

}
