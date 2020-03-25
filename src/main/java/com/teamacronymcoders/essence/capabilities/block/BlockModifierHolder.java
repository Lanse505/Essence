package com.teamacronymcoders.essence.core.capabilities.block;

import com.teamacronymcoders.essence.api.holder.ModifierHolder;
import net.minecraft.block.Block;

public class BlockModifierHolder extends ModifierHolder<Block> {
    public BlockModifierHolder() {
        super(Block.class);
    }
}
