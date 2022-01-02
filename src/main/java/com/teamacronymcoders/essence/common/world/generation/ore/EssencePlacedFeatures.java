package com.teamacronymcoders.essence.common.world.generation.ore;

import com.teamacronymcoders.essence.common.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.common.util.config.subconfigs.EssenceOreGenConfig;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class EssencePlacedFeatures {

    public final PlacedFeature ORE_ESSENCE_UPPER;
    public final PlacedFeature ORE_ESSENCE_MIDDLE;
    public final PlacedFeature ORE_ESSENCE_SMALL;

    public final PlacedFeature ORE_ESSENCE_CRYSTAL_UPPER;
    public final PlacedFeature ORE_ESSENCE_CRYSTAL_MIDDLE;
    public final PlacedFeature ORE_ESSENCE_CRYSTAL_SMALL;

    public final PlacedFeature ORE_ANCIENT_DEBRIS_LARGE;
    public final PlacedFeature ORE_ANCIENT_DEBRIS_SMALL;

    public EssencePlacedFeatures(EssenceConfiguredFeatures configured) {
        EssenceOreGenConfig oreGenConfig = EssenceWorldGenConfig.getOreGenConfig();
        ORE_ESSENCE_UPPER = PlacementUtils.register("ore_essence_upper", configured.ESSENCE_ORE.placed(
                OrePlacements.commonOrePlacement(
                        oreGenConfig.getEssenceOreUpper().getSpawnTries().get(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(oreGenConfig.getEssenceOreUpper().getMinHeight().get()),
                                VerticalAnchor.absolute(oreGenConfig.getEssenceOreUpper().getMaxHeight().get())
                        )
                )
        ));
        ORE_ESSENCE_MIDDLE = PlacementUtils.register("ore_essence_middle", configured.ESSENCE_ORE_MIDDLE.placed(
                OrePlacements.commonOrePlacement(
                        oreGenConfig.getEssenceOreMiddle().getSpawnTries().get(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(oreGenConfig.getEssenceOreMiddle().getMinHeight().get()),
                                VerticalAnchor.absolute(oreGenConfig.getEssenceOreMiddle().getMaxHeight().get())
                        )
                )
        ));
        ORE_ESSENCE_SMALL = PlacementUtils.register("ore_essence_small", configured.ESSENCE_ORE_SMALL.placed(
                OrePlacements.commonOrePlacement(
                        oreGenConfig.getEssenceOreSmall().getSpawnTries().get(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(oreGenConfig.getEssenceOreSmall().getMinHeight().get()),
                                VerticalAnchor.absolute(oreGenConfig.getEssenceOreSmall().getMaxHeight().get())
                        )
                )
        ));

        ORE_ESSENCE_CRYSTAL_UPPER = PlacementUtils.register("ore_essence_crystal_upper", configured.ESSENCE_CRYSTAL_ORE.placed(
                OrePlacements.commonOrePlacement(
                        oreGenConfig.getEssenceCrystalOreUpper().getSpawnTries().get(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(oreGenConfig.getEssenceCrystalOreUpper().getMinHeight().get()),
                                VerticalAnchor.absolute(oreGenConfig.getEssenceCrystalOreUpper().getMaxHeight().get())
                        )
                )
        ));
        ORE_ESSENCE_CRYSTAL_MIDDLE = PlacementUtils.register("ore_essence_crystal_middle", configured.ESSENCE_CRYSTAL_ORE_MIDDLE.placed(
                OrePlacements.commonOrePlacement(
                        oreGenConfig.getEssenceCrystalOreMiddle().getSpawnTries().get(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(oreGenConfig.getEssenceCrystalOreMiddle().getMinHeight().get()),
                                VerticalAnchor.absolute(oreGenConfig.getEssenceCrystalOreMiddle().getMaxHeight().get())
                        )
                )
        ));
        ORE_ESSENCE_CRYSTAL_SMALL = PlacementUtils.register("ore_essence_crystal_small", configured.ESSENCE_CRYSTAL_ORE_SMALL.placed(
                OrePlacements.commonOrePlacement(
                        oreGenConfig.getEssenceCrystalOreSmall().getSpawnTries().get(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(oreGenConfig.getEssenceCrystalOreSmall().getMinHeight().get()),
                                VerticalAnchor.absolute(oreGenConfig.getEssenceCrystalOreSmall().getMaxHeight().get())
                        )
                )
        ));

        int chance = oreGenConfig.getLargeAncientEnderite().getChanceInX() == null ? 10 : oreGenConfig.getLargeAncientEnderite().getChanceInX().get();
        int tries = oreGenConfig.getSmallAncientEnderite().getSpawnTries() == null ? 5 : oreGenConfig.getSmallAncientEnderite().getSpawnTries().get();
        ORE_ANCIENT_DEBRIS_LARGE = PlacementUtils.register("ancient_enderite_large", configured.ANCIENT_ENDERITE_LARGE.placed(
                OrePlacements.rareOrePlacement(chance, PlacementUtils.RANGE_8_8))
        );
        ORE_ANCIENT_DEBRIS_SMALL = PlacementUtils.register("ancient_enderite_small", configured.ANCIENT_ENDERITE_SMALL.placed(
                OrePlacements.commonOrePlacement(tries, PlacementUtils.RANGE_8_8))
        );

    }

}
