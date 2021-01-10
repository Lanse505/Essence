package com.teamacronymcoders.essence.block.wood;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceItemTags;
import java.util.function.Consumer;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EssencePlankBlock extends BasicBlock {
  public EssencePlankBlock () {
    super(Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
    setItemGroup(Essence.CORE_TAB);
  }

  @Override
  public boolean isFlammable (BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    return true;
  }

  @Override
  public int getFlammability (BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    return 75;
  }

  @Override
  public void registerRecipe (Consumer<IFinishedRecipe> consumer) {
    TitaniumShapelessRecipeBuilder.shapelessRecipe(this, 4)
            .addIngredient(EssenceItemTags.ESSENCE_WOOD_LOG).build(consumer);
  }
}
