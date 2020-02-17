package com.teamacronymcoders.essence.impl.blocks.essence.wood;

import com.teamacronymcoders.essence.utils.EssenceReferences;
import com.teamacronymcoders.essence.base.CustomLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class EssenceLeavesBlock extends CustomLeavesBlock {
    public EssenceLeavesBlock() {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).nonOpaque());
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_leaves"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
