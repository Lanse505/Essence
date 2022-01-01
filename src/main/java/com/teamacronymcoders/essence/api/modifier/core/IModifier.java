package com.teamacronymcoders.essence.api.modifier.core;

import net.minecraft.nbt.CompoundTag;

public interface IModifier {

    boolean canApplyTogether(IModifier modifier);

    void update(CompoundTag compoundNBT);

    /**
     * This returns a boolean check against both Modifiers not just this Modifier.
     *
     * @param modifier Modifier to check against.
     * @return Returns the final value if this can be applied together with the other Modifier.
     */
    default boolean isCompatibleWith(IModifier modifier) {
        return this.canApplyTogether(modifier) && modifier.canApplyTogether(this);
    }
}
