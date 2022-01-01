package com.teamacronymcoders.essence.client.render.entity.renderer;

import com.teamacronymcoders.essence.client.render.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.client.render.entity.layer.ShearedPigSaddleLayer;
import com.teamacronymcoders.essence.common.entity.sheared.ShearedPigEntity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShearedPigRenderer extends MobRenderer<ShearedPigEntity, PigModel<ShearedPigEntity>> {

    public ShearedPigRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.7F);
        this.addLayer(new ShearedPigSaddleLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(ShearedPigEntity entity) {
        return EssenceEntityTextures.SHEARED_PIG;
    }
}
