package com.teamacronymcoders.essence.block.wood;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.base.CustomSaplingBlock;
import com.teamacronymcoders.essence.generation.tree.essence_tree.EssenceSaplingTree;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EssenceSaplingBlock extends SaplingBlock {
  public EssenceSaplingBlock (Properties properties) {
    super(new EssenceSaplingTree(), properties);
  }
}
