package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.tags.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EssenceTagProvider {

  public static void addTagProviders (DataGenerator generator, ExistingFileHelper existingFileHelper) {
    EssenceBlockTagProvider blockTagProvider = new EssenceBlockTagProvider(generator, existingFileHelper);
    generator.addProvider(new EssenceItemTagProvider(generator, blockTagProvider, existingFileHelper));
    generator.addProvider(blockTagProvider);
    generator.addProvider(new EssenceFluidTagProvider(generator, existingFileHelper));
    generator.addProvider(new EssenceEntityTagProvider(generator, existingFileHelper));
    generator.addProvider(new EssenceTileEntityTypeTagProvider(generator, existingFileHelper));
  }

}
