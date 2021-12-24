package com.teamacronymcoders.essence.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamacronymcoders.essence.entity.sheared.ShearedPigEntity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ShearedPigSaddleLayer extends RenderLayer<ShearedPigEntity, PigModel<ShearedPigEntity>> {

  private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
  private final PigModel<ShearedPigEntity> pigModel;

  public ShearedPigSaddleLayer(RenderLayerParent<ShearedPigEntity, PigModel<ShearedPigEntity>> parent, EntityModelSet modelSet) {
    super(parent);
    pigModel = new PigModel<>(modelSet.bakeLayer(ModelLayers.PIG_SADDLE));
  }

  @Override
  public void render(PoseStack poseStack, MultiBufferSource source, int i, ShearedPigEntity shearedPigEntity, float v, float v1, float v2, float v3, float v4, float v5) {
    if (shearedPigEntity.isSaddled()) {
      this.getParentModel().copyPropertiesTo(this.pigModel);
      this.pigModel.prepareMobModel(shearedPigEntity, v, v1, v2);
      this.pigModel.setupAnim(shearedPigEntity, v, v1, v3, v4, v5);
      VertexConsumer vertexConsumer = source.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
      this.pigModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
  }
}
