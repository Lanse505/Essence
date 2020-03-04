package com.teamacronymcoders.essence.serializable;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public class EssenceRecipeProvider extends TitaniumRecipeProvider {
    public EssenceRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void register(Consumer<IFinishedRecipe> consumer) {
        BasicBlock.BLOCKS.stream()
            .filter(basicBlock -> basicBlock.getRegistryName().getNamespace().equals(Essence.MODID))
            .forEach(basicBlock -> basicBlock.registerRecipe(consumer));
    }
}
