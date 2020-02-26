package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public class InfusionPedestalBlock extends BasicTileBlock<InfusionPedestalTile> {

    public InfusionPedestalBlock() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3).sound(SoundType.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).variableOpacity().nonOpaque(), InfusionPedestalTile.class);
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_infusion_pedestal"));
        setItemGroup(EssenceReferences.CORE_TAB);
    }

    @Override
    public IFactory<InfusionPedestalTile> getTileEntityFactory() {
        return InfusionPedestalTile::new;
    }
}
