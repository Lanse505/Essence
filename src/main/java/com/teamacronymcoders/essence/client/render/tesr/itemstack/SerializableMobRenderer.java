package com.teamacronymcoders.essence.client.render.tesr.itemstack;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.essence.item.wrench.SerializedEntityItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@OnlyIn(Dist.CLIENT)
public class SerializableMobRenderer extends BlockEntityWithoutLevelRenderer {

  public static Cache<UUID, LivingEntity> entityCache = CacheBuilder.newBuilder()
          .maximumSize(2048)
          .expireAfterAccess(10, TimeUnit.SECONDS)
          .build();

  public SerializableMobRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
    super(pBlockEntityRenderDispatcher, pEntityModelSet);
  }

  @Override
  public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    if (!stack.isEmpty() && stack.getItem() instanceof SerializedEntityItem && stack.getTag() != null && stack.getTag().contains("uuid")) {
      UUID uuid = stack.getTag().getUUID("uuid");
      LivingEntity entity = entityCache.getIfPresent(uuid);
      if (entity != null) {
        renderEntityStatic(entity, poseStack, buffer, combinedLight);
      } else if (!stack.getTag().getString("entity").equals(EntityType.getKey(EntityType.IRON_GOLEM).toString())) {
        LivingEntity nbtEntity = SerializedEntityItem.getEntityFromNBT(stack.getTag(), Minecraft.getInstance().level);
        if (nbtEntity != null) {
          entityCache.put(uuid, nbtEntity);
        }
      }
    }
  }

  public <E extends Entity> void renderEntityStatic(E entity, PoseStack poseStack, MultiBufferSource buffer, int light) {
    Minecraft minecraft = Minecraft.getInstance();
    EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
    EntityRenderer<? super E> renderer = dispatcher.getRenderer(entity);

    Vec3 vec3 = renderer.getRenderOffset(entity, minecraft.getDeltaFrameTime());
    double x = 1.0f + vec3.get(Direction.Axis.X);
    double y = 1.0f + vec3.get(Direction.Axis.Y);
    double z = 1.0f + vec3.get(Direction.Axis.Z);
    float yaw = 1.0f;

    poseStack.scale(0.35f, 0.35f, 0.35f);
    poseStack.translate(0.4, -0.5, 0.5);
    dispatcher.render(entity, x, y, z, yaw, minecraft.getDeltaFrameTime(), poseStack, buffer, light);
  }

}
