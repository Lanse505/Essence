package com.teamacronymcoders.essence.util.config.subconfigs;

import com.teamacronymcoders.essence.util.config.extendables.EssenceTreeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceTreeGenConfig {

    private final EssenceTreeConfig normalVariant;
    private final EssenceTreeConfig fancyVariant;

    public EssenceTreeGenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Trees");
        normalVariant = new EssenceTreeConfig(builder, "Normal", true, 0.085f, 1);
        fancyVariant = new EssenceTreeConfig(builder, "Fancy", true, 0.085f, 1);
        builder.pop();
    }

    public EssenceTreeConfig getNormalVariant() {
        return normalVariant;
    }

    public EssenceTreeConfig getFancyVariant() {
        return fancyVariant;
    }
}
