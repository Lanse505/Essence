package com.teamacronymcoders.essence.block;

import com.teamacronymcoders.essence.block.base.CustomOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EssenceOreBlock extends CustomOreBlock {
  public EssenceOreBlock () {
    super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
  }
}
