package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.util.EssenceTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

import static com.teamacronymcoders.essence.util.EssenceObjectHolders.*;

public class EssenceBlockTagProvider extends BlockTagsProvider {

    public EssenceBlockTagProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
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

    @Override
    public String getName() {
        return "Essence Tags<Blocks>";
    }
}
