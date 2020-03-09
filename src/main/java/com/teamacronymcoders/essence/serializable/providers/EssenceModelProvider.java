package com.teamacronymcoders.essence.serializable.providers;

import com.hrznstudio.titanium.recipe.generator.BlockItemModelGeneratorProvider;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.data.DataGenerator;

public class EssenceModelProvider extends BlockItemModelGeneratorProvider {

    public EssenceModelProvider(DataGenerator generator) {
        super(generator, Essence.MODID);
    }

}
