package com.teamacronymcoders.essence.util.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceGeneralConfig {

    private static EssenceGeneralConfig instance;
    private final ForgeConfigSpec spec;

    private final ForgeConfigSpec.BooleanValue enableDebugLogging;


    public EssenceGeneralConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        enableDebugLogging = builder.comment("Should Debug-Logging be Enabled?").define("enableDebugLogging", false);
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
}
