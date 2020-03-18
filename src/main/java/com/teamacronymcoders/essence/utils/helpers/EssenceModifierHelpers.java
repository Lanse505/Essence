package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapabilities;
import com.teamacronymcoders.essence.api.tool.modifierholder.IModifierHolder;
import com.teamacronymcoders.essence.api.tool.modifierholder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.modifier.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.modifier.interaction.RainbowModifier;
import com.teamacronymcoders.essence.utils.registration.EssenceRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EssenceModifierHelpers {
    public static final String TAG_MODIFIERS = "ModifierInstances";

    /**
     * @param name The ResourceLocation name of the Modifier stored in NBT.
     * @return Returns the Modifier matching the ResourceLocation name from the Modifier Registry.
     */
    public static Modifier getModifierByRegistryName(String name) {
        return EssenceRegistries.MODIFIER_REGISTRY.getValue(new ResourceLocation(name));
    }

    public static boolean canApplyModifier(Modifier input, ItemStack stack, Modifier modifier) {
        return input.isCompatibleWith(modifier) && modifier.canApplyOnItemStack(stack);
    }

    public static boolean canApplyModifiers(Modifier input, ItemStack stack, Modifier[] modifiers) {
        return Arrays.stream(modifiers).allMatch(input::isCompatibleWith) && Arrays.stream(modifiers).allMatch(modifier -> modifier.canApplyOnItemStack(stack));
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to get the level off.
     * @return Returns the level of the modifier on the tool.
     */
    public static ModifierInstance getModifierInstance(ItemStack stack, Modifier modifier) {
        final LazyOptional<IModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        if (holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> holder.deserializeNBT(stack.getOrCreateTag().getCompound(TAG_MODIFIERS)));
            return holderLazyOptional.map(holder -> Objects.requireNonNull(holder.getModifierInstances().stream().filter(instance -> instance.getModifier() == modifier).findAny().orElse(null))).orElse(null);
        }
        return null;
    }

    public static void addModifier(ItemStack stack, Modifier modifier, int level, CompoundNBT modifierData) {
        final LazyOptional<IModifierHolder> instances = stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModifiedTool && instances.isPresent()) {
            instances.ifPresent(holder -> {
                if (modifier != null && holder.addModifierInstance(true, stack, new ModifierInstance(modifier, level, modifierData))) {
                    holder.addModifierInstance(false, stack, new ModifierInstance(modifier, level, modifierData));
                    stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
                }
            });
        }
    }

    /**
     * This adds the specified Modifiers to the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifier to remove.
     */
    public static void addModifiers(ItemStack stack, Modifier... modifiers) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModifiedTool && holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> {
                List<ModifierInstance> instances = holder.getModifierInstances();
                if (instances.stream().allMatch(instance -> Arrays.stream(modifiers).allMatch(modifier -> instance.getModifier().isCompatibleWith(modifier)))) {
                    Arrays.stream(modifiers)
                        .filter(modifier -> instances.contains(new ModifierInstance(modifier, 1, null)))
                        .forEach(modifier -> addModifier(stack, modifier, 1, null));
                }
            });
        }
    }


    public static void removeModifiers(ItemStack stack, Modifier... modifiersToRemove) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.getModifierInstances().stream().filter(instance -> Arrays.stream(modifiersToRemove).anyMatch(modifier -> instance.getModifier() == modifier)).forEach(instance -> {
                    if (holder.removeModifierInstance(true, stack, instance)) {
                        holder.removeModifierInstance(false, stack, instance);
                        stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
                    }
                });
        });
    }

    public static void setModifierLevel(ItemStack stack, ModifierInstance replacement) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.getModifierInstances().stream()
                .filter(modifierInstance -> modifierInstance.getModifier() == replacement.getModifier() && modifierInstance.getModifierData() == replacement.getModifierData())
                .findFirst()
                .ifPresent(modifierInstance -> modifierInstance.setLevel(replacement.getLevel()));
            stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
        });
    }

    public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance) {
        increaseModifierLevel(stack, checkInstance, 1);
    }


    @SuppressWarnings("unchecked")
    public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int increase) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            if (holder.levelUpModifier(true, stack, increase, checkInstance)) {
                holder.levelUpModifier(false, stack, increase, checkInstance);
                stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
            }
        });
    }


    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance modifier) {
        decreaseModifierLevel(stack, modifier, 1);
    }


    @SuppressWarnings("unchecked")
    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int decrease) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            if (holder.levelDownModifier(true, stack, decrease, checkInstance)) {
                holder.levelDownModifier(false, stack, decrease, checkInstance);
                stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
            }
        });
    }

    /**
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns an random modifier from the modifiers on the ItemStack
     */
    public static ModifierInstance getRandomModifierInstance(ItemStack stack) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().skip(Essence.RANDOM.nextInt(holder.getModifierInstances().size())).findFirst()).orElse(null).orElse(null);
    }

    /**
     * DO NOT USE THIS UNLESS YOU KNOW WHAT YOU'RE DOING!
     * I'm looking at you Future Simon >_>
     *
     * @param stack The ItemStack to be cleared of Modifiers
     */
    public static void clearModifiers(ItemStack stack) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(IModifierHolder::clearModifiers);
    }

    public static boolean hasEnchantedModifier(ItemStack stack) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof EnchantedModifier)).orElse(false);
    }

    public static boolean hasRainbowModifier(ItemStack stack) {
        final LazyOptional<IModifierHolder> holderLazyOptional= stack.getCapability(EssenceCapabilities.MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof RainbowModifier)).orElse(false);
    }
}
