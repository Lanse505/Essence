package com.teamacronymcoders.essence.block.worker.tile;

import com.teamacronymcoders.essence.capability.block.BlockModifierHolder;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;

import javax.annotation.Nonnull;

public class EssenceFurnaceWorkerTile extends EssenceWorkerTile<EssenceFurnaceWorkerTile> {

    private final BlockModifierHolder modifierHolder;

    public EssenceFurnaceWorkerTile(BlockModifierHolder modifierHolder) {
        super(EssenceObjectHolders.FURNACE_WORKER_BLOCK);
        this.modifierHolder = modifierHolder;
    }

    @Nonnull
    @Override
    public EssenceFurnaceWorkerTile getSelf() {
        return this;
    }

}
