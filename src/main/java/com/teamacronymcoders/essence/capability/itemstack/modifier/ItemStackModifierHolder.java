package com.teamacronymcoders.essence.capability.itemstack.modifier;

import com.teamacronymcoders.essence.api.holder.ModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.List;

public class ItemStackModifierHolder extends ModifierHolder<ItemStack> {

    private ItemStack stack;

    public ItemStackModifierHolder() {
        super(ItemStack.class);
    }

    public ItemStackModifierHolder(ItemStack stack) {
        super(ItemStack.class);
        this.stack = stack;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean addModifierInstance(boolean simulate, ItemStack object, ModifierInstance<ItemStack>... instances) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            for (ModifierInstance<ItemStack> instance : instances) {
                if (instance.getModifier().getType() == getType()) {
                    if (!sim.contains(instance)) {
                        sim.add(instance);
                    }
                }
            }
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            for (ModifierInstance<ItemStack> instance : instances) {
                if (instance.getModifier().getType() == getType()) {
                    if (!getModifierInstances().contains(instance)) {
                        getModifierInstances().add(instance);
                        modified.decreaseFreeModifiers(instance.getModifier().getModifierCountValue(instance.getLevel(), object));
                    }
                }
            }
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean removeModifierInstance(boolean simulate, ItemStack object, ModifierInstance<ItemStack>... instances) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            Arrays.stream(instances).forEach(sim::remove);
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            int cmc = Arrays.stream(instances).map(instance -> getModifierInstances().stream()
                .filter(trueInstance -> trueInstance.getModifier().equals(instance.getModifier()) && trueInstance.getModifierData() == instance.getModifierData())
                .map(trueInstance -> {
                    if (instance.getModifier().getType() == getType()) {
                        getModifierInstances().remove(trueInstance);
                        return trueInstance.getModifier().getModifierCountValue(trueInstance.getLevel(), object);
                    }
                    return 0;
                }).reduce(0, Integer::sum)).reduce(0, Integer::sum);
            modified.addModifierWithoutIncreasingAdditional(cmc);
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean levelUpModifier(boolean simulate, ItemStack object, int increase, Modifier<ItemStack>... modifiers) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            sim.stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        Modifier<ItemStack> stackCoreModifier = instance.getModifier();
                        instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                    }
                });
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            getModifierInstances().stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        Modifier<ItemStack> stackCoreModifier = instance.getModifier();
                        int x = stackCoreModifier.getModifierCountValue(instance.getLevel(), object);
                        int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)), object);
                        instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                        if (x < y) {
                            modified.decreaseFreeModifiers(y - x);
                        }
                    }
                });
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean levelUpModifier(boolean simulate, ItemStack object, int increase, ModifierInstance<ItemStack>... modifiersWithData) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            sim.stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        Modifier<ItemStack> stackCoreModifier = instance.getModifier();
                        instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                    }
                });
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            getModifierInstances().stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        Modifier<ItemStack> stackCoreModifier = instance.getModifier();
                        int x = stackCoreModifier.getModifierCountValue(instance.getLevel(), object);
                        int y = stackCoreModifier.getModifierCountValue(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)), object);
                        instance.setLevel(Math.min(instance.getLevel() + increase, stackCoreModifier.getMaxLevel(object)));
                        if (x < y) {
                            modified.decreaseFreeModifiers(y - x);
                        }
                    }
                });
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean levelDownModifier(boolean simulate, ItemStack object, int decrease, Modifier<ItemStack>... modifiers) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            sim.stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        int level = instance.getLevel();
                        if (level - decrease < instance.getModifier().getMinLevel(object)) {
                            sim.remove(instance);
                            return;
                        }
                        instance.setLevel(instance.getLevel() - decrease);
                    }
                });
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            getModifierInstances().stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> modifier == instance.getModifier()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        int level = instance.getLevel();
                        if (level - decrease < instance.getModifier().getMinLevel(object)) {
                            removeModifierInstance(false, object, instance);
                            return;
                        }
                        int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease, object);
                        instance.setLevel(instance.getLevel() - decrease);
                        if (x > 0) {
                            modified.addModifierWithoutIncreasingAdditional(x);
                        }
                    }
                });
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean levelDownModifier(boolean simulate, ItemStack object, int decrease, ModifierInstance<ItemStack>... modifiersWithData) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            sim.stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        int level = instance.getLevel();
                        if (level - decrease < instance.getModifier().getMinLevel(object)) {
                            sim.remove(instance);
                            return;
                        }
                        instance.setLevel(instance.getLevel() - decrease);
                    }
                });
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            getModifierInstances().stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        int level = instance.getLevel();
                        if (level - decrease < instance.getModifier().getMinLevel(object)) {
                            removeModifierInstance(false, object, instance);
                            return;
                        }
                        int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(instance.getLevel() - decrease, object);
                        instance.setLevel(instance.getLevel() - decrease);
                        if (x > 0) {
                            modified.addModifierWithoutIncreasingAdditional(x);
                        }
                    }
                });
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean levelSetModifier(boolean simulate, ItemStack object, int level, Modifier<ItemStack>... modifiers) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            sim.stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        instance.setLevel(level);
                    }
                });
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            getModifierInstances().stream()
                .filter(instance -> Arrays.stream(modifiers).anyMatch(modifier -> instance.getModifier() == modifier))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(level, object);
                        instance.setLevel(level);
                        if (x > 0) {
                            modified.addModifierWithoutIncreasingAdditional(x);
                        }
                        if (x < 0) {
                            modified.decreaseFreeModifiers(x);
                        }
                    }
                });
            return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean levelSetModifier(boolean simulate, ItemStack object, int level, ModifierInstance<ItemStack>... modifiersWithData) {
        if (simulate && object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            List<ModifierInstance<ItemStack>> sim = getModifierInstances();
            sim.stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        instance.setLevel(level);
                    }
                });
            return modified.recheck(object, sim);
        }
        if (object.getItem() instanceof IModified && ((IModified) object.getItem()).getType() == getType()) {
            IModified<ItemStack> modified = (IModified<ItemStack>) object.getItem();
            getModifierInstances().stream()
                .filter(instance -> Arrays.stream(modifiersWithData).anyMatch(modifier -> instance.getModifier() == modifier.getModifier() && instance.getModifierData() == modifier.getModifierData()))
                .forEach(instance -> {
                    if (instance.getModifier().getType() == getType()) {
                        int x = instance.getModifier().getModifierCountValue(instance.getLevel(), object) - instance.getModifier().getModifierCountValue(level, object);
                        instance.setLevel(level);
                        if (x > 0) {
                            modified.addModifierWithoutIncreasingAdditional(x);
                        }
                        if (x < 0) {
                            modified.decreaseFreeModifiers(x);
                        }
                    }
                });
            return true;
        }
        return false;
    }

    @Override
    public ListNBT serializeNBT() {
        final ListNBT listNBT = new ListNBT();
        for (ModifierInstance<ItemStack> instance : getModifierInstances()) {
            listNBT.add(instance.serializeNBT());
        }
        stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, listNBT);
        return listNBT;
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        super.deserializeNBT(nbt.size() > 0 ? nbt : stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
    }
}
