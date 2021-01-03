package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceBlockTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceModifierTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamacronymcoders.essence.util.EssenceObjectHolders.*;

public class EssenceBlockTagProvider extends BlockTagsProvider {

    public EssenceBlockTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Essence.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        // Vanilla Tag<Block>
        getOrCreateBuilder(BlockTags.LEAVES).add(ESSENCE_WOOD_LEAVES);
        getOrCreateBuilder(BlockTags.LOGS).add(ESSENCE_WOOD_LOG);
        getOrCreateBuilder(BlockTags.PLANKS).add(ESSENCE_WOOD_PLANKS);
        getOrCreateBuilder(BlockTags.SAPLINGS).add(ESSENCE_WOOD_SAPLING);
        getOrCreateBuilder(BlockTags.SLABS).add(ESSENCE_WOOD_SLAB);
        getOrCreateBuilder(BlockTags.STONE_BRICKS).add(
            ESSENCE_BRICKS_WHITE, ESSENCE_BRICKS_ORANGE, ESSENCE_BRICKS_MAGENTA, ESSENCE_BRICKS_LIGHT_BLUE, ESSENCE_BRICKS_YELLOW, ESSENCE_BRICKS_LIME,
            ESSENCE_BRICKS_PINK, ESSENCE_BRICKS_GRAY, ESSENCE_BRICKS_LIGHT_GRAY, ESSENCE_BRICKS_CYAN, ESSENCE_BRICKS_PURPLE, ESSENCE_BRICKS_BLUE,
            ESSENCE_BRICKS_BROWN, ESSENCE_BRICKS_GREEN, ESSENCE_BRICKS_RED, ESSENCE_BRICKS_BLACK);
        getOrCreateBuilder(BlockTags.WITHER_IMMUNE).add(
            ESSENCE_BRICKS_WHITE, ESSENCE_BRICKS_ORANGE, ESSENCE_BRICKS_MAGENTA, ESSENCE_BRICKS_LIGHT_BLUE, ESSENCE_BRICKS_YELLOW, ESSENCE_BRICKS_LIME,
            ESSENCE_BRICKS_PINK, ESSENCE_BRICKS_GRAY, ESSENCE_BRICKS_LIGHT_GRAY, ESSENCE_BRICKS_CYAN, ESSENCE_BRICKS_PURPLE, ESSENCE_BRICKS_BLUE,
            ESSENCE_BRICKS_BROWN, ESSENCE_BRICKS_GREEN, ESSENCE_BRICKS_RED, ESSENCE_BRICKS_BLACK);

        // Essence Tag<Block>
        getOrCreateBuilder(EssenceBlockTags.ESSENCE_CRYSTAL_ORE).add(ESSENCE_CRYSTAL_ORE);
        getOrCreateBuilder(EssenceBlockTags.ESSENCE_ORE).add(ESSENCE_ORE);
        getOrCreateBuilder(EssenceBlockTags.ESSENCE_WOOD_LOG).add(ESSENCE_WOOD_LOG);
        getOrCreateBuilder(EssenceBlockTags.ESSENCE_BRICKS).add(
            ESSENCE_BRICKS_WHITE, ESSENCE_BRICKS_ORANGE, ESSENCE_BRICKS_MAGENTA, ESSENCE_BRICKS_LIGHT_BLUE, ESSENCE_BRICKS_YELLOW, ESSENCE_BRICKS_LIME,
            ESSENCE_BRICKS_PINK, ESSENCE_BRICKS_GRAY, ESSENCE_BRICKS_LIGHT_GRAY, ESSENCE_BRICKS_CYAN, ESSENCE_BRICKS_PURPLE, ESSENCE_BRICKS_BLUE,
            ESSENCE_BRICKS_BROWN, ESSENCE_BRICKS_GREEN, ESSENCE_BRICKS_RED, ESSENCE_BRICKS_BLACK);

        // Essence Modifier-Specific Tag<Block>
        getOrCreateBuilder(EssenceModifierTags.CASCADING_NONE);
        getOrCreateBuilder(EssenceModifierTags.CASCADING_VEIN)
            .addTag(Tags.Blocks.ORES);
        getOrCreateBuilder(EssenceModifierTags.CASCADING_LUMBER)
            .addTag(BlockTags.LOGS);
        getOrCreateBuilder(EssenceModifierTags.CASCADING_EXCAVATION)
            .addTags(Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL);
        getOrCreateBuilder(EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST).add(
            Blocks.BEDROCK,
            Blocks.NETHER_PORTAL, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME
        );
        getOrCreateBuilder(EssenceBlockTags.FORGE_MOVEABLE_WHITELIST).add(
                Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST,
                Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER,
                Blocks.HOPPER, Blocks.BARREL
        );
    }

    @Override
    public String getName() {
        return "Essence Tags<Blocks>";
    }
}
