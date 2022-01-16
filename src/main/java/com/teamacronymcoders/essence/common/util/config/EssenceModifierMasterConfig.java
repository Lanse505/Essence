package com.teamacronymcoders.essence.common.util.config;

import com.teamacronymcoders.essence.common.util.config.extendables.EssenceCascadingConfig;
import com.teamacronymcoders.essence.common.util.config.extendables.EssenceModifierConfig;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceModifierMasterConfig {

    private static EssenceModifierMasterConfig instance;
    private final ForgeConfigSpec spec;

    private final EssenceCascadingConfig excavation;
    private final EssenceCascadingConfig lumber;
    private final EssenceCascadingConfig vein;

    private final EssenceModifierConfig lifesteal;

    public EssenceModifierMasterConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Modifiers");
        this.excavation = new EssenceCascadingConfig(builder, "Excavation", 125, 25);
        this.lumber = new EssenceCascadingConfig(builder, "Lumber", 125, 75);
        this.vein = new EssenceCascadingConfig(builder, "Vein", 125, 25);
        this.lifesteal = new EssenceModifierConfig(builder, "Lifesteal").addDoubleRangeConfig("Determines the lifesteal procentage for the modifier", "lifestealProcentage", 0.25d, 0.0d, 1.0d).build();
        builder.pop();
        this.spec = builder.build();
    }

    public static ForgeConfigSpec initialize() {
        EssenceModifierMasterConfig config = new EssenceModifierMasterConfig(new ForgeConfigSpec.Builder());
        instance = config;
        return config.getSpec();
    }

    public static EssenceModifierMasterConfig getInstance() {
        return instance;
    }

    public ForgeConfigSpec getSpec() {
        return spec;
    }

    public EssenceCascadingConfig getExcavation() {
        return excavation;
    }

    public EssenceCascadingConfig getLumber() {
        return lumber;
    }

    public EssenceCascadingConfig getVein() {
        return vein;
    }
}
