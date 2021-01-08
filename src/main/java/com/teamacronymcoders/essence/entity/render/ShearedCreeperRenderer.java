package com.teamacronymcoders.essence.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamacronymcoders.essence.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.entity.impl.sheared.ShearedCreeperEntity;
import com.teamacronymcoders.essence.entity.render.layer.ShearedCreeperChargedLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ShearedCreeperRenderer extends MobRenderer<ShearedCreeperEntity, CreeperModel<ShearedCreeperEntity>> {

    public ShearedCreeperRenderer(EntityRendererManager manager) {
        super(manager, new CreeperModel<>(), 0.5F);
        this.addLayer(new ShearedCreeperChargedLayer(this));
    }

    @Override
    protected void preRenderCallback(ShearedCreeperEntity entity, MatrixStack matrixStack, float v) {
        float intensity = entity.getCreeperFlashIntensity(v);
        float v1 = 1.0F + MathHelper.sin(intensity * 100.0F) * intensity * 0.01F;
        intensity = MathHelper.clamp(intensity, 0.0F, 1.0F);
        intensity *= intensity;
        intensity *= intensity;
        float v2 = (1.0F + intensity * 0.4F) * v1;
        float v3 = (1.0F + intensity * 0.1F) / v1;
        matrixStack.scale(v2, v3, v2);
    }

    @Override
    protected float getOverlayProgress(ShearedCreeperEntity entity, float v) {
        float intensity = entity.getCreeperFlashIntensity(v);
        return (int) (intensity * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(intensity, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(ShearedCreeperEntity entity) {
        return EssenceEntityTextures.SHEARED_CREEPER;
    }
}
