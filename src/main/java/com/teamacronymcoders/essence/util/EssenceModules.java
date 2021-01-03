package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.EssenceBlock;
import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.block.EssenceCrystalOreBlock;
import com.teamacronymcoders.essence.block.EssenceOreBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionTableBlock;
import com.teamacronymcoders.essence.block.wood.*;
import com.teamacronymcoders.essence.item.essence.EssenceCrystal;
import com.teamacronymcoders.essence.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.item.essence.EssenceStickItem;
import com.teamacronymcoders.essence.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.item.tool.*;
import com.teamacronymcoders.essence.item.tool.misc.PortableCrafterItem;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.SerializedEntityItem;
import com.teamacronymcoders.essence.util.registration.*;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

public class EssenceModules {
    public static final Module.Builder CORE = Module.builder("core")
        .force()
        .description("Core-Content")
        .feature(
            Feature.builder("items")
                .force()
                .description("Core-Items")
                .content(Item.class, new EssenceCrystal().setRegistryName(new ResourceLocation(Essence.MODID, "essence_crystal")))
                .content(Item.class, new EssenceIngotItem(EssenceItemTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_ingot")))
                .content(Item.class, new EssenceIngotItem(EssenceItemTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_ingot_empowered")))
                .content(Item.class, new EssenceIngotItem(EssenceItemTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_ingot_supreme")))
                .content(Item.class, new EssenceIngotItem(EssenceItemTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_ingot_divine")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget_empowered")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget_supreme")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget_divine")))
                .content(Item.class, new EssenceStickItem().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_sticks")))
                .content(Item.class, new TomeOfKnowledgeItem().setRegistryName(new ResourceLocation(Essence.MODID, "tome_of_knowledge")))
                .content(Item.class, new TomeOfExperienceItem().setRegistryName(new ResourceLocation(Essence.MODID, "tome_of_experience")))
        )
        .feature(
            Feature.builder("blocks")
                .force()
                .description("Core-Blocks")
                .content(Block.class, new EssenceBlock(EssenceItemTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block")))
                .content(Block.class, new EssenceBlock(EssenceItemTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block_empowered")))
                .content(Block.class, new EssenceBlock(EssenceItemTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block_supreme")))
                .content(Block.class, new EssenceBlock(EssenceItemTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block_divine")))
                .content(Block.class, new EssenceCrystalOreBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_crystal_ore")))
                .content(Block.class, new EssenceLeavesBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_leaves")))
                .content(Block.class, new EssenceLogBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_log")))
                .content(Block.class, new EssenceOreBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_ore")))
                .content(Block.class, new EssencePlankBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_planks")))
                .content(Block.class, new EssenceSaplingBlock().setRegistryName(Essence.MODID, "essence_wood_sapling"))
                .content(Block.class, new EssenceSlabBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_slab")))
                .content(Block.class, new EssenceBrickBlock(DyeColor.WHITE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.WHITE.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.ORANGE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.ORANGE.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.MAGENTA).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.MAGENTA.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.LIGHT_BLUE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.LIGHT_BLUE.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.YELLOW).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.YELLOW.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.LIME).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.LIME.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.PINK).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.PINK.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.GRAY).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.GRAY.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.LIGHT_GRAY).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.LIGHT_GRAY.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.CYAN).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.CYAN.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.PURPLE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.PURPLE.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.BLUE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.BLUE.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.BROWN).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.BROWN.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.GREEN).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.GREEN.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.RED).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.RED.getString())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.BLACK).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.BLACK.getString())))
        )
        .feature(
            Feature.builder("infusion")
                .force()
                .description("Infusion-Blocks")
                .content(Block.class, new InfusionPedestalBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_infusion_pedestal")))
                .content(Block.class, new InfusionTableBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_infusion_table")))
        )
        .feature(
            Feature.builder("misc")
                .force()
                .description("Core-Misc")
                .content(Item.class, new GlueBallItem().setRegistryName(new ResourceLocation(Essence.MODID, "glue_ball")))
                .content(TitaniumFluidInstance.class, EssenceObjectHolders.ESSENCE_FLUID)
                .content(TitaniumFluidInstance.class, EssenceObjectHolders.EXP_FLUID)
        );

    public static final Module.Builder TOOLS = Module.builder("tools")
        .force()
        .description("Tool-Content")
        .feature(
            Feature.builder("tools")
                .force()
                .description("Tools")
                .content(Item.class, new PortableCrafterItem().setRegistryName(new ResourceLocation(Essence.MODID, "portable_crafter")))
                .content(Item.class, new EssenceWrench().setRegistryName(new ResourceLocation(Essence.MODID, "wrench")))
                .content(Item.class, new SerializedEntityItem().setRegistryName(new ResourceLocation(Essence.MODID, "serialized_entity")))

                // Tier 1
                .content(Item.class, new EssenceAxe(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_axe")))
                .content(Item.class, new EssencePickaxe(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_pickaxe")))
                .content(Item.class, new EssenceShovel(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shovel")))
                .content(Item.class, new EssenceSword(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_sword")))
                .content(Item.class, new EssenceHoe(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_hoe")))
                .content(Item.class, new EssenceOmniTool(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_omnitool")))
                .content(Item.class, new EssenceShear(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shear")))
                .content(Item.class, new EssenceBow(EssenceToolTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bow")))

                // Tier 2
                .content(Item.class, new EssenceAxe(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_axe_empowered")))
                .content(Item.class, new EssencePickaxe(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_pickaxe_empowered")))
                .content(Item.class, new EssenceShovel(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shovel_empowered")))
                .content(Item.class, new EssenceSword(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_sword_empowered")))
                .content(Item.class, new EssenceHoe(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_hoe_empowered")))
                .content(Item.class, new EssenceOmniTool(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_omnitool_empowered")))
                .content(Item.class, new EssenceShear(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shear_empowered")))
                .content(Item.class, new EssenceBow(EssenceToolTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bow_empowered")))

                // Tier 3
                .content(Item.class, new EssenceAxe(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_axe_supreme")))
                .content(Item.class, new EssencePickaxe(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_pickaxe_supreme")))
                .content(Item.class, new EssenceShovel(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shovel_supreme")))
                .content(Item.class, new EssenceSword(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_sword_supreme")))
                .content(Item.class, new EssenceHoe(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_hoe_supreme")))
                .content(Item.class, new EssenceOmniTool(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_omnitool_supreme")))
                .content(Item.class, new EssenceShear(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shear_supreme")))
                .content(Item.class, new EssenceBow(EssenceToolTiers.SUPREME_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bow_supreme")))

                // Tier 4
                .content(Item.class, new EssenceAxe(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_axe_divine")))
                .content(Item.class, new EssencePickaxe(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_pickaxe_divine")))
                .content(Item.class, new EssenceShovel(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shovel_divine")))
                .content(Item.class, new EssenceSword(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_sword_divine")))
                .content(Item.class, new EssenceHoe(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_hoe_divine")))
                .content(Item.class, new EssenceOmniTool(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_omnitool_divine")))
                .content(Item.class, new EssenceShear(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shear_divine")))
                .content(Item.class, new EssenceBow(EssenceToolTiers.DIVINE_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bow_divine")))
        );
}
