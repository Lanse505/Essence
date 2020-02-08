package lanse505.essence.core.blocks.essence;

import lanse505.essence.core.core.CustomLeavesBlock;
import lanse505.essence.utils.EssenceReferences;
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
