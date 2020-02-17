package lanse505.essence.impl.items.essence;

import lanse505.essence.base.CustomSaplingBlock;
import lanse505.essence.impl.generation.tree.feature.essence_tree.EssenceTree;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EssenceSapling extends CustomSaplingBlock {
    public EssenceSapling() {
        super(new EssenceTree(), Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT));
        setRegistryName(EssenceReferences.MODID, "essence_wood_sapling");
        setItemGroup(EssenceReferences.CORE_TAB);
    }
}
