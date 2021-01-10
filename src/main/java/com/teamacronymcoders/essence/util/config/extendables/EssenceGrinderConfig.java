package com.teamacronymcoders.essence.util.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceGrinderConfig {

  private final ForgeConfigSpec.ConfigValue<String> preferedMods;

  public EssenceGrinderConfig (ForgeConfigSpec.Builder builder, String preferedMods) {
    builder.push("Prefered Mods");
    this.preferedMods = builder.comment("Prefered Mod Outputs of Grinder").define("preferedMods", preferedMods);
  }

  public ForgeConfigSpec.ConfigValue<String> getPreferedMods () {
    return preferedMods;
  }
}
