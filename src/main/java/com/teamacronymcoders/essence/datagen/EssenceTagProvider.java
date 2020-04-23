package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.tags.EssenceBlockTagProvider;
import com.teamacronymcoders.essence.datagen.tags.EssenceEntityTagProvider;
import com.teamacronymcoders.essence.datagen.tags.EssenceFluidTagProvider;
import com.teamacronymcoders.essence.datagen.tags.EssenceItemTagProvider;
import net.minecraft.data.DataGenerator;

public class EssenceTagProvider {

    public static void addTagProviders(DataGenerator generator) {
        generator.addProvider(new EssenceItemTagProvider(generator));
        generator.addProvider(new EssenceBlockTagProvider(generator));
        generator.addProvider(new EssenceFluidTagProvider(generator));
        generator.addProvider(new EssenceEntityTagProvider(generator));
    }

}
