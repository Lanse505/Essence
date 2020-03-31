package com.teamacronymcoders.essence.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.helper.EssenceRenderHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class InfusionPedestalTESR extends TileEntityRenderer<InfusionPedestalTile> {

    private static Random random = new Random();

    public InfusionPedestalTESR(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(InfusionPedestalTile tile, float partial, MatrixStack matrixStack, IRenderTypeBuffer buffer, int overlay, int light) {
        BlockState state = tile.getWorld().getBlockState(tile.getPos());
        if (state.getBlock() != EssenceObjectHolders.INFUSION_PEDESTAL) {
            return;
        }

        ItemStack stack = tile.getStack();

        if (!stack.isEmpty()) {
            EssenceRenderHelper.renderItemStack(stack, tile.getWorld(), tile.getPos(), tile.getTicksExisted(), 1.0f, partial, matrixStack, buffer, overlay);
        }
    }
}
