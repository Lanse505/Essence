package com.teamacronymcoders.essence.util.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceCascadingConfig {

  private ForgeConfigSpec.IntValue blockLimit;
  private ForgeConfigSpec.IntValue searchLimit;

  public EssenceCascadingConfig(ForgeConfigSpec.Builder builder, String type, int blockLimit, int searchLimit) {
    builder.push(type);
    this.blockLimit = builder.comment("Block Limit for Cascading[" + type + "]").defineInRange("blockLimit", blockLimit, 0, Integer.MAX_VALUE);
    this.searchLimit = builder.comment("Search Limit for Cascading[" + type + "]").defineInRange("searchLimit", searchLimit, 0, Integer.MAX_VALUE);
  }

  public ForgeConfigSpec.IntValue getBlockLimit() {
    return blockLimit;
  }

  public ForgeConfigSpec.IntValue getSearchLimit() {
    return searchLimit;
  }
}
