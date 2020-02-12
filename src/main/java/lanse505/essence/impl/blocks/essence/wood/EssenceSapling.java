package lanse505.essence.impl.blocks.essence.wood;

import lanse505.essence.base.core.CustomSaplingBlock;
import lanse505.essence.impl.generation.tree.feature.essence_tree.EssenceSaplingTree;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EssenceSapling extends CustomSaplingBlock {
    public EssenceSapling() {
        super(new EssenceSaplingTree(), Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT));
        setRegistryName(EssenceReferences.MODID, "essence_wood_sapling");
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
