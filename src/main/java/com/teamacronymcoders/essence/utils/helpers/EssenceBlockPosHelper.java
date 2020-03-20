package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.modifier.item.interaction.cascading.CascadingType;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EssenceBlockPosHelper {
    public static List<BlockPos> findPositions(BlockState state, BlockPos origin, World world, int maxBlocks, int maxRange) {
        List<BlockPos> found = new ArrayList<>();
        Set<BlockPos> checked = new ObjectOpenHashSet<>();
        found.add(origin);
        Block startBlock = state.getBlock();
        int maxCount = maxBlocks - 1;
        for (int i = 0; i < found.size(); i++) {
            BlockPos blockPos = found.get(i);
            checked.add(blockPos);
            for (BlockPos pos : BlockPos.getAllInBoxMutable(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1))) {
                if (!checked.contains(pos)) {
                    if (maxRange == -1 || Math.sqrt(origin.distanceSq(pos)) <= maxRange) {
                        if (world.isBlockPresent(pos) && startBlock == world.getBlockState(pos).getBlock()) {
                            found.add(pos.toImmutable());
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

    public static List<BlockPos> findPositions(BlockState state, BlockPos origin, World world, CascadingType type) {
        List<BlockPos> found = new ArrayList<>();
        Set<BlockPos> checked = new ObjectOpenHashSet<>();
        found.add(origin);
        Block startBlock = state.getBlock();
        int maxCount = type.getBlockLimit().get() - 1;
        for (int i = 0; i < found.size(); i++) {
            BlockPos blockPos = found.get(i);
            checked.add(blockPos);
            for (BlockPos pos : BlockPos.getAllInBoxMutable(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1))) {
                if (!checked.contains(pos)) {
                    if (type.getSearchLimit().get() == -1 || Math.sqrt(origin.distanceSq(pos)) <= type.getSearchLimit().get()) {
                        if (world.isBlockPresent(pos) && startBlock == world.getBlockState(pos).getBlock()) {
                            found.add(pos.toImmutable());
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
