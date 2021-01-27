package com.teamacronymcoders.essence.util.config;

import com.teamacronymcoders.essence.item.wrench.config.BlockSerializationEnum;
import com.teamacronymcoders.essence.item.wrench.config.EntitySerializationEnum;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

public class EssenceGeneralConfig {

  private static EssenceGeneralConfig instance;
  private final ForgeConfigSpec spec;

  private final ForgeConfigSpec.BooleanValue enableDebugLogging;
  private final ForgeConfigSpec.EnumValue<BlockSerializationEnum> serializeBlock;
  private final ForgeConfigSpec.EnumValue<EntitySerializationEnum> serializeEntity;

  public EssenceGeneralConfig(ForgeConfigSpec.Builder builder) {
    builder.push("General");
    enableDebugLogging = builder.comment("Should Debug-Logging be Enabled?").define("enableDebugLogging", false);
    serializeBlock = builder.defineEnum("Should serialization be done on a Whitelist or Blacklist for Blocks?", BlockSerializationEnum.BLACKLIST);
    serializeEntity = builder.defineEnum("Should serialization be done on a Whitelist or Blacklist for Entities?", EntitySerializationEnum.BLACKLIST);
    builder.pop();
    this.spec = builder.build();
  }

  public static ForgeConfigSpec initialize() {
    EssenceGeneralConfig config = new EssenceGeneralConfig(new ForgeConfigSpec.Builder());
    instance = config;
    return config.getSpec();
  }

  public static EssenceGeneralConfig getInstance() {
    return instance;
  }

  public ForgeConfigSpec getSpec() {
    return spec;
  }

  public ForgeConfigSpec.BooleanValue getEnableDebugLogging() {
    return enableDebugLogging;
  }

  public EnumValue<BlockSerializationEnum> getSerializeBlock() {
    return serializeBlock;
  }

  public EnumValue<EntitySerializationEnum> getSerializeEntity() {
    return serializeEntity;
  }
}
