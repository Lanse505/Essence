package lanse505.essence.core.blocks.essence;

import lanse505.essence.core.core.CustomSlabBlock;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

public class EssenceSlabBlock extends CustomSlabBlock {
    public EssenceSlabBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_slab"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
