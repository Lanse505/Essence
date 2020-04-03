package com.teamacronymcoders.essence.datagen;

import com.teamacronymcoders.essence.datagen.advancement.ExtendableAdvancementProvider;
import net.minecraft.data.DataGenerator;

public class EssenceAdvancementProvider extends ExtendableAdvancementProvider {

    public EssenceAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Essence Advancements";
    }
}
