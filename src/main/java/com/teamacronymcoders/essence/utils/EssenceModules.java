package com.teamacronymcoders.essence.utils;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
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
import net.minecraft.block.Block;
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
                .content(Item.class, new EssenceCrystal())
                .content(Item.class, new EssenceIngotItem())
                .content(Item.class, new EssenceNuggetItem())
                .content(Item.class, new EssenceStickItem())
        )
        .feature(
            Feature.builder("blocks")
                .force()
                .description("Core-Blocks")
                .content(Block.class, new EssenceBlock())
                .content(Block.class, new EssenceCrystalOreBlock())
                .content(Block.class, new EssenceLeavesBlock())
                .content(Block.class, new EssenceLogBlock())
                .content(Block.class, new EssenceOreBlock(new ResourceLocation(Essence.MODID, "essence_ore")))
                .content(Block.class, new EssencePlankBlock())
                .content(Block.class, new EssenceSaplingBlock())
                .content(Block.class, new EssenceSlabBlock())
        )
        .feature(
            Feature.builder("infusion")
                .force()
                .description("Infusion-Blocks")
                .content(Block.class, new InfusionPedestalBlock())
                .content(Block.class, new InfusionTableBlock())
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
                .content(Item.class, new EssenceAxe(new ResourceLocation(Essence.MODID, "essence_axe")))
                .content(Item.class, new EssencePickaxe(new ResourceLocation(Essence.MODID, "essence_pickaxe")))
                .content(Item.class, new EssenceShovel(new ResourceLocation(Essence.MODID, "essence_shovel")))
                .content(Item.class, new EssenceSword(new ResourceLocation(Essence.MODID, "essence_sword")))
                .content(Item.class, new EssenceHoe(new ResourceLocation(Essence.MODID, "essence_hoe")))
                .content(Item.class, new EssenceOmniTool(new ResourceLocation(Essence.MODID, "essence_omnitool")))
                .content(Item.class, new EssenceShear(new ResourceLocation(Essence.MODID, "essence_shear")))
                .content(Item.class, new EssenceBow(new ResourceLocation(Essence.MODID, "essence_bow")))
        );
}
