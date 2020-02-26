package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;

import javax.annotation.Nonnull;

public class InfusionPedestalTile extends ActiveTile<InfusionPedestalTile> {

    public InfusionPedestalTile() {
        super(EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL);
    }

    @Nonnull
    @Override
    public InfusionPedestalTile getSelf() {
        return this;
    }
}
