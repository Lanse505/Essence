package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.entity.impl.GlueBallEntity;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class GlueBallRenderer extends SpriteRenderer<GlueBallEntity> {
    public GlueBallRenderer(EntityRendererManager manager, ItemRenderer renderer) {
        super(manager, renderer, 0.75F, true);
    }
}
