package com.teamacronymcoders.essence.client.render.entity.renderer;

import com.teamacronymcoders.essence.common.entity.SerializedEntityEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class SerializedEntityRenderer extends ThrownItemRenderer<SerializedEntityEntity> {
    public SerializedEntityRenderer(EntityRendererProvider.Context context) {
        super(context, 0.75f, true);
    }
}
