package com.teamacronymcoders.essence.client.render.entity.renderer;

import com.teamacronymcoders.essence.client.render.entity.EssenceEntityTextures;
import com.teamacronymcoders.essence.common.entity.sheared.ShearedCowEntity;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShearedCowRenderer extends MobRenderer<ShearedCowEntity, CowModel<ShearedCowEntity>> {

    public ShearedCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(ShearedCowEntity entity) {
        return EssenceEntityTextures.SHEARED_COW;
    }

}
