package com.teamacronymcoders.essence.util.helper;

import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class EssenceVoxelHelper {

  public static VoxelShape combine(VoxelShape... shapes) {
    return batchCombine(VoxelShapes.empty(), IBooleanFunction.OR, true, shapes);
  }

  public static VoxelShape batchCombine(VoxelShape initial, IBooleanFunction function, boolean simplify, VoxelShape... shapes) {
    VoxelShape combinedShape = initial;
    for (VoxelShape shape : shapes) {
      combinedShape = VoxelShapes.combine(combinedShape, shape, function);
    }
    return simplify ? combinedShape.simplify() : combinedShape;
  }
}
