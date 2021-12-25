package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.sheared.ShearedChickenEntity;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ShearedChickenRenderer extends MobRenderer<ShearedChickenEntity, ChickenModel<ShearedChickenEntity>> {

  public ShearedChickenRenderer(EntityRendererProvider.Context context) {
    super(context, new ChickenModel<>(context.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
  }

  @Override
  public ResourceLocation getTextureLocation(ShearedChickenEntity entity) {
    return EssenceEntityTextures.SHEARED_CHICKEN;
  }

  protected float handleRotationFloat(ShearedChickenEntity entity, float v) {
    float lerp = Mth.lerp(v, entity.oFlap, entity.flap);
    float lerp1 = Mth.lerp(v, entity.oFlapSpeed, entity.flapSpeed);
    return (Mth.sin(lerp) + 1.0F) * lerp1;
  }
}
