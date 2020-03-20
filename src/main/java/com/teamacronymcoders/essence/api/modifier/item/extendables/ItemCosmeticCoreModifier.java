package com.teamacronymcoders.essence.api.modifier_new.item.extendables;

import com.teamacronymcoders.essence.api.modifier_new.item.ItemCoreModifier;
import net.minecraft.item.ItemStack;

public abstract class ItemCosmeticCoreModifier extends ItemCoreModifier {

    public ItemCosmeticCoreModifier() {
        this(1);
    }

    public ItemCosmeticCoreModifier(int maxLevel) {
        this(maxLevel, 0);
    }

    public ItemCosmeticCoreModifier(int maxLevel, int minLevel) {
        super(maxLevel, minLevel);
    }

    @Override
    public boolean countsTowardsLimit(int level, ItemStack stack) {
        return false;
    }
}
