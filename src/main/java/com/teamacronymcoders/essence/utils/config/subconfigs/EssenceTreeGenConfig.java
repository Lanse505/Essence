package com.teamacronymcoders.essence.utils.config.subconfigs;

import com.teamacronymcoders.essence.utils.config.extendables.EssenceTreeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceTreeGenConfig {

    private final EssenceTreeConfig normalVariant;
    private final EssenceTreeConfig fancyVariant;

    public EssenceTreeGenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Trees");
        normalVariant = new EssenceTreeConfig(builder, "Normal", true, 3, 0, 0, 128);
        fancyVariant = new EssenceTreeConfig(builder, "Fancy", true, 1, 0, 0, 128);
        builder.pop();
    }

    public EssenceTreeConfig getNormalVariant() {
        return normalVariant;
    }

    public EssenceTreeConfig getFancyVariant() {
        return fancyVariant;
    }
}
