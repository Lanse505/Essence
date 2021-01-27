package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.sheared.ShearedChickenEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ShearedChickenRenderer extends MobRenderer<ShearedChickenEntity, ChickenModel<ShearedChickenEntity>> {

  public ShearedChickenRenderer(EntityRendererManager manager) {
    super(manager, new ChickenModel<>(), 0.3F);
  }

  @Override
  public ResourceLocation getEntityTexture(ShearedChickenEntity entity) {
    return EssenceEntityTextures.SHEARED_CHICKEN;
  }

  protected float handleRotationFloat(ShearedChickenEntity entity, float v) {
    float lerp = MathHelper.lerp(v, entity.oFlap, entity.wingRotation);
    float lerp1 = MathHelper.lerp(v, entity.oFlapSpeed, entity.destPos);
    return (MathHelper.sin(lerp) + 1.0F) * lerp1;
  }
}
