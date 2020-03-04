package com.teamacronymcoders.essence.blocks;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.base.CustomLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class EssenceLeavesBlock extends CustomLeavesBlock {
    public EssenceLeavesBlock() {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid());
        setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_leaves"));
        setItemGroup(Essence.CORE_TAB);
    }
}
