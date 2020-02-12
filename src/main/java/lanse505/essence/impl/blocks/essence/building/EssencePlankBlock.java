package lanse505.essence.core.blocks.essence;

import com.hrznstudio.titanium.block.BasicBlock;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EssencePlankBlock extends BasicBlock {
    public EssencePlankBlock() {
        super(Properties.create(Material.WOOD, MaterialColor.CYAN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_planks"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 75;
    }
}
