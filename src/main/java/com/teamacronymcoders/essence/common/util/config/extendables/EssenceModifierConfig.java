package com.teamacronymcoders.essence.common.util.config.extendables;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class EssenceModifierConfig {

  private final ForgeConfigSpec.Builder builder;
  public Map<String, ForgeConfigSpec.ConfigValue<?>> configValueMap;

  public EssenceModifierConfig(ForgeConfigSpec.Builder builder, String name) {
    this.builder = builder;
    this.configValueMap = new HashMap<>();
    builder.push(name);
  }

  public EssenceModifierConfig addIntRangeConfig(String comment, String fieldName, int min, int max, int value) {
    this.builder.comment(comment).defineInRange(fieldName, value, min, max);
    return this;
  }

  public EssenceModifierConfig addBooleanConfig(String comment, String fieldName, boolean value) {
    this.builder.comment(comment).define(fieldName, value);
    return this;
  }

  public EssenceModifierConfig addDoubleRangeConfig(String comment, String fieldName, double value, double min, double max) {
    this.builder.comment(comment).defineInRange(fieldName, value, min, max);
    return this;
  }

  public EssenceModifierConfig build() {
    builder.pop();
    return this;
  }

  public ForgeConfigSpec.ConfigValue<?> getConfigValue(String fieldName) {
    return this.configValueMap.get(fieldName);
  }

}
