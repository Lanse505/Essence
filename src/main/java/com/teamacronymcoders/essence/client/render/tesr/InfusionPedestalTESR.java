package com.teamacronymcoders.essence.client.render.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionPedestalBlockEntity;
import com.teamacronymcoders.essence.common.util.helper.EssenceRenderHelper;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class InfusionPedestalTESR implements BlockEntityRenderer<InfusionPedestalBlockEntity> {

    public InfusionPedestalTESR(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(InfusionPedestalBlockEntity tile, float partial, PoseStack poseStack, MultiBufferSource buffer, int overlay, int light) {
        if (tile.getLevel() != null) {
            tile.getLevel().getBlockState(tile.getBlockPos());
            BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
            if (state.getBlock() != EssenceBlockRegistrate.INFUSION_PEDESTAL.get()) {
                return;
            }

            ItemStack stack = tile.getStack();

            if (!stack.isEmpty()) {
                EssenceRenderHelper.renderItemStack(stack, tile.getLevel(), tile.getBlockPos(), tile.getTicksExisted(), 1.0f, partial, poseStack, buffer);
            }
        }
    }
}
