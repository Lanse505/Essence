package com.teamacronymcoders.essence.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.essence.entity.sheared.ShearedGhastEntity;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShearedGhastRenderer extends MobRenderer<ShearedGhastEntity, GhastModel<ShearedGhastEntity>> {

  public ShearedGhastRenderer(EntityRendererProvider.Context context) {
    super(context, new GhastModel<>(context.bakeLayer(ModelLayers.GHAST)), 1.5F);
  }

  @Override
  public ResourceLocation getTextureLocation(ShearedGhastEntity entity) {
    return entity.isCharging() ? EssenceEntityTextures.SHEARED_GHAST_SHOOTING : EssenceEntityTextures.SHEARED_GHAST;
  }

  @Override
  protected void scale(ShearedGhastEntity entity, PoseStack poseStack, float v) {
    poseStack.scale(4.5F, 4.5F, 4.5F);
  }
}
