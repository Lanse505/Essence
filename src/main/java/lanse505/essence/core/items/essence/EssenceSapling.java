package lanse505.essence.core.items.essence;

import lanse505.essence.core.core.CustomSaplingBlock;
import lanse505.essence.core.generation.trees.feature.EssenceTreeFeature;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class EssenceSapling extends CustomSaplingBlock {
    public EssenceSapling(Tree treeIn) {
        super(treeIn, Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT));
        setRegistryName(EssenceReferences.MODID, "essence_wood_sapling");
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
