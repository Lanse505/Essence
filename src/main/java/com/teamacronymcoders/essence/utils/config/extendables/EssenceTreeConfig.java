package com.teamacronymcoders.essence.utils.config.extendables;

import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceTreeConfig {

    private final ForgeConfigSpec.BooleanValue shouldGenerate;
    private final ForgeConfigSpec.IntValue perChunk;
    private final ForgeConfigSpec.ConfigValue<Integer> bottomOffset;
    private final ForgeConfigSpec.ConfigValue<Integer> topOffset;
    private final ForgeConfigSpec.IntValue maxHeight;

    public EssenceTreeConfig(ForgeConfigSpec.Builder builder, String variant, boolean shouldGenerate, int perChunk, int bottomOffset, int topOffset, int maxHeight) {
        builder.push(variant);
        builder.comment("Generation Settings for Essence Tree variant: " + variant + ".");
        this.shouldGenerate = builder.comment("Determines if " + variant + " variant should be added to world generation.").define("shouldGenerate", shouldGenerate);
        this.perChunk = builder.comment("Chance that " + variant + " variant generates in a chunk.").defineInRange("perChunk", perChunk, 1, 65536);
        this.maxHeight = builder.comment("Base maximum height (exclusive) that tree of variant " + variant + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset").defineInRange("maxHeight", maxHeight, 1, 256);
        this.topOffset = builder.comment("Top offset for calculating height that trees of variant " + variant + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
            .define("topOffset", topOffset, value -> {
                if (value instanceof Integer) {
                    int val = (int) value;
                    return val >= 0 && val < this.maxHeight.get();
                }
                return false;
            });
        this.bottomOffset = builder.comment("Bottom offset for calculating height that trees of variant " + variant + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
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
