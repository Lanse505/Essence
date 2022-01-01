package com.teamacronymcoders.essence.api.holder;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Supplier;

public class ModifierInstance implements INBTSerializable<CompoundTag> {
    public static final String TAG_MODIFIER = "Modifier";
    public static final String TAG_INFO = "ModifierInfo";
    public static final String TAG_LEVEL = "ModifierLevel";
    public static final String TAG_COMPOUND = "ModifierCompound";

    private Supplier<Modifier> modifier;
    private int level;
    private CompoundTag modifierData;

    public ModifierInstance() {
    }

    public ModifierInstance(Supplier<Modifier> modifier, int level, CompoundTag modifierData) {
        this.modifier = modifier;
        this.level = level;
        this.modifierData = modifierData;
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putString(TAG_MODIFIER, modifier.get().getRegistryName().toString());
        final CompoundTag info = new CompoundTag();
        info.putInt(TAG_LEVEL, level);
        if (modifierData == null) {
            info.put(TAG_COMPOUND, new CompoundTag());
        } else {
            info.put(TAG_COMPOUND, modifierData);
        }
        compoundNBT.put(TAG_INFO, info);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final Modifier modifier = EssenceItemstackModifierHelpers.getModifierByRegistryName(nbt.getString(TAG_MODIFIER));
        final CompoundTag info = nbt.getCompound(TAG_INFO);
        final int level = info.getInt(TAG_LEVEL);
        final CompoundTag modifierData = info.getCompound(TAG_COMPOUND);

        this.modifier = () -> modifier;
        this.level = level;
        this.modifierData = modifierData;
        this.modifier.get().update(this.modifierData);
    }

    public Modifier getModifier() {
        return modifier.get();
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
