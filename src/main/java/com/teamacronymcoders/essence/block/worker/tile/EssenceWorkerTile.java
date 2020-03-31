package com.teamacronymcoders.essence.block.worker.tile;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;

public abstract class EssenceWorkerTile<T extends ActiveTile<T>> extends ActiveTile<T> {

    public EssenceWorkerTile(BasicTileBlock<T> base) {
        super(base);
    }


}
