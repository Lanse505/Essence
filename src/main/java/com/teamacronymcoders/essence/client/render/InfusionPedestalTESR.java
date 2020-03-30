package com.teamacronymcoders.essence.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.blocks.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.helpers.EssenceRenderHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

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
