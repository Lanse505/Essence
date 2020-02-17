package com.teamacronymcoders.essence.impl.blocks.essence.ore;

import com.teamacronymcoders.essence.base.CustomOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class EssenceOre extends CustomOreBlock {
    public EssenceOre(ResourceLocation resourceLocation) {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
        setRegistryName(resourceLocation);
    }
}
