package com.teamacronymcoders.essence.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.teamacronymcoders.essence.entity.impl.sheared.ShearedPigEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;

public class ShearedPigSaddleLayer extends LayerRenderer<ShearedPigEntity, PigModel<ShearedPigEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final PigModel<PigEntity> pigModel = new PigModel<>(0.5F);

    public ShearedPigSaddleLayer(IEntityRenderer<ShearedPigEntity, PigModel<ShearedPigEntity>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, ShearedPigEntity shearedPigEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        if (shearedPigEntity.isHorseSaddled()) {
            ((PigModel)this.getEntityModel()).copyModelAttributesTo(this.pigModel);
            this.pigModel.setLivingAnimations(shearedPigEntity, v, v1, v2);
            this.pigModel.setRotationAngles(shearedPigEntity, v, v1, v3, v4, v5);
            IVertexBuilder vertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE));
            this.pigModel.render(matrixStack, vertexBuilder, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
