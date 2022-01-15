package com.teamacronymcoders.essence.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.BakedQuad;

public interface IBakedModifierModel {

  ImmutableList<BakedQuad> getQuads();
}
