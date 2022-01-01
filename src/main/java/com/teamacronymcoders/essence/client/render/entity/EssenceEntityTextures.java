package com.teamacronymcoders.essence.client.render.entity;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EssenceEntityTextures {

    public static final ResourceLocation SHEARED_COW = new ResourceLocation(Essence.MOD_ID, "textures/entity/sheared_cow");
    public static final ModelLayerLocation SHEARED_COW_LAYER = createLocation("sheared_cow", "main");
    public static final ResourceLocation SHEARED_PIG = new ResourceLocation(Essence.MOD_ID, "textures/entity/sheared_pig/sheared_pig");
    public static final ModelLayerLocation SHEARED_PIG_LAYER = createLocation("sheared_pig", "main");
    public static final ResourceLocation SHEARED_CHICKEN = new ResourceLocation(Essence.MOD_ID, "textures/entity/sheared_chicken");
    public static final ModelLayerLocation SHEARED_CHICKEN_LAYER = createLocation("sheared_chicken", "main");
    public static final ResourceLocation SHEARED_CREEPER = new ResourceLocation(Essence.MOD_ID, "textures/entity/sheared_creeper");
    public static final ModelLayerLocation SHEARED_CREEPER_LAYER = createLocation("sheared_creeper", "main");
    public static final ResourceLocation SHEARED_GHAST = new ResourceLocation(Essence.MOD_ID, "textures/entity/sheared_ghast/sheared_ghast");
    public static final ModelLayerLocation SHEARED_GHAST_LAYER = createLocation("sheared_ghast", "main");
    public static final ResourceLocation SHEARED_GHAST_SHOOTING = new ResourceLocation(Essence.MOD_ID, "textures/entity/sheared_ghast/sheared_ghast_shooting");
    public static final ModelLayerLocation SHEARED_GHAST_SHOOTING_LAYER = createLocation("sheared_ghast_shooting", "main");

    private static ModelLayerLocation createLocation(String pPath, String pModel) {
        return new ModelLayerLocation(new ResourceLocation(Essence.MOD_ID, pPath), pModel);
    }

}
