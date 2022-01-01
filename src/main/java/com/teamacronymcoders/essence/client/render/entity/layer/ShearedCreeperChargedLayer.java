package com.teamacronymcoders.essence.client.render.entity.layer;

import com.teamacronymcoders.essence.common.entity.sheared.ShearedCreeperEntity;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class ShearedCreeperChargedLayer extends EnergySwirlLayer<ShearedCreeperEntity, CreeperModel<ShearedCreeperEntity>> {

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<ShearedCreeperEntity> creeperModel;

    public ShearedCreeperChargedLayer(RenderLayerParent<ShearedCreeperEntity, CreeperModel<ShearedCreeperEntity>> parent, EntityModelSet modelSet) {
        super(parent);
        creeperModel = new CreeperModel<ShearedCreeperEntity>(modelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    @Override
    protected float xOffset(float v) {
        return v * 0.01F;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return LIGHTNING_TEXTURE;
    }

    @Override
    protected EntityModel<ShearedCreeperEntity> model() {
        return this.creeperModel;
    }
}
