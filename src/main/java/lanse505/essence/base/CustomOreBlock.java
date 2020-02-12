package lanse505.essence.base.core;

import com.hrznstudio.titanium.block.BasicBlock;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class CustomOreBlock extends BasicBlock {

    public CustomOreBlock(Properties properties) {
        super(properties);
        setItemGroup(EssenceReferences.CORE_TAB);
    }

    protected int getExperience(Random random) {
        return 0;
    }

    @Override
    public void spawnAdditionalDrops(BlockState state, World world, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, world, pos, stack);
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}
