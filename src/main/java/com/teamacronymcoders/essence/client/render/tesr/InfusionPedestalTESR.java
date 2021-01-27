package com.teamacronymcoders.essence.client.render.tesr;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceRenderHelper;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class InfusionPedestalTESR extends TileEntityRenderer<InfusionPedestalTile> {

  private static final Random random = new Random();

  public InfusionPedestalTESR(TileEntityRendererDispatcher rendererDispatcher) {
    super(rendererDispatcher);
  }

  @Override
  public void render(InfusionPedestalTile tile, float partial, MatrixStack matrixStack, IRenderTypeBuffer buffer, int overlay, int light) {
    if (tile.getWorld() != null) {
      tile.getWorld().getBlockState(tile.getPos());
      BlockState state = tile.getWorld().getBlockState(tile.getPos());
      if (state.getBlock() != EssenceBlockRegistrate.INFUSION_PEDESTAL.get()) {
        return;
      }

      ItemStack stack = tile.getStack();

      if (!stack.isEmpty()) {
        EssenceRenderHelper.renderItemStack(stack, tile.getWorld(), tile.getPos(), tile.getTicksExisted(), 1.0f, partial, matrixStack, buffer);
      }
    }
  }
}
