package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.render.layer.ShearedPigSaddleLayer;
import com.teamacronymcoders.essence.entity.sheared.ShearedPigEntity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShearedPigRenderer extends MobRenderer<ShearedPigEntity, PigModel<ShearedPigEntity>> {

  public ShearedPigRenderer(EntityRendererProvider.Context context) {
    super(context, new PigModel<>(context.bakeLayer(EssenceEntityTextures.SHEARED_PIG_LAYER)), 0.7F);
    this.addLayer(new ShearedPigSaddleLayer(this, context.getModelSet()));
  }

  @Override
  public ResourceLocation getTextureLocation(ShearedPigEntity entity) {
    return EssenceEntityTextures.SHEARED_PIG;
  }
}
