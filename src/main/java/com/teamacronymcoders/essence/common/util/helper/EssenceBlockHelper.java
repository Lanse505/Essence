package com.teamacronymcoders.essence.common.util.helper;

import com.teamacronymcoders.essence.common.modifier.item.interaction.cascading.CascadingType;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EssenceBlockHelper {

    public static List<BlockPos> findPositions(BlockState state, BlockPos origin, Level level, CascadingType type) {
        List<BlockPos> found = new ArrayList<>();
        Set<BlockPos> checked = new ObjectOpenHashSet<>();
        found.add(origin);
        Block startBlock = state.getBlock();
        int maxCount = type.getBlockLimit().get() - 1;
        for (int i = 0; i < found.size(); i++) {
            BlockPos blockPos = found.get(i);
            checked.add(blockPos);
            for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))) {
                if (!checked.contains(pos)) {
                    if (type.getSearchLimit().get() == -1 || Math.sqrt(origin.distSqr(pos)) <= type.getSearchLimit().get()) {
                        if (level.isLoaded(pos) && startBlock == level.getBlockState(pos).getBlock()) {
                            found.add(pos.immutable());
                            if (found.size() > maxCount) {
                                return found;
                            }
                        }
                    }
                }
            }
        }
        return found;
    }
}
