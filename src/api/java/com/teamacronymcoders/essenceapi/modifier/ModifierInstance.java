package com.teamacronymcoders.essenceapi.modifier;

import com.teamacronymcoders.essenceapi.EssenceRegistries;
import com.teamacronymcoders.essenceapi.helper.EssenceAPIItemstackModifierHelpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Supplier;

public class ModifierInstance implements INBTSerializable<CompoundTag> {

    public static final String TAG_MODIFIER = "Modifier";
    public static final String TAG_INFO = "ModifierInfo";
    public static final String TAG_LEVEL = "ModifierLevel";
    public static final String TAG_DATA = "ModifierData";

    private Supplier<IModifier> modifier;
    private int level;
    private CompoundTag modifierData = null;

    public ModifierInstance() {
    }

    public ModifierInstance(Supplier<IModifier> modifier) {
        this.modifier = modifier;
        this.level = 1;
    }

    public ModifierInstance(Supplier<IModifier> modifier, int level) {
        this.modifier = modifier;
        this.level = level;
    }

    public ModifierInstance(Supplier<IModifier> modifier, int level, CompoundTag modifierData) {
        this.modifier = modifier;
        this.level = level;
        this.modifierData = modifierData;
    }


    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag storage = new CompoundTag();
        storage.putString(TAG_MODIFIER, EssenceRegistries.MODIFIER.get().getKey(modifier.get()).toString());
        final CompoundTag info = new CompoundTag();
        info.putInt(TAG_LEVEL, this.level);
        if (modifierData != null) info.put(TAG_DATA, this.modifierData);
        storage.put(TAG_INFO, info);
        return storage;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final IModifier<?> modifier = EssenceAPIItemstackModifierHelpers.getModifierByRegistryName(nbt.getString(TAG_MODIFIER));
        this.modifier = () -> modifier;
        final CompoundTag info = nbt.getCompound(TAG_INFO);
        this.level = info.getInt(TAG_LEVEL);
        if (info.contains(TAG_DATA)) {
            this.modifierData = info.getCompound(TAG_DATA);
        }
        this.modifier.get().update(this.modifierData);
    }

    public Supplier<IModifier> getModifier() {
        return modifier;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CompoundTag getModifierData() {
        return modifierData;
    }

    public void setModifierData(CompoundTag modifierData) {
        this.modifierData = modifierData;
    }
}
