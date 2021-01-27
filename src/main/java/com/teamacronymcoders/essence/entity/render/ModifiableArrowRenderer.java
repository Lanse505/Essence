package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.util.ResourceLocation;

public class ModifiableArrowRenderer extends ArrowRenderer<ModifiableArrowEntity> {

  public ModifiableArrowRenderer(EntityRendererManager renderManagerIn) {
    super(renderManagerIn);
  }

  @Override
  public ResourceLocation getEntityTexture(ModifiableArrowEntity entity) {
    return entity.getColor() > 0 ? TippedArrowRenderer.RES_TIPPED_ARROW : TippedArrowRenderer.RES_ARROW;
  }

}
