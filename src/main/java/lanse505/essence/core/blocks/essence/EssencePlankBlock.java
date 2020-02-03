package lanse505.essence.core.blocks.essence;

import com.hrznstudio.titanium.block.BasicBlock;
import lanse505.essence.utils.EssenceReferences;
import lanse505.essence.utils.module.ModuleObjects;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

public class EssencePlankBlock extends BasicBlock {
    public EssencePlankBlock() {
        super(Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_planks"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
