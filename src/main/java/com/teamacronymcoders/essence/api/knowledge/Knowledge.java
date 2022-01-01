package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knowledge extends ForgeRegistryEntry<Knowledge> implements INBTSerializable<CompoundTag> {
    private static final String TAG_KNOWLEDGE = "Knowledge";
    private static final String TAG_MODIFIER_INSTANCES = "ModifierInstances";

    private List<ModifierInstance> modifierInstances;

    public Knowledge() {
        this.modifierInstances = new ArrayList<>();
    }

    public Knowledge(ModifierInstance... modifiers) {
        this.modifierInstances = new ArrayList<>();
        Collections.addAll(this.modifierInstances, modifiers);
    }

    public String getTranslationString() {
        return "knowledge." + getRegistryName().getNamespace() + "." + getRegistryName().getPath();
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag nbt = new CompoundTag();
        nbt.putString(TAG_KNOWLEDGE, getRegistryName().toString());
        final ListTag modifierInstances = new ListTag();
        for (ModifierInstance instance : this.modifierInstances) {
            modifierInstances.add(instance.serializeNBT());
        }
        nbt.put(TAG_MODIFIER_INSTANCES, modifierInstances);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final List<ModifierInstance> modifierInstances = new ArrayList<>();
        final ListTag listNBT = nbt.getList(TAG_MODIFIER_INSTANCES, Tag.TAG_COMPOUND);
        for (int i = 0; i < listNBT.size(); i++) {
            final CompoundTag subCNBT = listNBT.getCompound(i);
            final ModifierInstance instance = new ModifierInstance();
            instance.deserializeNBT(subCNBT);
            modifierInstances.add(instance);
        }
        this.modifierInstances = modifierInstances;
    }

}
