package com.teamacronymcoders.essence.impl.serializable;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.tags.EssenceTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;

public class EssenceTagProvider {

    public static class Items extends ItemTagsProvider {

        public Items(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            Essence.LOGGER.debug("Registering Tag<Item> tags");
            getBuilder(EssenceTags.Items.ESSENCE_CRYSTAL).add(EssenceObjectHolders.ESSENCE_INFUSED_CRYSTAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_NUGGET).add(EssenceObjectHolders.ESSENCE_INFUSED_METAL_NUGGET);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL).add(EssenceObjectHolders.ESSENCE_INFUSED_METAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_STICK).add(EssenceObjectHolders.ESSENCE_INFUSED_STICK);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_BLOCK).add(EssenceObjectHolders.ESSENCE_INFUSED_METAL_BLOCK.asItem());
            getBuilder(EssenceTags.Items.ESSENCE_WOOD_LOG).add(EssenceObjectHolders.ESSENCE_WOOD_LOG.asItem());
            getBuilder(EssenceTags.Items.ESSENCE_WOOD_PLANKS).add(EssenceObjectHolders.ESSENCE_WOOD_PLANKS.asItem());
        }
    }

    public static class Blocks extends BlockTagsProvider {

        public Blocks(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            Essence.LOGGER.debug("Registering Tag<Block> tags");
            getBuilder(BlockTags.LEAVES).add(EssenceObjectHolders.ESSENCE_WOOD_LEAVES);
            getBuilder(BlockTags.LOGS).add(EssenceObjectHolders.ESSENCE_WOOD_LOG);
            getBuilder(BlockTags.PLANKS).add(EssenceObjectHolders.ESSENCE_WOOD_PLANKS);
            getBuilder(BlockTags.SAPLINGS).add(EssenceObjectHolders.ESSENCE_WOOD_SAPLING);
            getBuilder(BlockTags.SLABS).add(EssenceObjectHolders.ESSENCE_WOOD_SLAB);
            getBuilder(EssenceTags.Blocks.ESSENCE_CRYSTAL_ORE).add(EssenceObjectHolders.ESSENCE_CRYSTAL_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_ORE).add(EssenceObjectHolders.ESSENCE_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_WOOD_LOG).add(EssenceObjectHolders.ESSENCE_WOOD_LOG);
        }
    }
}
