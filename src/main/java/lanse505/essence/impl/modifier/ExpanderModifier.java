package lanse505.essence.impl.modifier;

import lanse505.essence.api.modifier.InteractionCoreModifier;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class ExpanderModifier extends InteractionCoreModifier {

    public ExpanderModifier() {
        super(new ResourceLocation(EssenceReferences.MODID, "expander"), 2);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {
        Stream<BlockPos> blockPosStream = BlockPos.getAllInBox(
                pos.getX() + level, pos.getY() + level, pos.getZ() + level,
                pos.getX() - level, pos.getY() - level, pos.getZ() - level);
        blockPosStream.filter(position -> !position.equals(pos) && stack.canHarvestBlock(state)).forEach(position -> world.breakBlock(position, true, miner));
        return true;
    }
}
