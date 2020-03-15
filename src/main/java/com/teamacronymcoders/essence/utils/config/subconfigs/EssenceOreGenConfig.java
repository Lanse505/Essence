package com.teamacronymcoders.essence.utils.config.subconfigs;

import com.teamacronymcoders.essence.utils.config.extendables.EssenceOreConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceOreGenConfig {

    private final EssenceOreConfig essence_ore;
    private final EssenceOreConfig essence_crystal_ore;

    public EssenceOreGenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Ores");
        essence_ore = new EssenceOreConfig(builder, "Essence", true, 24, 8, 0, 0, 128);
        essence_crystal_ore = new EssenceOreConfig(builder, "EssenceCrystal", true, 16, 4, 0, 0, 128);
        builder.pop();
    }

    public EssenceOreConfig getEssence_ore() {
        return essence_ore;
    }

    public EssenceOreConfig getEssence_crystal_ore() {
        return essence_crystal_ore;
    }
}
