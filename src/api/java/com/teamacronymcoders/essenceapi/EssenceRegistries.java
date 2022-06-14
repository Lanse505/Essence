package com.teamacronymcoders.essenceapi;

import com.teamacronymcoders.essenceapi.knowledge.Knowledge;
import com.teamacronymcoders.essenceapi.modifier.IModifier;
import com.teamacronymcoders.essenceapi.modifier.Modifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class EssenceRegistries {

  public static void init() {
  }



  static final DeferredRegister<Knowledge> DEFERRED_KNOWLEDGE = DeferredRegister.create(Keys.KNOWLEDGE, Keys.KNOWLEDGE.location().getNamespace());
  public static final Supplier<IForgeRegistry<Knowledge>> KNOWLEDGE = DEFERRED_KNOWLEDGE.makeRegistry(() -> new RegistryBuilder<Knowledge>()
          .setName(new ResourceLocation(EssenceAPI.MOD_ID, "knowledge"))
          .setIDRange(1, Integer.MAX_VALUE - 1)
          .disableSaving());

  static final DeferredRegister<IModifier<?>> DEFERRED_MODIFIER = DeferredRegister.create(Keys.MODIFIER, Keys.MODIFIER.location().getNamespace());
  public static final Supplier<IForgeRegistry<IModifier<?>>> MODIFIER = DEFERRED_MODIFIER.makeRegistry(() -> new RegistryBuilder<IModifier<?>>()
          .setName(new ResourceLocation(EssenceAPI.MOD_ID, "modifier"))
          .setIDRange(1, Integer.MAX_VALUE - 1)
          .disableSaving());

  public static final class Keys {
    public static final ResourceKey<Registry<Knowledge>> KNOWLEDGE = key("essence:knowledge");
    public static final ResourceKey<Registry<IModifier<?>>> MODIFIER = key("essence:modifier");

    private static <T> ResourceKey<Registry<T>> key(String name) {
      return ResourceKey.createRegistryKey(new ResourceLocation(name));
    }

    private static void init() {}
  }
}
