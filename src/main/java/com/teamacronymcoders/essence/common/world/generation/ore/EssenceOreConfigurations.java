package com.teamacronymcoders.essence.common.world.generation.ore;

import com.google.common.collect.Lists;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

public class EssenceOreConfigurations {

    public static final RuleTest END_STONE_ORE_REPLACEABLES = new TagMatchTest(EssenceTags.EssenceBlockTags.END_STONE_REPLACEABLE);

    public static final List<OreConfiguration.TargetBlockState> ORE_ESSENCE_TARGET_LIST =
            Lists.newArrayList(OreConfiguration.target(STONE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_ORE.getDefaultState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_ORE_DEEP_SLATE.getDefaultState()));

    public static final List<OreConfiguration.TargetBlockState> ORE_ESSENCE_CRYSTAL_TARGET_LIST =
            Lists.newArrayList(
                    OreConfiguration.target(STONE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_CRYSTAL_ORE.getDefaultState()),
                    OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, EssenceBlockRegistrate.ESSENCE_CRYSTAL_ORE_DEEP_SLATE.getDefaultState())
            );

    public static final OreConfiguration.TargetBlockState ANCIENT_ENDERITE_TARGET_LIST = OreConfiguration.target(END_STONE_ORE_REPLACEABLES, EssenceBlockRegistrate.ANCIENT_ENDERITE.getDefaultState());
}
