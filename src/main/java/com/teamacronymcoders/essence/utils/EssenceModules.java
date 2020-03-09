package com.teamacronymcoders.essence.utils;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.blocks.*;
import com.teamacronymcoders.essence.blocks.wood.EssencePlankBlock;
import com.teamacronymcoders.essence.blocks.wood.EssenceSlabBlock;
import com.teamacronymcoders.essence.blocks.wood.EssenceLeavesBlock;
import com.teamacronymcoders.essence.blocks.wood.EssenceLogBlock;
import com.teamacronymcoders.essence.blocks.wood.EssenceSaplingBlock;
import com.teamacronymcoders.essence.items.essence.EssenceCrystal;
import com.teamacronymcoders.essence.items.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.items.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.items.essence.EssenceStickItem;
import com.teamacronymcoders.essence.items.tools.*;
import com.teamacronymcoders.essence.utils.tiers.EssenceItemTiers;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
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
                .content(Item.class, new EssenceIngotItem(EssenceItemTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_ingot_exalted")))
                .content(Item.class, new EssenceIngotItem(EssenceItemTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_ingot_godly")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget_empowered")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget_exalted")))
                .content(Item.class, new EssenceNuggetItem(EssenceItemTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_nugget_godly")))
                .content(Item.class, new EssenceStickItem().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_sticks")))
        )
        .feature(
            Feature.builder("blocks")
                .force()
                .description("Core-Blocks")
                .content(Block.class, new EssenceBlock(EssenceItemTiers.ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block")))
                .content(Block.class, new EssenceBlock(EssenceItemTiers.EMPOWERED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block_empowered")))
                .content(Block.class, new EssenceBlock(EssenceItemTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block_exalted")))
                .content(Block.class, new EssenceBlock(EssenceItemTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_infused_block_godly")))
                .content(Block.class, new EssenceCrystalOreBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_crystal_ore")))
                .content(Block.class, new EssenceLeavesBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_leaves")))
                .content(Block.class, new EssenceLogBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_log")))
                .content(Block.class, new EssenceOreBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_ore")))
                .content(Block.class, new EssencePlankBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_planks")))
                .content(Block.class, new EssenceSaplingBlock().setRegistryName(Essence.MODID, "essence_wood_sapling"))
                .content(Block.class, new EssenceSlabBlock().setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_slab")))
                .content(Block.class, new EssenceBrickBlock(DyeColor.WHITE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.WHITE.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.ORANGE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.ORANGE.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.MAGENTA).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.MAGENTA.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.LIGHT_BLUE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.LIGHT_BLUE.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.YELLOW).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.YELLOW.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.LIME).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.LIME.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.PINK).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.PINK.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.GRAY).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.GRAY.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.LIGHT_GRAY).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.LIGHT_GRAY.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.CYAN).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.CYAN.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.PURPLE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.PURPLE.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.BLUE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.BLUE.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.BROWN).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.BROWN.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.GREEN).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.GREEN.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.RED).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.RED.getName())))
                .content(Block.class, new EssenceBrickBlock(DyeColor.BLACK).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bricks_" + DyeColor.BLACK.getName())))
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
                .content(TitaniumFluidInstance.class, EssenceObjectHolders.ESSENCE_FLUID)
        );

    public static final Module.Builder TOOLS = Module.builder("tools")
        .force()
        .description("Tool-Content")
        .feature(
            Feature.builder("tools")
                .force()
                .description("Tools")

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
                .content(Item.class, new EssenceAxe(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_axe_exalted")))
                .content(Item.class, new EssencePickaxe(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_pickaxe_exalted")))
                .content(Item.class, new EssenceShovel(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shovel_exalted")))
                .content(Item.class, new EssenceSword(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_sword_exalted")))
                .content(Item.class, new EssenceHoe(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_hoe_exalted")))
                .content(Item.class, new EssenceOmniTool(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_omnitool_exalted")))
                .content(Item.class, new EssenceShear(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shear_exalted")))
                .content(Item.class, new EssenceBow(EssenceToolTiers.EXALTED_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bow_exalted")))

                // Tier 4
                .content(Item.class, new EssenceAxe(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_axe_godly")))
                .content(Item.class, new EssencePickaxe(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_pickaxe_godly")))
                .content(Item.class, new EssenceShovel(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shovel_godly")))
                .content(Item.class, new EssenceSword(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_sword_godly")))
                .content(Item.class, new EssenceHoe(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_hoe_godly")))
                .content(Item.class, new EssenceOmniTool(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_omnitool_godly")))
                .content(Item.class, new EssenceShear(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_shear_godly")))
                .content(Item.class, new EssenceBow(EssenceToolTiers.GODLY_ESSENCE).setRegistryName(new ResourceLocation(Essence.MODID, "essence_bow_godly")))
        );
}
