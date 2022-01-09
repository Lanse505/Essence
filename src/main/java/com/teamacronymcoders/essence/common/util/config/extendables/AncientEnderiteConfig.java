package com.teamacronymcoders.essence.common.util.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;

public class AncientEnderiteConfig {

    private final ForgeConfigSpec.BooleanValue shouldGenerate;
    private final ForgeConfigSpec.IntValue size;
    private final ForgeConfigSpec.DoubleValue discardChanceOnAirExposure;
    private ForgeConfigSpec.IntValue chanceInX;
    private ForgeConfigSpec.IntValue spawnTries;

    public AncientEnderiteConfig(ForgeConfigSpec.Builder builder, String prefix, boolean shouldGenerate, int size, double discardChance) {
        builder.push(prefix + " Ancient Enderite");
        builder.comment("Generation Settings for " + prefix + " Ancient Enderite.");
        this.shouldGenerate = builder.comment("Determines if " + prefix + " Ancient Enderite should generate").define("shouldGenerate", shouldGenerate);
        this.size = builder.comment("Determines the size of the" + prefix + " Ancient Enderite deposits in the End").defineInRange("size", size, 1, 100);
        this.discardChanceOnAirExposure = builder.comment("Determines the chance for the ore-generation to discard the ore if it's adjacent to air").defineInRange("discardChanceOnAirExposure", discardChance, 0.0, 1.0);
        if (prefix.equals("Large"))
            this.chanceInX = builder.comment("Determines the 1 in X chance to spawn the rarer large vein variant").defineInRange("chanceInX", 10, 1, 10000);
        if (prefix.equals("Small"))
            this.spawnTries = builder.comment("Determines the amount of tries it will try and spawn the default vein").defineInRange("spawnTries", 5, 1, 1000);
        builder.pop();
    }

    public ForgeConfigSpec.BooleanValue getShouldGenerate() {
        return shouldGenerate;
    }

    public ForgeConfigSpec.IntValue getSize() {
        return size;
    }

    public ForgeConfigSpec.DoubleValue getDiscardChanceOnAirExposure() {
        return discardChanceOnAirExposure;
    }

    @Nullable
    public ForgeConfigSpec.IntValue getChanceInX() {
        return chanceInX;
    }

    @Nullable
    public ForgeConfigSpec.IntValue getSpawnTries() {
        return spawnTries;
    }
}
