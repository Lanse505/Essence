package com.teamacronymcoders.essence.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.blocks.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
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
            matrixStack.push();
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            int i = stack.isEmpty() ? 187 : Item.getIdFromItem(stack.getItem()) + stack.getDamage();
            random.setSeed(i);
            IBakedModel ibakedmodel = renderer.getItemModelWithOverrides(stack, tile.getWorld(), null);
            boolean flag = ibakedmodel.isGui3d();
            float f1 = MathHelper.sin(((float)tile.getTicksExisted() + partial) / 10.0F) * 0.05F + 0.1F;
            float f3 = tile.getTicksExisted() + partial * 0.7F;
            matrixStack.translate(0.5D, f1 + 0.9F, 0.5D);
            matrixStack.rotate(new Quaternion(0, -f3, 0, true));

            matrixStack.push();
            renderer.renderItem(stack, ItemCameraTransforms.TransformType.GROUND, WorldRenderer.getCombinedLight(tile.getWorld(), tile.getPos().up()), overlay, matrixStack, buffer);
            matrixStack.pop();

            if (!flag) {
                matrixStack.translate(0.0, 0.0, 0.09375F);
            }

            matrixStack.pop();
        }
    }
}
