package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceTileEntityTypeTagProvider extends ForgeRegistryTagsProvider<TileEntityType<?>> {

  public EssenceTileEntityTypeTagProvider (DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
    super(generatorIn, ForgeRegistries.TILE_ENTITIES, Essence.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerTags () {
      getOrCreateBuilder(EssenceTags.EssenceTileEntityTypeTags.RELOCATION_NOT_SUPPORTED);
      getOrCreateBuilder(EssenceTags.EssenceTileEntityTypeTags.IMMOVABLE);
  }

  @Override
  public String getName () {
    return "Essence Tags<TileEntityType>";
  }
}
