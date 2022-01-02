package com.teamacronymcoders.essence.common.util.config.subconfigs;

import com.teamacronymcoders.essence.common.util.config.extendables.AncientEnderiteConfig;
import com.teamacronymcoders.essence.common.util.config.extendables.EssenceOreConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class EssenceOreGenConfig {

    private final EssenceOreConfig essenceOreUpper;
    private final EssenceOreConfig essenceOreMiddle;
    private final EssenceOreConfig essenceOreSmall;
    private final EssenceOreConfig essenceCrystalOreUpper;
    private final EssenceOreConfig essenceCrystalOreMiddle;
    private final EssenceOreConfig essenceCrystalOreSmall;
    private final AncientEnderiteConfig largeAncientEnderite;
    private final AncientEnderiteConfig smallAncientEnderite;

    public EssenceOreGenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Ores");
        essenceOreUpper = new EssenceOreConfig(builder, "Essence Ore [Upper]", true, 9, 90, 80, 384);
        essenceOreMiddle = new EssenceOreConfig(builder, "Essence Ore [Middle]", true, 9,10, -24, 56);
        essenceOreSmall = new EssenceOreConfig(builder, "Essence Ore [Small]", true, 4, 10, -64, 72);
        essenceCrystalOreUpper = new EssenceOreConfig(builder, "Essence Crystal Ore [Upper]", true, 8, 90, 80, 384);
        essenceCrystalOreMiddle = new EssenceOreConfig(builder, "Essence Crystal Ore [Middle]", true, 8, 10, -24, 56);
        essenceCrystalOreSmall = new EssenceOreConfig(builder, "Essence Crystal Ore [Small]", true, 4, 10, -64, 72);
        largeAncientEnderite = new AncientEnderiteConfig(builder, "Large", true, 8, 0.0);
        smallAncientEnderite = new AncientEnderiteConfig(builder, "Small", true, 4, 0.0);
        builder.pop();
    }

    public EssenceOreConfig getEssenceOreUpper() {
        return essenceOreUpper;
    }

    public EssenceOreConfig getEssenceOreMiddle() {
        return essenceOreMiddle;
    }

    public EssenceOreConfig getEssenceOreSmall() {
        return essenceOreSmall;
    }

    public EssenceOreConfig getEssenceCrystalOreUpper() {
        return essenceCrystalOreUpper;
    }

    public EssenceOreConfig getEssenceCrystalOreMiddle() {
        return essenceCrystalOreMiddle;
    }

    public EssenceOreConfig getEssenceCrystalOreSmall() {
        return essenceCrystalOreSmall;
    }

    public AncientEnderiteConfig getLargeAncientEnderite() {
        return largeAncientEnderite;
    }

    public AncientEnderiteConfig getSmallAncientEnderite() {
        return smallAncientEnderite;
    }
}
