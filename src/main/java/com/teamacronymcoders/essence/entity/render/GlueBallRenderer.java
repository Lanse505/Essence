package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.impl.GlueBallEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class GlueBallRenderer extends SpriteRenderer<GlueBallEntity> {

  public GlueBallRenderer (EntityRendererManager entityRendererManager) {
    super(entityRendererManager, Minecraft.getInstance().getItemRenderer(), 0.75f, true);
  }

}
