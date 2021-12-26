package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;

public class EssenceTagRegistrate {

  public static ProviderType<RegistrateTagsProvider<BlockEntityType<?>>> TILE_ENTITY = ProviderType.register("tags/tile_entity", (type) -> (p, e) -> new RegistrateTagsProvider<>(p, type, "tile_entity_types", e.getGenerator(), Registry.BLOCK_ENTITY_TYPE, e.getExistingFileHelper()));
  private static final EntityType<?>[] blacklistedEntities = new EntityType[] {
          EntityType.ARMOR_STAND, EntityType.ENDER_DRAGON, EntityType.PLAYER
  };
  private static final EntityType<?>[] whitelistedEntities = new EntityType[] {
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
  };

  private static final Block[] blacklistedBlocks = new Block[] {
          Blocks.BEDROCK, Blocks.NETHER_PORTAL, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME
  };

  private static final Tag.Named<Block>[] blacklistedTags = new Tag.Named[] {
          BlockTags.LOGS, BlockTags.LEAVES, Tags.Blocks.ORES, Tags.Blocks.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL
  };

  private static final Block[] whitelistedBlocks = new Block[] {
          Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER, Blocks.HOPPER, Blocks.BARREL
  };

  public static void init(Registrate registrate) {
    // Misc Item Tags
    registrate.addDataGenerator(ProviderType.ITEM_TAGS, provider -> {
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.AMMO_HOLDER);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceModifierTags.NONE_TOOL);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.MINEABLE_AXE)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE_DIVINE.getId(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.MINEABLE_PICKAXE)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE_DIVINE.getId(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.MINEABLE_SHOVEL)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL_DIVINE.getId(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.MINEABLE_HOE)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE_DIVINE.getId(), Essence.MOD_ID);

      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.IRON_TIER)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE.getId(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.DIAMOND_TIER)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL_SUPREME.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE_EMPOWERED.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE_SUPREME.getId(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceItemTags.NETHERITE_TIER)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_AXE_DIVINE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_PICKAXE_DIVINE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_SHOVEL_DIVINE.getId(), Essence.MOD_ID)
              .addOptionalElement(EssenceItemRegistrate.ESSENCE_HOE_DIVINE.getId(), Essence.MOD_ID);
    });

    // Misc Block Tags
    registrate.addDataGenerator(ProviderType.BLOCK_TAGS, provider -> {
      provider.getOrCreateRawBuilder(EssenceTags.EssenceModifierTags.CASCADING_NONE);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceModifierTags.CASCADING_VEIN).addTag(Tags.Blocks.ORES.getName(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceModifierTags.CASCADING_LUMBER).addTag(BlockTags.LOGS.getName(), Essence.MOD_ID);
      provider.getOrCreateRawBuilder(EssenceTags.EssenceModifierTags.CASCADING_EXCAVATION).addTag(Tags.Blocks.DIRT.getName(), Essence.MOD_ID).addTag(Tags.Blocks.SAND.getName(), Essence.MOD_ID).addTag(Tags.Blocks.GRAVEL.getName(), Essence.MOD_ID);
      Tag.Builder blacklist = provider.getOrCreateRawBuilder(EssenceTags.EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST);
      for (Block block : blacklistedBlocks) {
        ResourceLocation rl = block.getRegistryName();
        if (rl != null) blacklist.addElement(rl, Essence.MOD_ID);
      }
      for (Tag.Named<Block> tag : blacklistedTags) {
        blacklist.addTag(tag.getName(), Essence.MOD_ID);
      }
      Tag.Builder whitelist = provider.getOrCreateRawBuilder(EssenceTags.EssenceBlockTags.FORGE_MOVEABLE_WHITELIST);
      for (Block block : whitelistedBlocks) {
        ResourceLocation rl = block.getRegistryName();
        if (rl != null) whitelist.addElement(rl, Essence.MOD_ID);
      }
    });

    // Misc Entity Tags
    registrate.addDataGenerator(ProviderType.ENTITY_TAGS, provider -> {
      Tag.Builder blacklist = provider.getOrCreateRawBuilder(EssenceTags.EssenceEntityTags.BLACKLIST);
      for (EntityType<?> type : blacklistedEntities) {
        ResourceLocation rl = type.getRegistryName();
        if (rl != null) blacklist.addElement(rl, Essence.MOD_ID);
      }
      Tag.Builder whitelist = provider.getOrCreateRawBuilder(EssenceTags.EssenceEntityTags.WHITELIST);
      for (EntityType<?> type : whitelistedEntities) {
        ResourceLocation rl = type.getRegistryName();
        if (rl != null) whitelist.addElement(rl, Essence.MOD_ID);
      }
    });

  }

}
