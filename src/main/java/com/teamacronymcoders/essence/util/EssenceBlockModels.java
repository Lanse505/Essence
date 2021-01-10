package com.teamacronymcoders.essence.util;

import com.teamacronymcoders.essence.util.helper.EssenceVoxelHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import static net.minecraft.block.Block.makeCuboidShape;

public class EssenceBlockModels {

  public static final VoxelShape[] INFUSION_PEDESTAL = new VoxelShape[] {
          EssenceVoxelHelper.combine(
                  makeCuboidShape(5.2, 14, 5.2, 10.8, 14.2, 10.8),
                  makeCuboidShape(11, 13, 8, 12, 14, 10),
                  makeCuboidShape(11, 13, 6, 12, 14, 8),
                  makeCuboidShape(10, 13, 5, 11, 14, 6),
                  makeCuboidShape(8, 13, 4, 10, 14, 5),
                  makeCuboidShape(6, 13, 4, 8, 14, 5),
                  makeCuboidShape(5, 13, 5, 6, 14, 6),
                  makeCuboidShape(4, 13, 6, 5, 14, 8),
                  makeCuboidShape(4, 13, 8, 5, 14, 10),
                  makeCuboidShape(5, 13, 10, 6, 14, 11),
                  makeCuboidShape(6, 13, 11, 8, 14, 12),
                  makeCuboidShape(8, 13, 11, 10, 14, 12),
                  makeCuboidShape(5, 13, 6, 6, 14, 10),
                  makeCuboidShape(10, 13, 10, 11, 14, 11),
                  makeCuboidShape(6, 13, 5, 10, 14, 11),
                  makeCuboidShape(10, 13, 6, 11, 14, 10),
                  makeCuboidShape(5, 0, 5, 11, 1, 11),
                  makeCuboidShape(6, 0, 4, 10, 1, 5),
                  makeCuboidShape(6, 0, 11, 10, 1, 12),
                  makeCuboidShape(7, 0, 12, 9, 0.5, 14),
                  makeCuboidShape(12, 0, 7, 14, 0.5, 9),
                  makeCuboidShape(2, 0, 7, 4, 0.5, 9),
                  makeCuboidShape(7, 0, 2, 9, 0.5, 4),
                  makeCuboidShape(4, 0, 6, 5, 1, 10),
                  makeCuboidShape(11, 0, 6, 12, 1, 10),
                  makeCuboidShape(11, 12, 6, 12, 13, 8),
                  makeCuboidShape(10, 1, 5, 11, 2, 6),
                  makeCuboidShape(5, 7, 10, 6, 8, 11),
                  makeCuboidShape(10, 10, 10, 11, 11, 11),
                  makeCuboidShape(8, 2, 4, 10, 3, 5),
                  makeCuboidShape(6, 3, 4, 8, 4, 5),
                  makeCuboidShape(6, 8, 11, 8, 9, 12),
                  makeCuboidShape(8, 9, 11, 10, 10, 12),
                  makeCuboidShape(5, 4, 5, 6, 5, 6),
                  makeCuboidShape(4, 5, 6, 5, 6, 8),
                  makeCuboidShape(4, 6, 8, 5, 7, 10),
                  makeCuboidShape(11, 11, 8, 12, 12, 10),
                  makeCuboidShape(5, 1, 10, 6, 2, 11),
                  makeCuboidShape(10, 4, 10, 11, 5, 11),
                  makeCuboidShape(10, 7, 5, 11, 8, 6),
                  makeCuboidShape(8, 8, 4, 10, 9, 5),
                  makeCuboidShape(6, 9, 4, 8, 10, 5),
                  makeCuboidShape(5, 10, 5, 6, 11, 6),
                  makeCuboidShape(4, 11, 6, 5, 12, 8),
                  makeCuboidShape(4, 12, 8, 5, 13, 10),
                  makeCuboidShape(11, 5, 8, 12, 6, 10),
                  makeCuboidShape(11, 6, 6, 12, 7, 8),
                  makeCuboidShape(6, 2, 11, 8, 3, 12),
                  makeCuboidShape(8, 3, 11, 10, 4, 12),
                  makeCuboidShape(6, 0.1, 6, 10, 13, 10),
                  makeCuboidShape(6.5, 1, 5.75, 9.5, 13, 6),
                  makeCuboidShape(6.5, 1, 10, 9.5, 13, 10.25),
                  makeCuboidShape(5.75, 1, 6.5, 6, 13, 9.5),
                  makeCuboidShape(10, 1, 6.5, 10.25, 13, 9.5)
          )
  };
  public static final VoxelShape[] INFUSION_TABLE = new VoxelShape[] {
          EssenceVoxelHelper.combine(
                  makeCuboidShape(1, 0, 1, 15, 9, 15),
                  makeCuboidShape(0, 4, 0, 2, 10, 2),
                  makeCuboidShape(0, 4, 14, 2, 10, 16),
                  makeCuboidShape(14, 4, 14, 16, 10, 16),
                  makeCuboidShape(14, 4, 0, 16, 10, 2),
                  makeCuboidShape(5, 0.1, 0.5, 11, 9.5, 3.5),
                  makeCuboidShape(5, 0.1, 12.5, 11, 9.5, 15.5),
                  makeCuboidShape(12.5, 0.1, 5, 15.5, 9.5, 11),
                  makeCuboidShape(0.5, 0.1, 5, 3.5, 9.5, 11),
                  makeCuboidShape(3, 9, 3, 13, 10, 13)
          )
  };

}
