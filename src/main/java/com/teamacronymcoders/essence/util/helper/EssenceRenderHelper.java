package com.teamacronymcoders.essence.util.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EssenceRenderHelper {

  private static final Random random = new Random();

  @OnlyIn(Dist.CLIENT)
  public static void renderItemStack (ItemStack stack, World world, BlockPos pos, int existedTicks, float yOffset, float partial, MatrixStack matrixStack, IRenderTypeBuffer buffer, int overlay) {
    matrixStack.push();
    ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
    int i = stack.isEmpty() ? 187 : Item.getIdFromItem(stack.getItem()) + stack.getDamage();
    random.setSeed(i);
    IBakedModel ibakedmodel = renderer.getItemModelWithOverrides(stack, world, null);
    boolean flag = ibakedmodel.isGui3d();
    float f1 = MathHelper.sin(((float) existedTicks + partial) / 10.0F) * 0.05F + 0.1F;
    float f3 = existedTicks + partial * 0.7F;
    matrixStack.translate(0.5D, f1 + yOffset, 0.5D);
    matrixStack.rotate(new Quaternion(0, -f3, 0, true));

    matrixStack.push();
    renderer.renderItem(stack, ItemCameraTransforms.TransformType.GROUND, WorldRenderer.getCombinedLight(world, pos.up()), overlay, matrixStack, buffer);
    matrixStack.pop();

    if (!flag) {
      matrixStack.translate(0.0, 0.0, 0.09375F);
    }

    matrixStack.pop();
  }


}
