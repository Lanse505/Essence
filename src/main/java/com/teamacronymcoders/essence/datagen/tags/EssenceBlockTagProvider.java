package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceBlockTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceModifierTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import static com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate.*;

public class EssenceBlockTagProvider extends BlockTagsProvider {

  public EssenceBlockTagProvider (DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
    super(generatorIn, Essence.MOD_ID, existingFileHelper);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void registerTags () {
    // Essence Modifier-Specific Tag<Block>
    getOrCreateBuilder(EssenceModifierTags.CASCADING_NONE);
    getOrCreateBuilder(EssenceModifierTags.CASCADING_VEIN)
            .addTag(Tags.Blocks.ORES);
    getOrCreateBuilder(EssenceModifierTags.CASCADING_LUMBER)
            .addTag(BlockTags.LOGS);
    getOrCreateBuilder(EssenceModifierTags.CASCADING_EXCAVATION)
            .addTags(Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL);
    getOrCreateBuilder(EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST)
            // Blacklist Bedrock, Nether Portal Blocks, End Portal Blocks and End Portal Frame Blocks
            .add(Blocks.BEDROCK, Blocks.NETHER_PORTAL, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME)
            // Blacklist Ores, Log-related Blocks, Dirts, Sands, Gravels, etc
            .addTag(Tags.Blocks.ORES)
            .addTags(BlockTags.LOGS, BlockTags.LEAVES)
            .addTags(Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL)
    ;
    getOrCreateBuilder(EssenceBlockTags.FORGE_MOVEABLE_WHITELIST)
            .add(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER, Blocks.HOPPER, Blocks.BARREL);
    getOrCreateBuilder(EssenceBlockTags.RELOCATION_NOT_SUPPORTED);
  }

  @Override
  public String getName () {
    return "Essence Tags<Blocks>";
  }
}
