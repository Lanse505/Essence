package com.teamacronymcoders.essence.client.render.tesr.itemstack;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.item.wrench.SerializedEntityItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SerializableMobRenderer extends ItemStackTileEntityRenderer {

    @Override
    public void render(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        if (!stack.isEmpty() && stack.getItem() instanceof SerializedEntityItem) {

            Entity entity = SerializedEntityItem.getSerializedEntity(stack);
            if (entity != null) {
                renderEntityStatic(entity, matrix, buffer, light);
            }
        }
    }

    public <E extends Entity> void renderEntityStatic(E entity, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        Minecraft minecraft = Minecraft.getInstance();
        EntityRendererManager manager = minecraft.getRenderManager();
        EntityRenderer<? super E> renderer = manager.getRenderer(entity);

        Vec3d vec3d = renderer.getRenderOffset(entity, minecraft.getRenderPartialTicks());
        double x = 1.0f + vec3d.getX();
        double y = 1.0f + vec3d.getY();
        double z = 1.0f + vec3d.getZ();
        float yaw = 1.0f;

        matrix.push();
        matrix.translate(x, y, z);
        renderer.render(entity, yaw, minecraft.getRenderPartialTicks(), matrix, buffer, light);
        matrix.pop();

    }
}
