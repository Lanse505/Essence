package com.teamacronymcoders.essence.utils;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.essence.impl.blocks.essence.EssenceBlock;
import com.teamacronymcoders.essence.impl.blocks.essence.building.EssencePlankBlock;
import com.teamacronymcoders.essence.impl.blocks.essence.building.EssenceSlabBlock;
import com.teamacronymcoders.essence.impl.blocks.essence.ore.EssenceCrystalOre;
import com.teamacronymcoders.essence.impl.blocks.essence.ore.EssenceOre;
import com.teamacronymcoders.essence.impl.blocks.essence.wood.EssenceLeavesBlock;
import com.teamacronymcoders.essence.impl.blocks.essence.wood.EssenceLogBlock;
import com.teamacronymcoders.essence.impl.blocks.essence.wood.EssenceSapling;
import com.teamacronymcoders.essence.impl.blocks.infuser.InfusionPedestalBlock;
import com.teamacronymcoders.essence.impl.blocks.infuser.InfusionTableBlock;
import com.teamacronymcoders.essence.impl.items.essence.EssenceCrystal;
import com.teamacronymcoders.essence.impl.items.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.impl.items.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.impl.items.essence.EssenceStickItem;
import com.teamacronymcoders.essence.impl.items.tools.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static com.teamacronymcoders.essence.utils.EssenceReferences.MODID;

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
                .content(Block.class, new EssenceCrystalOre())
                .content(Block.class, new EssenceLeavesBlock())
                .content(Block.class, new EssenceLogBlock())
                .content(Block.class, new EssenceOre(new ResourceLocation(MODID, "essence_ore")))
                .content(Block.class, new EssencePlankBlock())
                .content(Block.class, new EssenceSapling())
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
                .content(Item.class, new EssenceAxe(new ResourceLocation(MODID, "essence_axe")))
                .content(Item.class, new EssencePickaxe(new ResourceLocation(MODID, "essence_pickaxe")))
                .content(Item.class, new EssenceShovel(new ResourceLocation(MODID, "essence_shovel")))
                .content(Item.class, new EssenceSword(new ResourceLocation(MODID, "essence_sword")))
                .content(Item.class, new EssenceHoe(new ResourceLocation(MODID, "essence_hoe")))
                .content(Item.class, new EssenceOmniTool(new ResourceLocation(MODID, "essence_omnitool")))
                .content(Item.class, new EssenceShear(new ResourceLocation(MODID, "essence_shear")))
        );
}
