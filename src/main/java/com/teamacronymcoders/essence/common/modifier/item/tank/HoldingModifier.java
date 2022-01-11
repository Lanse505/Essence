package com.teamacronymcoders.essence.common.modifier.item.tank;

import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.world.item.ItemStack;

public class HoldingModifier extends ItemCoreModifier {

    public HoldingModifier() {
        super(3);
    }

    public int getTankSizeModifier(int level, int currentCapacity) {
        if (level == 1) {
            return (255532 - currentCapacity) / 2;
        }
        if (level == 2) {
            return (4554412 - currentCapacity) / 2;
        }

        if (level == 3) {
            return (2147483647 - currentCapacity) / 2;
        }
        return currentCapacity;
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return object.getItem() instanceof IModifiedItem;
    }

    @Override
    public boolean canApplyTogether(ItemStack stack, IModifier<ItemStack> modifier) {
        return !(modifier instanceof HoldingModifier);
    }
}
