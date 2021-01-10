package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceEntityTags;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EssenceEntityTagProvider extends EntityTypeTagsProvider {

  public EssenceEntityTagProvider (DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
    super(generator, Essence.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerTags () {
    getOrCreateBuilder(EssenceEntityTags.BLACKLIST).add(
            EntityType.ARMOR_STAND,
            EntityType.ENDER_DRAGON,
            EntityType.PLAYER
    );
    getOrCreateBuilder(EssenceEntityTags.WHITELIST).add(
            EntityType.BAT,
            EntityType.BEE,
            EntityType.BLAZE,
            EntityType.CAT,
            EntityType.CAVE_SPIDER,
            EntityType.CHICKEN,
            EntityType.COD,
            EntityType.COW,
            EntityType.CREEPER,
            EntityType.DONKEY,
            EntityType.DOLPHIN,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.FOX,
            EntityType.GHAST,
            EntityType.GIANT,
            EntityType.GUARDIAN,
            EntityType.HORSE,
            EntityType.HUSK,
            EntityType.ILLUSIONER,
            EntityType.LLAMA,
            EntityType.MAGMA_CUBE,
            EntityType.MULE,
            EntityType.MOOSHROOM,
            EntityType.OCELOT,
            EntityType.PANDA,
            EntityType.PARROT,
            EntityType.PIG,
            EntityType.PUFFERFISH,
            EntityType.ZOGLIN,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.POLAR_BEAR,
            EntityType.RABBIT,
            EntityType.SALMON,
            EntityType.SHEEP,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SKELETON_HORSE,
            EntityType.SLIME,
            EntityType.SNOW_GOLEM,
            EntityType.SPIDER,
            EntityType.SQUID,
            EntityType.STRAY,
            EntityType.TRADER_LLAMA,
            EntityType.TROPICAL_FISH,
            EntityType.TURTLE,
            EntityType.VEX,
            EntityType.VILLAGER,
            EntityType.IRON_GOLEM,
            EntityType.VINDICATOR,
            EntityType.PILLAGER,
            EntityType.WANDERING_TRADER,
            EntityType.WITCH,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.WOLF,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.PHANTOM,
            EntityType.RAVAGER
    );
  }

  @Override
  public String getName () {
    return "Essence Tags<EntityType>";
  }
}
