package com.teamacronymcoders.essence.util.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceTreeConfig {

  private final ForgeConfigSpec.BooleanValue shouldGenerate;
  private final ForgeConfigSpec.DoubleValue extraChance;
  private final ForgeConfigSpec.IntValue extraCount;

  public EssenceTreeConfig(ForgeConfigSpec.Builder builder, String variant, boolean shouldGenerate, float extraChance, int extraCount) {
    builder.push(variant);
    builder.comment("Generation Settings for Essence Tree variant: " + variant + ".");
    this.shouldGenerate = builder.comment("Determines if " + variant + " variant should be added to world generation.").define("shouldGenerate", shouldGenerate);
    this.extraChance = builder.comment("Chance that " + variant + " variant generates as a larger-sized variant").defineInRange("sizeChance", extraChance, 0, 1);
    this.extraCount = builder.comment("How many extra blocks taller " + variant + " variant should generate as when as a larger-sized variant").defineInRange("extraCount", extraCount, 0, 10);
    builder.pop();
  }

  public ForgeConfigSpec.BooleanValue getShouldGenerate() {
    return shouldGenerate;
  }

  public ForgeConfigSpec.DoubleValue getExtraChance() {
    return extraChance;
  }

  public ForgeConfigSpec.IntValue getExtraCount() {
    return extraCount;
  }
}
