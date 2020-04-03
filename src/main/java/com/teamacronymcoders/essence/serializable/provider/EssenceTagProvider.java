package com.teamacronymcoders.essence.serializable.provider;

import com.teamacronymcoders.essence.serializable.provider.tags.EssenceTagProviders;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.IOException;

public class EssenceTagProvider {

    public static void addTagProviders(DataGenerator generator) {
        generator.addProvider(new EssenceTagProviders.Items(generator));
        generator.addProvider(new EssenceTagProviders.Blocks(generator));
    }

}
