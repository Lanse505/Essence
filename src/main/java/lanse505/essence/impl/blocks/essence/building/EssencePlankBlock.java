package lanse505.essence.impl.blocks.essence.building;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import lanse505.essence.utils.EssenceReferences;
import lanse505.essence.utils.tags.EssenceTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Consumer;

public class EssencePlankBlock extends BasicBlock {
    public EssencePlankBlock() {
        super(Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_planks"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 75;
    }

    @Override
    public void registerRecipe(Consumer<IFinishedRecipe> consumer) {
        TitaniumShapelessRecipeBuilder.shapelessRecipe(this, 4)
                .addIngredient(EssenceTags.Items.ESSENCE_WOOD_LOG).build(consumer);
    }
}
