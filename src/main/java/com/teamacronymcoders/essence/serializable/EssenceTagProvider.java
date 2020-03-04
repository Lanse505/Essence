package com.teamacronymcoders.essence.serializable;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.EssenceTags;
import com.teamacronymcoders.essence.utils.config.EssenceGeneralConfig;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

public class EssenceTagProvider {

    public static class Items extends ItemTagsProvider {
        public Items(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            if (EssenceGeneralConfig.enableDebugLogging) {
                Essence.LOGGER.info("Registering Tag<Item> tags");
            }

            // Essence Tag<Item>(s)
            getBuilder(EssenceTags.Items.ESSENCE_CRYSTAL).add(EssenceObjectHolders.ESSENCE_INFUSED_CRYSTAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_NUGGET).add(EssenceObjectHolders.ESSENCE_INFUSED_METAL_NUGGET);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL).add(EssenceObjectHolders.ESSENCE_INFUSED_METAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_STICK).add(EssenceObjectHolders.ESSENCE_INFUSED_STICK);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_BLOCK).add(EssenceObjectHolders.ESSENCE_INFUSED_METAL_BLOCK.asItem());
            getBuilder(EssenceTags.Items.ESSENCE_WOOD_LOG).add(EssenceObjectHolders.ESSENCE_WOOD_LOG.asItem());
            getBuilder(EssenceTags.Items.ESSENCE_WOOD_PLANKS).add(EssenceObjectHolders.ESSENCE_WOOD_PLANKS.asItem());

            // Essence Modifier-Specific Tag<Item>(s)
            getBuilder(EssenceTags.Modifier.NONE_TOOL);
            getBuilder(EssenceTags.Modifier.AXE_TOOL).add(EssenceObjectHolders.ESSENCE_AXE, EssenceObjectHolders.ESSENCE_OMNITOOL);
            getBuilder(EssenceTags.Modifier.PICKAXE_TOOL).add(EssenceObjectHolders.ESSENCE_PICKAXE, EssenceObjectHolders.ESSENCE_OMNITOOL);
            getBuilder(EssenceTags.Modifier.SHOVEL_TOOL).add(EssenceObjectHolders.ESSENCE_SHOVEL, EssenceObjectHolders.ESSENCE_OMNITOOL);
        }
    }

    public static class Blocks extends BlockTagsProvider {
        public Blocks(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            if (EssenceGeneralConfig.enableDebugLogging) {
                Essence.LOGGER.info("Registering Tag<Block> tags");
            }

            // Vanilla Tag<Block>
            getBuilder(BlockTags.LEAVES).add(EssenceObjectHolders.ESSENCE_WOOD_LEAVES);
            getBuilder(BlockTags.LOGS).add(EssenceObjectHolders.ESSENCE_WOOD_LOG);
            getBuilder(BlockTags.PLANKS).add(EssenceObjectHolders.ESSENCE_WOOD_PLANKS);
            getBuilder(BlockTags.SAPLINGS).add(EssenceObjectHolders.ESSENCE_WOOD_SAPLING);
            getBuilder(BlockTags.SLABS).add(EssenceObjectHolders.ESSENCE_WOOD_SLAB);

            // Essence Tag<Block>
            getBuilder(EssenceTags.Blocks.ESSENCE_CRYSTAL_ORE).add(EssenceObjectHolders.ESSENCE_CRYSTAL_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_ORE).add(EssenceObjectHolders.ESSENCE_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_WOOD_LOG).add(EssenceObjectHolders.ESSENCE_WOOD_LOG);

            // Essence Modifier-Specific Tag<Block>
            getBuilder(EssenceTags.Modifier.CASCADING_NONE);
            getBuilder(EssenceTags.Modifier.CASCADING_VEIN).add(Tags.Blocks.ORES);
            getBuilder(EssenceTags.Modifier.CASCADING_LUMBER).add(BlockTags.LOGS);
            getBuilder(EssenceTags.Modifier.CASCADING_EXCAVATION).add(Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL);
        }
    }
}
