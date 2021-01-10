package com.teamacronymcoders.essence.block.wood;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.base.CustomSaplingBlock;
import com.teamacronymcoders.essence.generation.tree.essence_tree.EssenceSaplingTree;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EssenceSaplingBlock extends CustomSaplingBlock {
  public EssenceSaplingBlock () {
    super(new EssenceSaplingTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT));
    setItemGroup(Essence.CORE_TAB);
  }
}
