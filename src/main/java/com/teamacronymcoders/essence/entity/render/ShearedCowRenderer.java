package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.entity.impl.sheared.ShearedCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class ShearedCowRenderer extends MobRenderer<ShearedCowEntity, CowModel<ShearedCowEntity>> {

  public ShearedCowRenderer (EntityRendererManager manager) {
    super(manager, new CowModel<>(), 0.7F);
  }

  @Override
  public ResourceLocation getEntityTexture (ShearedCowEntity entity) {
    return EssenceEntityTextures.SHEARED_COW;
  }

}
