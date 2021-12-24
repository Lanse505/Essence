package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModifiableArrowRenderer extends ArrowRenderer<ModifiableArrowEntity> {

  public ModifiableArrowRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public ResourceLocation getTextureLocation(ModifiableArrowEntity entity) {
    return entity.getColor() > 0 ? TippableArrowRenderer.TIPPED_ARROW_LOCATION : TippableArrowRenderer.NORMAL_ARROW_LOCATION;
  }

}
