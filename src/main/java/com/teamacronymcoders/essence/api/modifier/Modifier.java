package com.teamacronymcoders.essence.api.modifier;

import net.minecraft.nbt.CompoundTag;

public class Modifier<T> implements IModifier<T> {

    private final int maxLevel;
    private final int minLevel;

    public Modifier() {
        this.maxLevel = 1;
        this.minLevel = 0;
    }

    public Modifier(int maxLevel) {
        this.maxLevel = maxLevel;
        this.minLevel = 0;
    }

    public Modifier(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    public int getMinLevel(T target) {
        return minLevel;
    }

    @Override
    public int getMaxLevel(T target) {
        return maxLevel;
    }

    @Override
    public boolean countsTowardsLimit(T target, int level) {
        return true;
    }

    @Override
    public int getModifierCountValue(T target, int level) {
        return 1;
    }

    @Override
    public boolean isCompatibleWith(T target, IModifier<?> modifier) {
        return this != modifier;
    }

    @Override
    public boolean canApplyOnObject(T target) {
        return true;
    }

    @Override
    public void mergeData(T target, CompoundTag originalTag, CompoundTag mergeTag) {
    }

    @Override
    public void mergeInstance(T target, ModifierInstance originalInstance, ModifierInstance mergeInstance) {
    }

    @Override
    public void update(CompoundTag data) {
    }
}