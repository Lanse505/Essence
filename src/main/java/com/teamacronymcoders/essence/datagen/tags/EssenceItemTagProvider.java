package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.util.EssenceTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;

import static com.teamacronymcoders.essence.util.EssenceObjectHolders.*;

public class EssenceItemTagProvider extends ItemTagsProvider {

    public EssenceItemTagProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        // Essence Tag<Item>(s)
        getBuilder(EssenceTags.Items.ESSENCE_CRYSTAL).add(ESSENCE_INFUSED_CRYSTAL);
        getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_NUGGET).add(ESSENCE_INFUSED_METAL_NUGGET, ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET, ESSENCE_INFUSED_METAL_SUPREME_NUGGET, ESSENCE_INFUSED_METAL_DIVINE_NUGGET);
        getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL).add(ESSENCE_INFUSED_METAL, ESSENCE_INFUSED_METAL_EMPOWERED, ESSENCE_INFUSED_METAL_SUPREME, ESSENCE_INFUSED_METAL_DIVINE);
        getBuilder(EssenceTags.Items.ESSENCE_INFUSED_STICK).add(ESSENCE_INFUSED_STICK);
        getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_BLOCK).add(ESSENCE_INFUSED_METAL_BLOCK.asItem(), ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK.asItem(), ESSENCE_INFUSED_METAL_SUPREME_BLOCK.asItem(), ESSENCE_INFUSED_METAL_DIVINE_BLOCK.asItem());
        getBuilder(EssenceTags.Items.ESSENCE_WOOD_LOG).add(ESSENCE_WOOD_LOG.asItem());
        getBuilder(EssenceTags.Items.ESSENCE_WOOD_PLANKS).add(ESSENCE_WOOD_PLANKS.asItem());
        getBuilder(EssenceTags.Items.ESSENCE_BRICKS).add(
            ESSENCE_BRICKS_WHITE.asItem(), ESSENCE_BRICKS_ORANGE.asItem(), ESSENCE_BRICKS_MAGENTA.asItem(), ESSENCE_BRICKS_LIGHT_BLUE.asItem(),
            ESSENCE_BRICKS_YELLOW.asItem(), ESSENCE_BRICKS_LIME.asItem(), ESSENCE_BRICKS_PINK.asItem(), ESSENCE_BRICKS_GRAY.asItem(),
            ESSENCE_BRICKS_LIGHT_GRAY.asItem(), ESSENCE_BRICKS_CYAN.asItem(), ESSENCE_BRICKS_PURPLE.asItem(), ESSENCE_BRICKS_BLUE.asItem(),
            ESSENCE_BRICKS_BROWN.asItem(), ESSENCE_BRICKS_GREEN.asItem(), ESSENCE_BRICKS_RED.asItem(), ESSENCE_BRICKS_BLACK.asItem());

        // Essence Modifier-Specific Tag<Item>(s)
        getBuilder(EssenceTags.Modifier.NONE_TOOL);
        getBuilder(EssenceTags.Modifier.AXE_TOOL).add(
            ESSENCE_AXE, ESSENCE_AXE_EMPOWERED, ESSENCE_AXE_SUPREME, ESSENCE_AXE_DIVINE,
            ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_SUPREME, ESSENCE_OMNITOOL_DIVINE
        );
        getBuilder(EssenceTags.Modifier.PICKAXE_TOOL).add(
            ESSENCE_PICKAXE, ESSENCE_PICKAXE_EMPOWERED, ESSENCE_PICKAXE_SUPREME, ESSENCE_PICKAXE_DIVINE,
            ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_SUPREME, ESSENCE_OMNITOOL_DIVINE
        );
        getBuilder(EssenceTags.Modifier.SHOVEL_TOOL).add(
            ESSENCE_SHOVEL, ESSENCE_SHOVEL_EMPOWERED, ESSENCE_SHOVEL_SUPREME, ESSENCE_SHOVEL_DIVINE,
            ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_SUPREME, ESSENCE_OMNITOOL_DIVINE
        );
    }

    @Override
    public String getName() {
        return "Essence Tags<Items>";
    }
}
