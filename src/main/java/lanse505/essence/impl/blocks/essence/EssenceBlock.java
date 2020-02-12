package lanse505.essence.impl.blocks.essence;

import com.hrznstudio.titanium.block.BasicBlock;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class EssenceBlock extends BasicBlock {

    public EssenceBlock() {
        super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).velocityMultiplier(1.25f));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_infused_block"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }

}
