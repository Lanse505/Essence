package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.util.EssenceTags;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

public class EssenceTagRegistrate {

  public static ProviderType<RegistrateTagsProvider<TileEntityType<?>>> TILE_ENTITY = ProviderType.register("tags/tile_entity", (type) -> (p, e) -> new RegistrateTagsProvider<>(p, type, "tile_entity_types", e.getGenerator(), Registry.BLOCK_ENTITY_TYPE, e.getExistingFileHelper()));

  public static void init(Registrate registrate) {
    // Misc Item Tags
    registrate.addDataGenerator(ProviderType.ITEM_TAGS, provider -> {
      provider.getOrCreateBuilder(EssenceTags.EssenceItemTags.AMMO_HOLDER);
      provider.getOrCreateBuilder(EssenceTags.EssenceModifierTags.NONE_TOOL);
    });

    // Misc Block Tags
    registrate.addDataGenerator(ProviderType.BLOCK_TAGS, provider -> {
      provider.getOrCreateBuilder(EssenceTags.EssenceModifierTags.CASCADING_NONE);
      provider.getOrCreateBuilder(EssenceTags.EssenceModifierTags.CASCADING_VEIN).addTag(Tags.Blocks.ORES);
      provider.getOrCreateBuilder(EssenceTags.EssenceModifierTags.CASCADING_LUMBER).addTag(BlockTags.LOGS);
      provider.getOrCreateBuilder(EssenceTags.EssenceModifierTags.CASCADING_EXCAVATION).addTags(Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL);
      provider.getOrCreateBuilder(EssenceTags.EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST).add(Blocks.BEDROCK, Blocks.NETHER_PORTAL, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME).addTags(BlockTags.LOGS, BlockTags.LEAVES, Tags.Blocks.ORES, Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL);
      provider.getOrCreateBuilder(EssenceTags.EssenceBlockTags.FORGE_MOVEABLE_WHITELIST).add(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER, Blocks.HOPPER, Blocks.BARREL);
      provider.getOrCreateBuilder(EssenceTags.EssenceBlockTags.RELOCATION_NOT_SUPPORTED);
    });

    // Misc Entity Tags
    registrate.addDataGenerator(ProviderType.ENTITY_TAGS, provider -> {
      provider.getOrCreateBuilder(EssenceTags.EssenceEntityTags.BLACKLIST).add(
              EntityType.ARMOR_STAND,
              EntityType.ENDER_DRAGON,
              EntityType.PLAYER
      );
      provider.getOrCreateBuilder(EssenceTags.EssenceEntityTags.WHITELIST).add(
              EntityType.BAT, EntityType.BEE, EntityType.BLAZE, EntityType.CAT, EntityType.CAVE_SPIDER,
              EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DONKEY,
              EntityType.DOLPHIN, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDERMITE, EntityType.EVOKER,
              EntityType.FOX, EntityType.GHAST, EntityType.GIANT, EntityType.GUARDIAN, EntityType.HORSE,
              EntityType.HUSK, EntityType.ILLUSIONER, EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MULE,
              EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG,
              EntityType.PUFFERFISH, EntityType.ZOGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.POLAR_BEAR, EntityType.RABBIT,
              EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON,
              EntityType.SKELETON_HORSE, EntityType.SLIME, EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID,
              EntityType.STRAY, EntityType.TRADER_LLAMA, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VEX,
              EntityType.VILLAGER, EntityType.IRON_GOLEM, EntityType.VINDICATOR, EntityType.PILLAGER, EntityType.WANDERING_TRADER,
              EntityType.WITCH, EntityType.WITHER, EntityType.WITHER_SKELETON, EntityType.WOLF, EntityType.ZOMBIE,
              EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.PHANTOM, EntityType.RAVAGER
      );
    });

    // Misc Tile-Entity Tags
    registrate.addDataGenerator(TILE_ENTITY, provider -> {
      provider.getOrCreateBuilder(EssenceTags.EssenceTileEntityTypeTags.RELOCATION_NOT_SUPPORTED);
      provider.getOrCreateBuilder(EssenceTags.EssenceTileEntityTypeTags.IMMOVABLE);
    });
  }

}
