package com.teamacronymcoders.essenceapi.knowledge;

import com.teamacronymcoders.essenceapi.EssenceRegistries;
import com.teamacronymcoders.essenceapi.modifier.ModifierInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knowledge implements INBTSerializable<CompoundTag> {
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
        return "com.teamacronymcoders.essenceapi.knowledge." + EssenceRegistries.KNOWLEDGE.get().getKey(this).getNamespace() + "." + EssenceRegistries.KNOWLEDGE.get().getKey(this).getPath();
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag nbt = new CompoundTag();
        nbt.putString(TAG_KNOWLEDGE, EssenceRegistries.KNOWLEDGE.get().getKey(this).toString());
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