package com.teamacronymcoders.essenceapi.holder;

import com.teamacronymcoders.essenceapi.helper.EssenceAPIItemstackModifierHelpers;
import com.teamacronymcoders.essenceapi.modifier.ModifierInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ModifierHolder<T> implements IModifierHolder<T> {

    private final List<ModifierInstance> modifiers;

    public ModifierHolder() {
        modifiers = new ArrayList<>();
    }

    @NotNull
    @Override
    public List<ModifierInstance> getModifierInstances() {
        return modifiers;
    }

    @Override
    public void clearModifiers() {
        modifiers.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag primaryTag = new CompoundTag();
        final ListTag listNBT = new ListTag();
        for (ModifierInstance instance : modifiers) {
            listNBT.add(instance.serializeNBT());
        }
        primaryTag.put(EssenceAPIItemstackModifierHelpers.TAG_MODIFIERS, listNBT);
        return primaryTag;
    }

    @Override
    public void deserializeNBT(CompoundTag tags) {
        this.modifiers.clear();
        ListTag instances = tags.getList(EssenceAPIItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND);
        for (int i = 0; i < instances.size(); i++) {
            final CompoundTag compoundTag = instances.getCompound(i);
            final ModifierInstance instance = new ModifierInstance();
            instance.deserializeNBT(compoundTag);
            this.modifiers.add(instance);
        }
    }
}
