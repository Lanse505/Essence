package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;

public abstract class ItemCosmeticCoreModifier extends ItemCoreModifier {

    public ItemCosmeticCoreModifier() {
        this(1);
    }

    public ItemCosmeticCoreModifier(int maxLevel) {
        this(maxLevel, 0);
    }

    public ItemCosmeticCoreModifier(int minLevel, int maxLevel) {
        super(minLevel, maxLevel);
    }

    @Override
    public boolean countsTowardsLimit(int level) {
        return false;
    }

    @Override
    public int getModifierCountValue(int level) {
        return 0;
    }
}
