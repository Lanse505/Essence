package com.teamacronymcoders.essence.common.util;

import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.level.block.Block.box;


public class EssenceBlockModels {

    public static final VoxelShape[] INFUSION_PEDESTAL = new VoxelShape[]{
            EssenceVoxelHelper.combine(
                    box(10, 11, 6, 11, 12, 10),
                    box(5, 11, 10, 11, 12, 11),
                    box(5, 11, 5, 11, 12, 6),
                    box(5, 11, 6, 6, 12, 10),
                    box(10, 6, 6, 11, 7, 10),
                    box(5, 6, 10, 11, 7, 11),
                    box(5, 6, 5, 11, 7, 6),
                    box(5, 6, 6, 6, 7, 10),
                    box(5, 1, 10, 11, 2, 11),
                    box(5, 1, 5, 11, 2, 6),
                    box(5, 1, 6, 6, 2, 10),
                    box(10, 1, 6, 11, 2, 10),
                    box(5.5, 7, 5.5, 10.5, 11, 10.5),
                    box(5, 13, 5, 11, 14, 11),
                    box(4, 0, 4, 12, 1, 12),
                    box(6, 2, 6, 10, 13, 10)
            )
    };
    public static final VoxelShape[] INFUSION_TABLE = new VoxelShape[]{
            EssenceVoxelHelper.combine(
                    box(0, 7, 14, 2, 8, 16),
                    box(1, 0, 1, 15, 9, 15),
                    box(3, 9, 3, 13, 10, 13),
                    box(0, 7, 0, 2, 8, 2),
                    box(0, 1, 0, 2, 2, 2),
                    box(0, 1, 2, 1, 8, 3),
                    box(2, 1, 0, 3, 8, 1),
                    box(14, 7, 0, 16, 8, 2),
                    box(14, 1, 0, 16, 2, 2),
                    box(15, 1, 2, 16, 8, 3),
                    box(13, 1, 0, 14, 8, 1),
                    box(14, 7, 14, 16, 8, 16),
                    box(14, 1, 14, 16, 2, 16),
                    box(15, 1, 13, 16, 8, 14),
                    box(13, 1, 15, 14, 8, 16),
                    box(2, 1, 15, 3, 8, 16),
                    box(0, 1, 14, 2, 2, 16),
                    box(0.5, 2, 13.5, 2.5, 7, 15.5),
                    box(0, 1, 13, 1, 8, 14),
                    box(5, 0, 15, 11, 10, 16),
                    box(5, 9, 13, 11, 10, 15),
                    box(5, 9, 1, 11, 10, 3),
                    box(5, 0, 0, 11, 10, 1),
                    box(13, 9, 5, 15, 10, 11),
                    box(15, 0, 5, 16, 10, 11),
                    box(0, 0, 5, 1, 10, 11),
                    box(1, 9, 5, 3, 10, 11),
                    box(13.5, 2, 13.5, 15.5, 7, 15.5),
                    box(0.5, 2, 0.5, 2.5, 7, 2.5),
                    box(13.5, 2, 0.5, 15.5, 7, 2.5)
            )
    };

}
