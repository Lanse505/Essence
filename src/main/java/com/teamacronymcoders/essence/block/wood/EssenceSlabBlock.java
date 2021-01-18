package com.teamacronymcoders.essence.block.wood;

import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.base.CustomSlabBlock;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceItemTags;
import java.util.function.Consumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EssenceSlabBlock extends SlabBlock {
  public EssenceSlabBlock (Properties properties) {
    super(properties);
  }

  @Override
  public boolean isFlammable (BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    return true;
  }

  @Override
  public int getFlammability (BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    return 75;
  }

}
