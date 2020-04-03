package com.teamacronymcoders.essence.serializable.provider.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags;
import com.teamacronymcoders.essence.util.config.EssenceGeneralConfig;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

import static com.teamacronymcoders.essence.util.EssenceObjectHolders.*;

public class EssenceTagProviders {

    public static class Items extends ItemTagsProvider {
        public Items(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            if (EssenceGeneralConfig.getInstance().getEnableDebugLogging().get()) {
                Essence.LOGGER.info("Registering Tag<Item> tags");
            }

            // Essence Tag<Item>(s)
            getBuilder(EssenceTags.Items.ESSENCE_CRYSTAL).add(ESSENCE_INFUSED_CRYSTAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_NUGGET).add(ESSENCE_INFUSED_METAL_NUGGET, ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET, ESSENCE_INFUSED_METAL_EXALTED_NUGGET, ESSENCE_INFUSED_METAL_GODLY_NUGGET);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL).add(ESSENCE_INFUSED_METAL, ESSENCE_INFUSED_METAL_EMPOWERED, ESSENCE_INFUSED_METAL_EXALTED, ESSENCE_INFUSED_METAL_GODLY);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_STICK).add(ESSENCE_INFUSED_STICK);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_BLOCK).add(ESSENCE_INFUSED_METAL_BLOCK.asItem(), ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK.asItem(), ESSENCE_INFUSED_METAL_EXALTED_BLOCK.asItem(), ESSENCE_INFUSED_METAL_GODLY_BLOCK.asItem());
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
                ESSENCE_AXE, ESSENCE_AXE_EMPOWERED, ESSENCE_AXE_EXALTED, ESSENCE_AXE_GODLY,
                ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_EXALTED, ESSENCE_OMNITOOL_GODLY
            );
            getBuilder(EssenceTags.Modifier.PICKAXE_TOOL).add(
                ESSENCE_PICKAXE, ESSENCE_PICKAXE_EMPOWERED, ESSENCE_PICKAXE_EXALTED, ESSENCE_PICKAXE_GODLY,
                ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_EXALTED, ESSENCE_OMNITOOL_GODLY
            );
            getBuilder(EssenceTags.Modifier.SHOVEL_TOOL).add(
                ESSENCE_SHOVEL, ESSENCE_SHOVEL_EMPOWERED, ESSENCE_SHOVEL_EXALTED, ESSENCE_SHOVEL_GODLY,
                ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_EXALTED, ESSENCE_OMNITOOL_GODLY
            );
        }
    }

    public static class Blocks extends BlockTagsProvider {
        public Blocks(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            if (EssenceGeneralConfig.getInstance().getEnableDebugLogging().get()) {
                Essence.LOGGER.info("Registering Tag<Block> tags");
            }

            // Vanilla Tag<Block>
            getBuilder(BlockTags.LEAVES).add(ESSENCE_WOOD_LEAVES);
            getBuilder(BlockTags.LOGS).add(ESSENCE_WOOD_LOG);
            getBuilder(BlockTags.PLANKS).add(ESSENCE_WOOD_PLANKS);
            getBuilder(BlockTags.SAPLINGS).add(ESSENCE_WOOD_SAPLING);
            getBuilder(BlockTags.SLABS).add(ESSENCE_WOOD_SLAB);
            getBuilder(BlockTags.STONE_BRICKS).add(
                ESSENCE_BRICKS_WHITE, ESSENCE_BRICKS_ORANGE, ESSENCE_BRICKS_MAGENTA, ESSENCE_BRICKS_LIGHT_BLUE, ESSENCE_BRICKS_YELLOW, ESSENCE_BRICKS_LIME,
                ESSENCE_BRICKS_PINK, ESSENCE_BRICKS_GRAY, ESSENCE_BRICKS_LIGHT_GRAY, ESSENCE_BRICKS_CYAN, ESSENCE_BRICKS_PURPLE, ESSENCE_BRICKS_BLUE,
                ESSENCE_BRICKS_BROWN, ESSENCE_BRICKS_GREEN, ESSENCE_BRICKS_RED, ESSENCE_BRICKS_BLACK);
            getBuilder(BlockTags.WITHER_IMMUNE).add(
                ESSENCE_BRICKS_WHITE, ESSENCE_BRICKS_ORANGE, ESSENCE_BRICKS_MAGENTA, ESSENCE_BRICKS_LIGHT_BLUE, ESSENCE_BRICKS_YELLOW, ESSENCE_BRICKS_LIME,
                ESSENCE_BRICKS_PINK, ESSENCE_BRICKS_GRAY, ESSENCE_BRICKS_LIGHT_GRAY, ESSENCE_BRICKS_CYAN, ESSENCE_BRICKS_PURPLE, ESSENCE_BRICKS_BLUE,
                ESSENCE_BRICKS_BROWN, ESSENCE_BRICKS_GREEN, ESSENCE_BRICKS_RED, ESSENCE_BRICKS_BLACK);

            // Essence Tag<Block>
            getBuilder(EssenceTags.Blocks.ESSENCE_CRYSTAL_ORE).add(ESSENCE_CRYSTAL_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_ORE).add(ESSENCE_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_WOOD_LOG).add(ESSENCE_WOOD_LOG);
            getBuilder(EssenceTags.Blocks.ESSENCE_BRICKS).add(
                ESSENCE_BRICKS_WHITE, ESSENCE_BRICKS_ORANGE, ESSENCE_BRICKS_MAGENTA, ESSENCE_BRICKS_LIGHT_BLUE, ESSENCE_BRICKS_YELLOW, ESSENCE_BRICKS_LIME,
                ESSENCE_BRICKS_PINK, ESSENCE_BRICKS_GRAY, ESSENCE_BRICKS_LIGHT_GRAY, ESSENCE_BRICKS_CYAN, ESSENCE_BRICKS_PURPLE, ESSENCE_BRICKS_BLUE,
                ESSENCE_BRICKS_BROWN, ESSENCE_BRICKS_GREEN, ESSENCE_BRICKS_RED, ESSENCE_BRICKS_BLACK);

            // Essence Modifier-Specific Tag<Block>
            getBuilder(EssenceTags.Modifier.CASCADING_NONE);
            getBuilder(EssenceTags.Modifier.CASCADING_VEIN)
                .add(Tags.Blocks.ORES);
            getBuilder(EssenceTags.Modifier.CASCADING_LUMBER)
                .add(BlockTags.LOGS);
            getBuilder(EssenceTags.Modifier.CASCADING_EXCAVATION)
                .add(Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL);

            getBuilder(EssenceTags.Blocks.FORGE_MOVEABLE)
                .add(net.minecraft.block.Blocks.CHEST, net.minecraft.block.Blocks.TRAPPED_CHEST, net.minecraft.block.Blocks.ENDER_CHEST)
                .add(net.minecraft.block.Blocks.FURNACE, net.minecraft.block.Blocks.BLAST_FURNACE, net.minecraft.block.Blocks.SMOKER)
                .add(net.minecraft.block.Blocks.HOPPER, net.minecraft.block.Blocks.BARREL);
        }
    }
}
