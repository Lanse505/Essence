package com.teamacronymcoders.essence.impl.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.impl.blocks.infuser.InfusionPedestalTile;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class PedestalTESR extends TileEntityRenderer<InfusionPedestalTile> {

    public PedestalTESR(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(InfusionPedestalTile tile, float partial, MatrixStack matrixStack, IRenderTypeBuffer buffer, int overlay, int light) {
        BlockState state = tile.getWorld().getBlockState(tile.getPos());
        if (state.getBlock() != EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL) return;
        ItemStack stack = tile.getStack();
        if (!stack.isEmpty() && stack.getCount() > 0)  {
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);

            Minecraft mc = Minecraft.getInstance();
            ItemRenderer renderer = mc.getItemRenderer();
            IBakedModel model = renderer.getItemModelWithOverrides(stack, tile.getWorld(), null);

            renderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer, overlay, light, model);
            matrixStack.pop();
        }
    }
}
