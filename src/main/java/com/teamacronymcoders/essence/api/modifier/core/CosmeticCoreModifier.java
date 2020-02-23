package com.teamacronymcoders.essence.api.modifier.core;

import net.minecraft.item.ItemStack;

public class CosmeticCoreModifier extends CoreModifier {

    public CosmeticCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    @Override
    public boolean countsTowardsLimit(ItemStack stack, int level) {
        return false;
    }
}
