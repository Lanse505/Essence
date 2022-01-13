package com.teamacronymcoders.essence.common.world.generation.ore;

import com.teamacronymcoders.essence.common.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.common.util.config.subconfigs.EssenceOreGenConfig;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class EssenceConfiguredFeatures {

    public final ConfiguredFeature<?, ?> ESSENCE_ORE;
    public final ConfiguredFeature<?, ?> ESSENCE_ORE_MIDDLE;
    public final ConfiguredFeature<?, ?> ESSENCE_ORE_SMALL;

    public final ConfiguredFeature<?, ?> ESSENCE_CRYSTAL_ORE;
    public final ConfiguredFeature<?, ?> ESSENCE_CRYSTAL_ORE_MIDDLE;
    public final ConfiguredFeature<?, ?> ESSENCE_CRYSTAL_ORE_SMALL;

    public final ConfiguredFeature<?, ?> ANCIENT_ENDERITE_LARGE;
    public final ConfiguredFeature<?, ?> ANCIENT_ENDERITE_SMALL;

    public EssenceConfiguredFeatures() {
        EssenceOreGenConfig oreGenConfig = EssenceWorldGenConfig.getOreGenConfig();
        ESSENCE_ORE = FeatureUtils.register("ore_essence",
                Feature.ORE.configured(
                        new OreConfiguration(EssenceOreConfigurations.ORE_ESSENCE_TARGET_LIST, oreGenConfig.getEssenceOreUpper().getVeinSize().get())
                ));
        ESSENCE_ORE_MIDDLE = FeatureUtils.register("ore_essence_middle",
                Feature.ORE.configured(
                        new OreConfiguration(EssenceOreConfigurations.ORE_ESSENCE_TARGET_LIST, oreGenConfig.getEssenceOreMiddle().getVeinSize().get())
                ));
        ESSENCE_ORE_SMALL = FeatureUtils.register("ore_essence_small",
                Feature.ORE.configured(
                        new OreConfiguration(EssenceOreConfigurations.ORE_ESSENCE_TARGET_LIST, oreGenConfig.getEssenceOreSmall().getVeinSize().get())
                ));
        ESSENCE_CRYSTAL_ORE = FeatureUtils.register("ore_essence_crystal",
                Feature.ORE.configured(
                        new OreConfiguration(EssenceOreConfigurations.ORE_ESSENCE_CRYSTAL_TARGET_LIST, oreGenConfig.getEssenceCrystalOreUpper().getVeinSize().get())
                ));
        ESSENCE_CRYSTAL_ORE_MIDDLE = FeatureUtils.register("ore_essence_crystal_middle",
                Feature.ORE.configured(
                        new OreConfiguration(EssenceOreConfigurations.ORE_ESSENCE_CRYSTAL_TARGET_LIST, oreGenConfig.getEssenceCrystalOreMiddle().getVeinSize().get())
                ));
        ESSENCE_CRYSTAL_ORE_SMALL = FeatureUtils.register("ore_essence_crystal_small",
                Feature.ORE.configured(
                        new OreConfiguration(EssenceOreConfigurations.ORE_ESSENCE_CRYSTAL_TARGET_LIST, oreGenConfig.getEssenceCrystalOreSmall().getVeinSize().get())
                ));
        ANCIENT_ENDERITE_LARGE = FeatureUtils.register("ancient_enderite_large",
                Feature.SCATTERED_ORE.configured(
                        new OreConfiguration(
                                EssenceOreConfigurations.ANCIENT_ENDERITE_TARGET_LIST.target,
                                EssenceOreConfigurations.ANCIENT_ENDERITE_TARGET_LIST.state,
                                oreGenConfig.getLargeAncientEnderite().getSize().get(),
                                oreGenConfig.getLargeAncientEnderite().getDiscardChanceOnAirExposure().get().floatValue())
                ));
        ANCIENT_ENDERITE_SMALL = FeatureUtils.register("ancient_enderite_small",
                Feature.SCATTERED_ORE.configured(
                        new OreConfiguration(
                                EssenceOreConfigurations.ANCIENT_ENDERITE_TARGET_LIST.target,
                                EssenceOreConfigurations.ANCIENT_ENDERITE_TARGET_LIST.state,
                                oreGenConfig.getSmallAncientEnderite().getSize().get(),
                                oreGenConfig.getSmallAncientEnderite().getDiscardChanceOnAirExposure().get().floatValue())
                ));
    }
}
