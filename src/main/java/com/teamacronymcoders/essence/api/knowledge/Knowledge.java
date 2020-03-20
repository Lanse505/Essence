package com.teamacronymcoders.essence.api.knowledge;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knowledge<T> extends ForgeRegistryEntry<Knowledge<?>> implements INBTSerializable<CompoundNBT> {
    private static final String TAG_KNOWLEDGE = "Knowledge";
    private static final String TAG_MODIFIER_INSTANCES = "ModifierInstances";

    private Class<T> type;
    private ResourceLocation identifier;
    private List<ModifierInstance<T>> modifierInstances;

    public Knowledge(Class<T> type) {
        this.type = type;
    }

    public Knowledge(Class<T> type, ResourceLocation identifier, ModifierInstance<T>... modifiers) {
        this.type = type;
        this.identifier = identifier;
        this.modifierInstances = new ArrayList<>();
        Collections.addAll(this.modifierInstances, modifiers);
    }

    public Knowledge() {
    }

    @Override
    public CompoundNBT serializeNBT() {
        final CompoundNBT nbt = new CompoundNBT();
        nbt.putString(TAG_KNOWLEDGE, identifier.toString());
        final ListNBT modifierInstances = new ListNBT();
        for (ModifierInstance<T> instance : this.modifierInstances) {
            modifierInstances.add(instance.serializeNBT());
        }
        nbt.put(TAG_MODIFIER_INSTANCES, modifierInstances);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        final ResourceLocation identifier = new ResourceLocation(nbt.getString(TAG_KNOWLEDGE));
        final List<ModifierInstance<T>> modifierInstances = new ArrayList<>();
        final ListNBT listNBT = nbt.getList(TAG_MODIFIER_INSTANCES, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < listNBT.size(); i++) {
            final CompoundNBT subCNBT = listNBT.getCompound(i);
            final ModifierInstance<T> instance = new ModifierInstance<>(type);
            instance.deserializeNBT(subCNBT);
            modifierInstances.add(instance);
        }
        this.identifier = identifier;
        this.modifierInstances = modifierInstances;
    }
}
