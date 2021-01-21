package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceTags;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class EssenceFluidRegistrate {

  public static void init() {}

  public static final Registrate registrate = Essence.ESSENCE_REGISTRATE;

  // TODO: Look into adding overlays to Essence and Experience Fluids
  public static final RegistryEntry<ForgeFlowingFluid.Flowing> ESSENCE = registrate.fluid("essence", new ResourceLocation(Essence.MOD_ID, "block/fluid/essence_still"), new ResourceLocation(Essence.MOD_ID, "block/fluid/essence_flowing"))
          .lang(flowing -> "fluid.essence.essence_fluid", "Essence")
          .tag(EssenceTags.EssenceFluidTags.FORGE_ESSENCE, EssenceTags.EssenceFluidTags.MY_ESSENCE)
            .block().blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models().withExistingParent(context.getId().getPath(), new ResourceLocation(Essence.MOD_ID, "fluid/fluid")).texture("particle", new ResourceLocation(Essence.MOD_ID, "block/fluid/essence_still"))))
              .item().model((context, provider) -> provider.blockItem(context)).build()
            .build()
            .bucket().properties(properties -> properties.group(Essence.CORE_TAB).maxStackSize(16))
            .model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation(Essence.MOD_ID, "item/item")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/essence_bucket"))).build()
          .register();

  public static final RegistryEntry<ForgeFlowingFluid.Flowing> EXPERIENCE = registrate.fluid("experience", new ResourceLocation(Essence.MOD_ID, "block/fluid/experience_still"), new ResourceLocation(Essence.MOD_ID, "block/fluid/experience_flowing"))
          .attributes(builder -> builder.sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY))
          .lang(flowing -> "fluid.essence.experience_fluid", "Experience")
          .tag(EssenceTags.EssenceFluidTags.FORGE_EXPERIENCE, EssenceTags.EssenceFluidTags.MY_EXPERIENCE)
            .block().blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models().withExistingParent(context.getId().getPath(), new ResourceLocation(Essence.MOD_ID, "fluid/fluid")).texture("particle", new ResourceLocation(Essence.MOD_ID, "block/fluid/experience_still"))))
              .item().model((context, provider) -> provider.blockItem(context)).build()
            .build()
            .bucket().properties(properties -> properties.group(Essence.CORE_TAB).maxStackSize(16))
            .model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation(Essence.MOD_ID, "item/item")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/experience_bucket"))).build()
          .register();

}
