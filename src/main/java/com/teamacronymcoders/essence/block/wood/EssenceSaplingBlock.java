package com.teamacronymcoders.essence.block.wood;

import com.teamacronymcoders.essence.generation.tree.essence_tree.EssenceSaplingTree;
import net.minecraft.block.SaplingBlock;

public class EssenceSaplingBlock extends SaplingBlock {

  public EssenceSaplingBlock(Properties properties) {
    super(new EssenceSaplingTree(), properties);
  }

}
