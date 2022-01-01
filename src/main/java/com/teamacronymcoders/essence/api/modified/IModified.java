package com.teamacronymcoders.essence.api.modified;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;

import java.util.List;

public interface IModified {
    void addModifierWithoutIncreasingAdditional(int increase);

    void increaseFreeModifiers(int increase);

    boolean decreaseFreeModifiers(int decrease);

    int getFreeModifiers();

    int getMaxModifiers();

    boolean recheck(List<ModifierInstance> instances);

}
