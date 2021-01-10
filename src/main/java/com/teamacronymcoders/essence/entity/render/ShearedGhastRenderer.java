package com.teamacronymcoders.essence.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.entity.impl.sheared.ShearedGhastEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.util.ResourceLocation;

public class ShearedGhastRenderer extends MobRenderer<ShearedGhastEntity, GhastModel<ShearedGhastEntity>> {

  public ShearedGhastRenderer (EntityRendererManager manager) {
    super(manager, new GhastModel<>(), 1.5F);
  }

  @Override
  public ResourceLocation getEntityTexture (ShearedGhastEntity entity) {
    return entity.isAttacking() ? EssenceEntityTextures.SHEARED_GHAST_SHOOTING : EssenceEntityTextures.SHEARED_GHAST;
  }

  @Override
  protected void preRenderCallback (ShearedGhastEntity entity, MatrixStack stack, float v) {
    stack.scale(4.5F, 4.5F, 4.5F);
  }
}
