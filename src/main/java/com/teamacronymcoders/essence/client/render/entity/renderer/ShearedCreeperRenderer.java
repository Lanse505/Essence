package com.teamacronymcoders.essence.client.render.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.essence.client.render.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.client.render.entity.layer.ShearedCreeperChargedLayer;
import com.teamacronymcoders.essence.common.entity.sheared.ShearedCreeperEntity;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ShearedCreeperRenderer extends MobRenderer<ShearedCreeperEntity, CreeperModel<ShearedCreeperEntity>> {

    public ShearedCreeperRenderer(EntityRendererProvider.Context context) {
        super(context, new CreeperModel<>(context.bakeLayer(ModelLayers.CREEPER)), 0.5F);
        this.addLayer(new ShearedCreeperChargedLayer(this, context.getModelSet()));
    }

    @Override
    protected void scale(ShearedCreeperEntity entity, PoseStack poseStack, float v) {
        float intensity = entity.getSwelling(v);
        float v1 = 1.0F + Mth.sin(intensity * 100.0F) * intensity * 0.01F;
        intensity = Mth.clamp(intensity, 0.0F, 1.0F);
        intensity *= intensity;
        intensity *= intensity;
        float v2 = (1.0F + intensity * 0.4F) * v1;
        float v3 = (1.0F + intensity * 0.1F) / v1;
        poseStack.scale(v2, v3, v2);
    }

    @Override
    protected float getWhiteOverlayProgress(ShearedCreeperEntity entity, float v) {
        float intensity = entity.getSwelling(v);
        return (int) (intensity * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(intensity, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(ShearedCreeperEntity entity) {
        return EssenceEntityTextures.SHEARED_CREEPER;
    }
}
