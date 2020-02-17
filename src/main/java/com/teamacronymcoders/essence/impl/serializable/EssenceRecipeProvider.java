package lanse505.essence.impl.serializable;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import lanse505.essence.utils.EssenceReferences;
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
                .filter(basicBlock -> basicBlock.getRegistryName().getNamespace().equals(EssenceReferences.MODID))
                .forEach(basicBlock -> basicBlock.registerRecipe(consumer));
    }
}
