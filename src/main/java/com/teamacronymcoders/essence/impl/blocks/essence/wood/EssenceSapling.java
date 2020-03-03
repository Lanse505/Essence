package com.teamacronymcoders.essence.impl.blocks.essence.wood;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.base.CustomSaplingBlock;
import com.teamacronymcoders.essence.impl.generation.tree.feature.essence_tree.EssenceSaplingTree;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EssenceSapling extends CustomSaplingBlock {
    public EssenceSapling() {
        super(new EssenceSaplingTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT));
        setRegistryName(Essence.MODID, "essence_wood_sapling");
        setItemGroup(Essence.CORE_TAB);
    }
}
