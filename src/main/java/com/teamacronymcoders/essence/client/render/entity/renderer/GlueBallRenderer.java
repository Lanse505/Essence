package com.teamacronymcoders.essence.client.render.entity.renderer;

import com.teamacronymcoders.essence.common.entity.GlueBallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class GlueBallRenderer extends ThrownItemRenderer<GlueBallEntity> {

    public GlueBallRenderer(EntityRendererProvider.Context context) {
        super(context, 0.75f, true);
    }

}
