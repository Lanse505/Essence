package com.teamacronymcoders.essence.api.modified.rewrite.itemstack;

import com.teamacronymcoders.essence.api.holder.ModifierHolder;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackModifierHolder extends ModifierHolder<ItemStack> {

    private final ItemStack stack;

    public ItemStackModifierHolder() {
        super();
        stack = ItemStack.EMPTY;
    }

    public ItemStackModifierHolder(ItemStack stack) {
        super();
        this.stack = stack;
    }

    @Override
    public CompoundTag serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tags) {
        if (stack.getTag() != null)
            super.deserializeNBT(stack.getTag().getCompound(EssenceItemstackModifierHelpers.TAG_MODIFIERS));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addModifierInstance(boolean simulate, ItemStack object, ModifierInstance... instances) {
        List<ModifierInstance> copy = new ArrayList<>(List.of(instances));
        for (ModifierInstance mi : getModifierInstances()) {
            for (ModifierInstance addition : instances) {
                if (mi.getModifier().get().equals(addition.getModifier().get())) {
                    copy.remove(addition);
                    mi.getModifier().get().mergeInstance(object, mi, addition);
                }
            }
        }
        if (!copy.isEmpty()) copy.forEach(instance -> getModifierInstances().add(instance));
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeModifierInstance(boolean simulate, ItemStack object, IModifier<ItemStack>... modifiers) {
        List<ModifierInstance> instancesToRemove = new ArrayList<>();
        for (ModifierInstance mi : getModifierInstances()) {
            for (IModifier<ItemStack> remover : modifiers) {
                if (mi.getModifier().get().equals(remover)) {
                    instancesToRemove.add(mi);
                }
            }
        }
        instancesToRemove.forEach(instance -> getModifierInstances().remove(instance));
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean levelUpModifier(boolean simulate, int increase, ItemStack object, IModifier<ItemStack>... modifiers) {
        for (ModifierInstance mi : getModifierInstances()) {
            for (IModifier<ItemStack> upper : modifiers) {
                if (mi.getModifier().get().equals(upper)) {
                    mi.setLevel(Math.min(mi.getLevel() + increase, mi.getModifier().get().getMaxLevel(object)));
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean levelUpModifier(boolean simulate, int increase, ItemStack object, ModifierInstance... modifiersWithData) {
        for (ModifierInstance mi : getModifierInstances()) {
            for (ModifierInstance upper : modifiersWithData) {
                if (mi.getModifier().get().equals(upper.getModifier().get())) {
                    mi.setLevel(Math.min(mi.getLevel() + increase, mi.getModifier().get().getMaxLevel(object)));
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean levelDownModifier(boolean simulate, int decrease, ItemStack object, IModifier<ItemStack>... modifiers) {
        for (ModifierInstance mi : getModifierInstances()) {
            for (IModifier<ItemStack> downer : modifiers) {
                if (mi.getModifier().get().equals(downer)) {
                    mi.setLevel(Math.max(mi.getLevel() - decrease, mi.getModifier().get().getMinLevel(object)));
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean levelDownModifier(boolean simulate, int decrease, ItemStack object, ModifierInstance... modifiersWithData) {
        for (ModifierInstance mi : getModifierInstances()) {
            for (ModifierInstance downer : modifiersWithData) {
                if (mi.getModifier().get().equals(downer.getModifier().get())) {
                    mi.setLevel(Math.max(mi.getLevel() - decrease, mi.getModifier().get().getMinLevel(object)));
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean levelSetModifier(boolean simulate, int level, ItemStack object, IModifier<ItemStack>... modifiers) {
        for (ModifierInstance mi : getModifierInstances()) {
            for (IModifier<ItemStack> setter : modifiers) {
                if (mi.getModifier().get().equals(setter)) {
                    mi.setLevel(Mth.clamp(level, mi.getModifier().get().getMinLevel(object), mi.getModifier().get().getMaxLevel(object)));
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean levelSetModifier(boolean simulate, int level, ItemStack object, ModifierInstance... modifiersWithData) {
        for (ModifierInstance mi : getModifierInstances()) {
            for (ModifierInstance setter : modifiersWithData) {
                if (mi.getModifier().get().equals(setter.getModifier().get())) {
                    mi.setLevel(Mth.clamp(level, mi.getModifier().get().getMinLevel(object), mi.getModifier().get().getMaxLevel(object)));
                }
            }
        }
        return true;
    }
}
