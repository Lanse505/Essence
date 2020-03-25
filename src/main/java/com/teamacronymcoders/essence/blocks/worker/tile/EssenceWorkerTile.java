package com.teamacronymcoders.essence.blocks.worker.tile;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.teamacronymcoders.essence.blocks.worker.EssenceWorkerBlock;

import javax.annotation.Nonnull;

public abstract class EssenceWorkerTile<T extends ActiveTile<T>> extends ActiveTile<T> {

    public EssenceWorkerTile(BasicTileBlock<T> base) {
        super(base);
    }


}
