package com.teamacronymcoders.essence.utils.helpers;

import com.google.common.collect.Lists;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.INBTModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.modifier.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.utils.registration.EssenceRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Stream;

public class EssenceModifierHelpers {
    public static final String TAG_MODIFIERS = "Modifiers";

    /**
     * @param name The ResourceLocation name of the Modifier stored in NBT.
     * @return Returns the Modifier matching the ResourceLocation name from the Modifier Registry.
     */
    public static Modifier getModifierByName(String name) {
        return EssenceRegistries.MODIFIER_REGISTRY.getValue(new ResourceLocation(name));
    }

    /**
     * @param stack The ItemStack to grab the Modifiers off.
     * @return Returns a map of all the Modifiers and their respective levels.
     */
    public static List<ModifierInstance> getModifiers(ItemStack stack) {
        final List<ModifierInstance> modifiers = Lists.newArrayList();
        final CompoundNBT compoundNBT = stack.getTag();
        if (compoundNBT != null) {
            final ListNBT list = compoundNBT.getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                final CompoundNBT subCNBT = list.getCompound(i);
                final ModifierInstance instance = new ModifierInstance();
                instance.deserializeNBT(subCNBT);
                modifiers.add(instance);
            }
        }
        return modifiers;
    }


    public static boolean canApplyModifier(Modifier input, ItemStack stack, Modifier modifier) {
        return input.isCompatibleWith(modifier) && modifier.canApplyOnItemStack(stack);
    }

    public static boolean canApplyModifiers(Modifier input, ItemStack stack, Modifier[] modifiers) {
        return Arrays.stream(modifiers).allMatch(input::isCompatibleWith) && Arrays.stream(modifiers).allMatch(modifier -> modifier.canApplyOnItemStack(stack));
    }

    /**
     * Sets the Converts the Map of Modifiers and Levels to NBT on the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Map of the Modifiers and their Levels on the Tool.
     */
    public static void setModifiersToNBT(ItemStack stack, List<ModifierInstance> modifiers) {
        final ListNBT list = new ListNBT();
        for (final ModifierInstance instance : modifiers) {
            list.add(instance.serializeNBT());
        }
        stack.getOrCreateTag().put(TAG_MODIFIERS, list);
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to get the level off.
     * @return Returns the level of the modifier on the tool.
     */
    public static ModifierInstance getModifierInstance(ItemStack stack, Modifier modifier) {
        final List<ModifierInstance> instances = getModifiers(stack);
        return instances.stream().filter(instance -> instance.getModifier() == modifier).findFirst().orElse(null);
    }

    public static void addModifier(ItemStack stack, Modifier modifier, int level, CompoundNBT modifierData) {
        final List<ModifierInstance> instances = getModifiers(stack);
        if (stack.getItem() instanceof IModifiedTool && instances.stream().allMatch(instance -> canApplyModifier(instance.getModifier(), stack, modifier))) {
            instances.add(new ModifierInstance(modifier, level, modifierData));
        }
    }

    /**
     * This adds the specified Modifiers to the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifier to remove.
     */
    public static void addModifiers(ItemStack stack, Modifier... modifiers) {
        final List<ModifierInstance> instances = getModifiers(stack);
        if (stack.getItem() instanceof IModifiedTool && instances.stream().allMatch(instance -> Arrays.stream(modifiers).allMatch(modifier -> instance.getModifier().isCompatibleWith(modifier)))) {
            Arrays.stream(modifiers)
                .filter(modifier -> instances.contains(new ModifierInstance(modifier, 1, null)))
                .forEach(modifier -> instances.add(new ModifierInstance(modifier, 1, null)));
        }
        setModifiersToNBT(stack, instances);
    }


    public static void removeModifiers(ItemStack stack, Modifier... modifiersToRemove) {
        final List<ModifierInstance> modifiers = getModifiers(stack);
        modifiers.stream()
            .filter(instance -> Arrays.stream(modifiersToRemove).anyMatch(modifier -> instance.getModifier() == modifier))
            .forEach(modifiers::remove);
        setModifiersToNBT(stack, modifiers);
    }

    public static void setModifierLevel(ItemStack stack, ModifierInstance replacement) {
        final List<ModifierInstance> instances = getModifiers(stack);
        instances.stream()
            .filter(instance -> instance.getModifier() == replacement.getModifier() && instance.getModifierData() == replacement.getModifierData())
            .findFirst()
            .ifPresent(instance -> instance.setLevel(replacement.getLevel()));
        setModifiersToNBT(stack, instances);
    }

    public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance) {
        increaseModifierLevel(stack, checkInstance, 1);
    }


    public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int increase) {
        final List<ModifierInstance> modifiers = getModifiers(stack);
        modifiers.stream()
            .filter(instance -> instance.getModifier() == checkInstance.getModifier() && instance.getModifierData() == checkInstance.getModifierData())
            .findFirst()
            .ifPresent(instance -> instance.setLevel(Math.min(instance.getLevel() + increase, instance.getModifier().getMaxLevel(stack))));
        setModifiersToNBT(stack, modifiers);
    }


    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance modifier) {
        decreaseModifierLevel(stack, modifier, 1);
    }


    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int decrease) {
        final List<ModifierInstance> modifiers = getModifiers(stack);
        modifiers.stream()
            .filter(instance -> instance.getModifier() == checkInstance.getModifier() && instance.getModifierData() == checkInstance.getModifierData())
            .findFirst()
            .ifPresent(instance -> instance.setLevel(Math.max(instance.getLevel() - decrease, instance.getModifier().getMinLevel(stack))));
        setModifiersToNBT(stack, modifiers);
    }

    /**
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns an random modifier from the modifiers on the ItemStack
     */
    public static ModifierInstance getRandomModifierInstance(ItemStack stack) {
        final List<ModifierInstance> modifiers = getModifiers(stack);
        return modifiers.stream().skip(Essence.RANDOM.nextInt(modifiers.size())).findFirst().orElse(null);
    }

    /**
     * DO NOT USE THIS UNLESS YOU KNOW WHAT YOU'RE DOING!
     * I'm looking at you Future Simon >_>
     *
     * @param stack The ItemStack to be cleared of Modifiers
     */
    public static void clearModifiers(ItemStack stack) {
        setModifiersToNBT(stack, Lists.newArrayList());
    }

    public static boolean hasEnchantedModifier(ItemStack stack) {
        final List<ModifierInstance> instances = getModifiers(stack);
        return instances.stream().anyMatch(instance -> instance.getModifier() instanceof EnchantedModifier);
    }
}
