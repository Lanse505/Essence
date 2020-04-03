package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.entity.impl.sheared.ShearedPigEntity;
import com.teamacronymcoders.essence.entity.render.layer.ShearedPigSaddleLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class ShearedPigRenderer extends MobRenderer<ShearedPigEntity, PigModel<ShearedPigEntity>> {

    public ShearedPigRenderer(EntityRendererManager manager) {
        super(manager, new PigModel<>(), 0.7F);
        this.addLayer(new ShearedPigSaddleLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(ShearedPigEntity entity) {
        return EssenceEntityTextures.SHEARED_PIG;
    }
}
