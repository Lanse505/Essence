package com.teamacronymcoders.essence.utils.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceOreConfig {

    private final ForgeConfigSpec.BooleanValue shouldGenerate;
    private final ForgeConfigSpec.IntValue perChunk;
    private final ForgeConfigSpec.IntValue maxVeinSize;
    private final ForgeConfigSpec.ConfigValue<Integer> bottomOffset;
    private final ForgeConfigSpec.ConfigValue<Integer> topOffset;
    private final ForgeConfigSpec.IntValue maxHeight;

    public EssenceOreConfig(ForgeConfigSpec.Builder builder, String ore, boolean shouldGenerate, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight) {
        builder.push(ore);
        builder.comment("Generation Settings for " + ore + " ore.");
        this.shouldGenerate = builder.comment("Determines if " + ore + " ore should be added to world generation.").define("shouldGenerate", shouldGenerate);
        //The max for perChunk and vein size are the values of the max number of blocks in a chunk.
        this.perChunk = builder.comment("Chance that " + ore + " generates in a chunk.").defineInRange("perChunk", perChunk, 1, 65536);
        this.maxVeinSize = builder.comment("Maximum number of blocks in a vein of " + ore + ".").defineInRange("maxVeinSize", maxVeinSize, 1, 65536);
        this.maxHeight = builder.comment("Base maximum height (exclusive) that veins of " + ore + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset").defineInRange("maxHeight", maxHeight, 1, 256);
        this.topOffset = builder.comment("Top offset for calculating height that veins of " + ore + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
            .define("topOffset", topOffset, value -> {
                if (value instanceof Integer) {
                    int val = (int) value;
                    return val >= 0 && val < this.maxHeight.get();
                }
                return false;
            });
        this.bottomOffset = builder.comment("Bottom offset for calculating height that veins of " + ore + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
            .define("bottomOffset", bottomOffset, value -> {
                if (value instanceof Integer) {
                    int val = (int) value;
                    return val >= 0 && val <= 256 - this.maxHeight.get() + this.topOffset.get();
                }
                return false;
            });
        builder.pop();
    }

    public ForgeConfigSpec.BooleanValue getShouldGenerate() {
        return shouldGenerate;
    }

    public ForgeConfigSpec.IntValue getPerChunk() {
        return perChunk;
    }

    public ForgeConfigSpec.IntValue getMaxVeinSize() {
        return maxVeinSize;
    }

    public ForgeConfigSpec.ConfigValue<Integer> getBottomOffset() {
        return bottomOffset;
    }

    public ForgeConfigSpec.ConfigValue<Integer> getTopOffset() {
        return topOffset;
    }

    public ForgeConfigSpec.IntValue getMaxHeight() {
        return maxHeight;
    }
}
