package com.teamacronymcoders.essence.utils.config;

import com.teamacronymcoders.essence.utils.config.subconfigs.EssenceOreGenConfig;
import com.teamacronymcoders.essence.utils.config.subconfigs.EssenceTreeGenConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public class EssenceWorldGenConfig {

    private static EssenceWorldGenConfig instance;
    private final ForgeConfigSpec spec;

    private static EssenceOreGenConfig oreGenConfig;
    private static EssenceTreeGenConfig treeGenConfig;

    public EssenceWorldGenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("WorldGen");
        oreGenConfig = new EssenceOreGenConfig(builder);
        treeGenConfig = new EssenceTreeGenConfig(builder);
        builder.pop();
        this.spec = builder.build();
    }

    public static ForgeConfigSpec initialize() {
        EssenceWorldGenConfig config = new EssenceWorldGenConfig(new ForgeConfigSpec.Builder());
        instance = config;
        return config.getSpec();
    }

    public static EssenceWorldGenConfig getInstance() {
        return Objects.requireNonNull(instance, "Called for Worldgen Config before it's Initialization");
    }

    public ForgeConfigSpec getSpec() {
        return spec;
    }

    public static EssenceOreGenConfig getOreGenConfig() {
        return oreGenConfig;
    }

    public static EssenceTreeGenConfig getTreeGenConfig() {
        return treeGenConfig;
    }
}
