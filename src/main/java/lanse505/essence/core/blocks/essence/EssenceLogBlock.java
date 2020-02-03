package lanse505.essence.core.blocks.essence;

import lanse505.essence.core.core.CustomRotatedPillarBlock;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class EssenceLogBlock extends CustomRotatedPillarBlock {
    public EssenceLogBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
        setRegistryName(EssenceReferences.MODID, "essence_wood_log");
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
