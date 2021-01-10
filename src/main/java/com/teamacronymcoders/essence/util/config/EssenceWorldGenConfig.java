package com.teamacronymcoders.essence.util.config;

import com.teamacronymcoders.essence.util.config.subconfigs.EssenceOreGenConfig;
import com.teamacronymcoders.essence.util.config.subconfigs.EssenceTreeGenConfig;
import java.util.Objects;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceWorldGenConfig {

  private static EssenceWorldGenConfig instance;
  private static EssenceOreGenConfig oreGenConfig;
  private static EssenceTreeGenConfig treeGenConfig;
  private final ForgeConfigSpec spec;

  public EssenceWorldGenConfig (ForgeConfigSpec.Builder builder) {
    builder.push("WorldGen");
    oreGenConfig = new EssenceOreGenConfig(builder);
    treeGenConfig = new EssenceTreeGenConfig(builder);
    builder.pop();
    this.spec = builder.build();
  }

  public static ForgeConfigSpec initialize () {
    EssenceWorldGenConfig config = new EssenceWorldGenConfig(new ForgeConfigSpec.Builder());
    instance = config;
    return config.getSpec();
  }

  public static EssenceWorldGenConfig getInstance () {
    return Objects.requireNonNull(instance, "Called for Worldgen Config before it's Initialization");
  }

  public static EssenceOreGenConfig getOreGenConfig () {
    return oreGenConfig;
  }

  public static EssenceTreeGenConfig getTreeGenConfig () {
    return treeGenConfig;
  }

  public ForgeConfigSpec getSpec () {
    return spec;
  }
}
