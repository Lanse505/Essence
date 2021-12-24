package com.teamacronymcoders.essence.util.helper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class EssenceRenderHelper {

  private static final Random random = new Random();

  @OnlyIn(Dist.CLIENT)
  public static void renderItemStack(ItemStack stack, Level level, BlockPos pos, int existedTicks, float yOffset, float partial, PoseStack poseStack, MultiBufferSource buffer) {
    poseStack.pushPose();
    ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
    int i = stack.isEmpty() ? 187 : Item.getId(stack.getItem()) + stack.getDamageValue();
    random.setSeed(i);
    BakedModel bakedModel = renderer.getModel(stack, level, null, i);
    boolean flag = bakedModel.isGui3d();
    float f1 = Mth.sin(((float) existedTicks + partial) / 10.0F) * 0.05F + 0.1F;
    float f3 = existedTicks + partial * 0.7F;
    poseStack.translate(0.5D, f1 + yOffset, 0.5D);
    poseStack.mulPose(new Quaternion(0, -f3, 0, true));

    poseStack.pushPose();
    renderer.renderStatic(stack, ItemTransforms.TransformType.GROUND, LevelRenderer.getLightColor(level, pos), OverlayTexture.NO_OVERLAY, poseStack, buffer, i);
    poseStack.popPose();

    if (!flag) {
      poseStack.translate(0.0, 0.0, 0.09375F);
    }

    poseStack.popPose();
  }


}
