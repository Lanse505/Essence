package com.teamacronymcoders.essence.entity.render.layer;

import com.teamacronymcoders.essence.entity.impl.sheared.ShearedCreeperEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class ShearedCreeperChargedLayer extends EnergyLayer<ShearedCreeperEntity, CreeperModel<ShearedCreeperEntity>> {

  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
  private final CreeperModel<ShearedCreeperEntity> creeperModel = new CreeperModel<>(2.0F);

  public ShearedCreeperChargedLayer (IEntityRenderer<ShearedCreeperEntity, CreeperModel<ShearedCreeperEntity>> renderer) {
    super(renderer);
  }

  @Override
  protected float func_225634_a_ (float v) {
    return v * 0.01F;
  }

  @Override
  protected ResourceLocation func_225633_a_ () {
    return LIGHTNING_TEXTURE;
  }

  @Override
  protected EntityModel<ShearedCreeperEntity> func_225635_b_ () {
    return this.creeperModel;
  }
}
