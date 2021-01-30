package com.teamacronymcoders.essence.client.render.tesr.itemstack;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.item.wrench.SerializedEntityItem;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SerializableMobRenderer extends ItemStackTileEntityRenderer {

  public static Cache<UUID, LivingEntity> entityCache = CacheBuilder.newBuilder()
          .maximumSize(2048)
          .expireAfterAccess(10, TimeUnit.SECONDS)
          .build();

  @Override
  public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transform, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
    if (!stack.isEmpty() && stack.getItem() instanceof SerializedEntityItem && stack.getTag() != null) {
      UUID uuid = stack.getTag().getUniqueId("uuid");
      LivingEntity entity = entityCache.getIfPresent(uuid);
      if (entity != null) {
        renderEntityStatic(entity, matrixStack, buffer, combinedLight);
      } else if (!stack.getTag().getString("entity").equals(EntityType.getKey(EntityType.IRON_GOLEM).toString())) {
        LivingEntity nbtEntity = SerializedEntityItem.getEntityFromNBT(stack.getTag(), Minecraft.getInstance().world);
        if (nbtEntity != null) {
          entityCache.put(uuid, nbtEntity);
        }
      }
    }
  }

  public <E extends Entity> void renderEntityStatic(E entity, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
    Minecraft minecraft = Minecraft.getInstance();
    EntityRendererManager manager = minecraft.getRenderManager();
    EntityRenderer<? super E> renderer = manager.getRenderer(entity);

    Vector3d vec3d = renderer.getRenderOffset(entity, minecraft.getRenderPartialTicks());
    double x = 1.0f + vec3d.getX();
    double y = 1.0f + vec3d.getY();
    double z = 1.0f + vec3d.getZ();
    float yaw = 1.0f;

    matrix.scale(0.35f, 0.35f, 0.35f);
    matrix.translate(0.4, -0.5, 0.5);
    manager.renderEntityStatic(entity, x, y, z, yaw, minecraft.getRenderPartialTicks(), matrix, buffer, light);
  }

}
