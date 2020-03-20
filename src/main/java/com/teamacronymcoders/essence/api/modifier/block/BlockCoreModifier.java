package com.teamacronymcoders.essence.api.modifier.block;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.block.Block;

public abstract class BlockCoreModifier extends Modifier<Block> {

    public BlockCoreModifier() {
        super(Block.class);
    }

    public BlockCoreModifier(int maxLevel) {
        super(Block.class, maxLevel);
    }

    public BlockCoreModifier(int maxLevel, int minLevel) {
        super(Block.class, maxLevel, minLevel);
    }

}
