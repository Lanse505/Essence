package com.teamacronymcoders.essence.blocks;

import com.teamacronymcoders.essence.base.CustomOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class EssenceOreBlock extends CustomOreBlock {
    public EssenceOreBlock() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
    }
}
