package com.teamacronymcoders.essence.common.util.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceOreConfig {

    private final ForgeConfigSpec.BooleanValue shouldGenerate;
    private final ForgeConfigSpec.IntValue veinSize;
    private final ForgeConfigSpec.IntValue spawnTries;
    private final ForgeConfigSpec.IntValue minHeight;
    private final ForgeConfigSpec.IntValue maxHeight;

    public EssenceOreConfig(ForgeConfigSpec.Builder builder, String ore, boolean shouldGenerate, int veinSize, int spawnTries, int minHeight, int maxHeight) {
        builder.push(ore);
        builder.comment("Generation Settings for " + ore + " ore.");
        this.shouldGenerate = builder.comment("Determines if " + ore + " ore should be added to world generation.").define("shouldGenerate", shouldGenerate);
        this.veinSize = builder.comment("The size of the Ore Vein").defineInRange("veinSize", veinSize, 1, 100);
        this.spawnTries = builder.comment("The amount of times it will try to spawn the ore vein in an chunk").defineInRange("spawnTries", spawnTries, 1, 100);
        this.minHeight = builder.comment("The minimum height for the ore-vein to attempt to spawn").defineInRange("minHeight", minHeight, -64, 500);
        this.maxHeight = builder.comment("The maximum height for the ore-vein to attempt to spawn").defineInRange("maxHeight", maxHeight, -64, 500);
        builder.pop();
    }

    public ForgeConfigSpec.BooleanValue getShouldGenerate() {
        return shouldGenerate;
    }

    public ForgeConfigSpec.IntValue getVeinSize() {
        return veinSize;
    }

    public ForgeConfigSpec.IntValue getSpawnTries() {
        return spawnTries;
    }

    public ForgeConfigSpec.IntValue getMaxHeight() {
        return maxHeight;
    }

    public ForgeConfigSpec.IntValue getMinHeight() {
        return minHeight;
    }
}
