package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.lang.EssenceEnglishLangProvider;
import net.minecraft.data.DataGenerator;

public class EssenceLangProvider {

    public static void registerLangProviders(DataGenerator generator) {
        generator.addProvider(new EssenceEnglishLangProvider(generator));
    }

}
