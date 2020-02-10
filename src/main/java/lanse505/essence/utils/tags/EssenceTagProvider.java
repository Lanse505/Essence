package lanse505.essence.utils.tags;

import lanse505.essence.utils.module.ModuleObjects;
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
            getBuilder(EssenceTags.Items.ESSENCE_CRYSTAL).add(ModuleObjects.ESSENCE_INFUSED_CRYSTAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_NUGGET).add(ModuleObjects.ESSENCE_INFUSED_METAL_NUGGET);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL).add(ModuleObjects.ESSENCE_INFUSED_METAL);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_STICK).add(ModuleObjects.ESSENCE_INFUSED_STICK);
            getBuilder(EssenceTags.Items.ESSENCE_INFUSED_METAL_BLOCK).add(ModuleObjects.ESSENCE_INFUSED_METAL_BLOCK.asItem());
            getBuilder(EssenceTags.Items.ESSENCE_WOOD_LOG).add(ModuleObjects.ESSENCE_WOOD_LOG.asItem());
            getBuilder(EssenceTags.Items.ESSENCE_WOOD_PLANKS).add(ModuleObjects.ESSENCE_WOOD_PLANKS.asItem());
        }
    }

    public static class Blocks extends BlockTagsProvider {

        public Blocks(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            getBuilder(BlockTags.LEAVES).add(ModuleObjects.ESSENCE_WOOD_LEAVES);
            getBuilder(BlockTags.LOGS).add(ModuleObjects.ESSENCE_WOOD_LOG);
            getBuilder(BlockTags.PLANKS).add(ModuleObjects.ESSENCE_WOOD_PLANKS);
            getBuilder(BlockTags.SAPLINGS).add(ModuleObjects.ESSENCE_WOOD_SAPLING);
            getBuilder(BlockTags.SLABS).add(ModuleObjects.ESSENCE_WOOD_SLAB);
            getBuilder(EssenceTags.Blocks.ESSENCE_CRYSTAL_ORE).add(ModuleObjects.ESSENCE_CRYSTAL_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_ORE).add(ModuleObjects.ESSENCE_ORE);
            getBuilder(EssenceTags.Blocks.ESSENCE_WOOD_LOG).add(ModuleObjects.ESSENCE_WOOD_LOG);
        }
    }
}
