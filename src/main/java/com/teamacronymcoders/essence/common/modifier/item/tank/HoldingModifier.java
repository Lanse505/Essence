package com.teamacronymcoders.essence.common.modifier.item.tank;

import com.teamacronymcoders.essence.api.modified.IModifiedTank;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import net.minecraft.world.item.ItemStack;

public class HoldingModifier extends ItemAttributeModifier {

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
        return object.getItem() instanceof IModifiedTank;
    }

    @Override
    public boolean canApplyTogether(IModifier modifier) {
        return !(modifier instanceof HoldingModifier);
    }
}
