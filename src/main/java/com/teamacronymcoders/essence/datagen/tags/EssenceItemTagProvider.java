package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceItemTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceModifierTags;
import javax.annotation.Nullable;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EssenceItemTagProvider extends ItemTagsProvider {

  public EssenceItemTagProvider (DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, blockTagProvider, Essence.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerTags () {
    // Essence Tag<Item>(s)
    getOrCreateBuilder(EssenceItemTags.AMMO_HOLDER);
    // Essence Modifier-Specific Tag<Item>(s)
    getOrCreateBuilder(EssenceModifierTags.NONE_TOOL);
  }

  @Override
  public String getName () {
    return "Essence Tags<Items>";
  }
}
