package com.teamacronymcoders.essence.client.model;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;

public class EssenceToolModifierLoader implements IModelLoader<EssenceToolModifierLoader.ToolModelGeometry> {
  public static final EssenceToolModifierLoader LOADER = new EssenceToolModifierLoader();


  @Override
  public ToolModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
    return null;
  }

  @Override
  public void onResourceManagerReload(ResourceManager pResourceManager) {

  }

  public static class ToolModelGeometry implements IModelGeometry<ToolModelGeometry> {

    private final Map<ItemCoreModifier, IBakedModifierModel> models = new HashMap<>();

    @Override
    public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
      return null;
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
      Set<Material> textures = Sets.newHashSet();

      return null;
    }
  }

}
