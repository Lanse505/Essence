package com.teamacronymcoders.essence.util.config.subconfigs;

import com.teamacronymcoders.essence.util.config.extendables.EssenceOreConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceOreGenConfig {

  private final EssenceOreConfig essenceOre;
  private final EssenceOreConfig essenceCrystalOre;

  public EssenceOreGenConfig (ForgeConfigSpec.Builder builder) {
    builder.push("Ores");
    essenceOre = new EssenceOreConfig(builder, "Essence", true, 24, 8, 1, 1, 128);
    essenceCrystalOre = new EssenceOreConfig(builder, "Essence Crystal", true, 16, 4, 1, 1, 128);
    builder.pop();
  }

  public EssenceOreConfig getEssenceOre () {
    return essenceOre;
  }

  public EssenceOreConfig getEssenceCrystalOre () {
    return essenceCrystalOre;
  }
}
