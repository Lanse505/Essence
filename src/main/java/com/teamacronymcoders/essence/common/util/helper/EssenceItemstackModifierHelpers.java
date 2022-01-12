package com.teamacronymcoders.essence.common.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.common.modifier.item.arrow.SoakedModifier;
import com.teamacronymcoders.essence.common.modifier.item.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.common.modifier.item.interaction.RainbowModifier;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EssenceItemstackModifierHelpers {
    public static final String HOLDER = "ModifierHolder";
    public static final String TAG_MODIFIERS = "ModifierInstances";

    /**
     * Used to get a Modifier from the registry using a string which gets converted to an ResourceLocation
     *
     * @param name The ResourceLocation name of the Modifier stored in NBT.
     * @return Returns the Modifier matching the ResourceLocation name from the Modifier Registry.
     */
    public static IModifier<?> getModifierByRegistryName(String name) {
        return EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(name));
    }

    /**
     * Checks if a modifier can be applied on a specific ItemStack as well as with a secondary modifier.
     *
     * @param input    The input Modifier to check against.
     * @param stack    The current ItemStack to be applied on.
     * @param modifier The secondary Modifier to check against.
     * @return Returns whether it can apply the Modifier.
     */
    public static boolean canApplyModifier(IModifier<ItemStack> input, ItemStack stack, IModifier<ItemStack> modifier) {
        if (input instanceof ItemCoreModifier) {
            return input.isCompatibleWith(stack, modifier) && input.canApplyOnObject(stack);
        }
        return input.isCompatibleWith(stack, modifier);
    }

    /**
     * Checks if a modifier can be applied on a specific ItemStack as well as with a set of secondary modifiers.
     *
     * @param input     The input Modifier to check against.
     * @param stack     The current ItemStack to be applied on.
     * @param modifiers The set of secondary Modifiers to check against.
     * @return Returns whether it can apply the Modifier.
     */
    public static boolean canApplyModifiers(IModifier<ItemStack> input, ItemStack stack, IModifier<ItemStack>[] modifiers) {
        if (input instanceof ItemCoreModifier) {
            return Arrays.stream(modifiers).allMatch(modifier -> input.isCompatibleWith(stack, modifier)) && Arrays.stream(modifiers).allMatch(modifier -> modifier.canApplyOnObject(stack));
        }
        return Arrays.stream(modifiers).allMatch(modifier -> input.isCompatibleWith(stack, modifier));
    }

    /**
     * Used to get a specific ModifierInstance using a modifier from an ItemStack.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to get the level off.
     * @return Returns the level of the modifier on the tool.
     */
    public static ModifierInstance getModifierInstance(ItemStack stack, IModifier<ItemStack> modifier) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> holder.deserializeNBT(stack.getOrCreateTag().getCompound(HOLDER)));
            return holderLazyOptional.map(holder -> Objects.requireNonNull(holder.getModifierInstances().stream().filter(instance -> instance.getModifier() == modifier).findAny().orElse(null))).orElse(null);
        }
        return null;
    }

    /**
     * Used to add an new Modifier to a specific ItemStack.
     *
     * @param stack        The ItemStack holding the Modifiers.
     * @param modifier     The new Modifier.
     * @param level        The level of the Modifier being applied.
     * @param modifierData Any additional data related to the modifier being applied.
     */
    public static void addModifier(ItemStack stack, IModifier<ItemStack> modifier, int level, CompoundTag modifierData) {
        final LazyOptional<ItemStackModifierHolder> instances = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModifiedItem && instances.isPresent()) {
            instances.ifPresent(holder -> {
                holder.addModifierInstance(false, stack, new ModifierInstance(() -> modifier, level, modifierData));
                stack.getTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, holder.serializeNBT());
            });
        }
    }

    public static void addModifier(ItemStack stack, ModifierInstance instance) {
        final LazyOptional<ItemStackModifierHolder> instances = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModifiedItem && instances.isPresent()) {
            instances.ifPresent(holder -> {
                holder.addModifierInstance(false, stack, instance);
                stack.getTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, holder.serializeNBT());
            });
        }
    }

    /**
     * This adds the specified Modifiers to the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifier to remove.
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static void addModifiers(ItemStack stack, IModifier<ItemStack>... modifiers) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModifiedItem && holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> {
                List<ModifierInstance> instances = new ArrayList<>(holder.getModifierInstances());
                boolean compatible = true;
                for (ModifierInstance instance : instances) {
                    boolean incompatible = false;
                    for (IModifier<ItemStack> modifier : modifiers) {
                        if (!instance.getModifier().get().isCompatibleWith(stack, modifier)) {
                            incompatible = true;
                            break;
                        }
                    }
                    if (incompatible) {
                        compatible = false;
                        break;
                    }
                }
                if (compatible) {
                    for (IModifier<ItemStack> modifier : modifiers) {
                        if (instances.contains(new ModifierInstance(() -> modifier, 1, null))) {
                            addModifier(stack, modifier, 1, null);
                        }
                    }
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
    @SuppressWarnings("unchecked")
    public static void addModifiers(ItemStack stack, ModifierInstance... modifiers) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModifiedItem && holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> {
                List<ModifierInstance> instances = new ArrayList<>(holder.getModifierInstances());
                boolean compatible = true;
                for (ModifierInstance instance : instances) {
                    boolean incompatible = false;
                    for (ModifierInstance modifier : modifiers) {
                            if (!instance.getModifier().get().isCompatibleWith(stack, modifier.getModifier().get())) {
                            incompatible = true;
                            break;
                        }
                    }
                    if (incompatible) {
                        compatible = false;
                        break;
                    }
                }
                if (compatible) {
                    for (ModifierInstance modifier : modifiers) {
                        if (!instances.contains(modifier)) {
                            addModifier(stack, modifier);
                        }
                    }
                }
            });
        }
    }


    /**
     * Used to remove a set of ModifierInstances using a set of modifiers as reference.
     *
     * @param stack             The ItemStack holding the Modifiers.
     * @param modifiersToRemove The Modifiers to remove.
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static void removeModifiers(ItemStack stack, IModifier<ItemStack>... modifiersToRemove) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.removeModifierInstance(false, stack, modifiersToRemove);
            holder.serializeNBT();
        });
    }

    /**
     * Used to set the modifier level of a specific modifier instance.
     *
     * @param stack       The ItemStack holding the Modifiers.
     * @param replacement The modifier to replace with.
     */
    public static void setModifierLevel(ItemStack stack, ModifierInstance replacement) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            for (ModifierInstance instance : holder.getModifierInstances()) {
                if (instance.getModifier() == replacement.getModifier() && instance.getModifierData() == replacement.getModifierData()) {
                    instance.setLevel(replacement.getLevel());
                    break;
                }
            }
            holder.serializeNBT();
        });
    }

    /**
     * Used to increase the level of a specific modifier instance.
     *
     * @param stack         The ItemStack holding the Modifiers.
     * @param checkInstance The modifier to increase with.
     */
    public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance) {
        increaseModifierLevel(stack, checkInstance, 1);
    }


    /**
     * Used to increase the level of a specific modifier instance.
     *
     * @param stack         The ItemStack holding the Modifiers.
     * @param checkInstance The modifier to increase with.
     * @param increase      The amount to increase by.
     */
    public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int increase) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.levelUpModifier(false, increase, stack, checkInstance);
            holder.serializeNBT();
        });
    }


    /**
     * Used to decrease the level of a specific modifier instance.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The modifier to decrease with.
     */
    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance modifier) {
        decreaseModifierLevel(stack, modifier, 1);
    }

    /**
     * Used to decrease the level of a specific modifier instance.
     *
     * @param stack         The ItemStack holding the Modifiers.
     * @param checkInstance The modifier to decrease with.
     * @param decrease      The amount to decrease by.
     */
    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int decrease) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.levelDownModifier(false, decrease, stack, checkInstance);
            holder.serializeNBT();
        });
    }

    /**
     * Used to merge two data compounds for a Modifier.
     *
     * @param stack         The ItemStack holding the Modifiers.
     * @param mergeInstance The modifier to merge with.
     */
    @SuppressWarnings("unchecked")
    public static void mergeModifierTags(ItemStack stack, ModifierInstance mergeInstance) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            for (ModifierInstance modifierInstance : holder.getModifierInstances()) {
                if (modifierInstance.getModifier().get().equals(mergeInstance.getModifier().get())) {
                    CompoundTag tagOnItem = modifierInstance.getModifierData();
                    modifierInstance.getModifier().get().mergeData(stack, tagOnItem, mergeInstance.getModifierData());
                    break;
                }
            }
            holder.serializeNBT();
        });
    }

    /**
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns a random modifier from the modifiers on the ItemStack
     */
    public static ModifierInstance getRandomModifierInstance(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        return Objects.requireNonNull(holderLazyOptional.map(holder -> holder.getModifierInstances().stream().skip(Essence.RANDOM.nextInt(holder.getModifierInstances().size())).findFirst()).orElse(null)).orElse(null);
    }

    /**
     * DO NOT USE THIS UNLESS YOU KNOW WHAT YOU'RE DOING!
     * I'm looking at you Future Simon >_>
     *
     * @param stack The ItemStack to be cleared of Modifiers
     */
    public static void clearModifiers(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(IModifierHolder::clearModifiers);
    }

    /**
     * Used to gather if a specific ItemStack currently has the RainbowModifier.
     *
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns whether the ItemStack has the RainbowModifier
     */
    public static boolean hasRainbowModifier(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier().get() instanceof RainbowModifier)).orElse(false);
    }

    /**
     * Used to gather if a specific ItemStack currently has the EnchantedModifier.
     *
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns whether the ItemStack has the EnchantedModifier
     */
    public static boolean hasEnchantedModifier(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier().get() instanceof EnchantedModifier)).orElse(false);
    }

    /**
     * Used to gather if a specific ItemStack currently has the SoakedModifier.
     *
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns whether the ItemStack has the SoakedModifier
     */
    public static boolean hasSoakedModifier(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier().get() instanceof SoakedModifier)).orElse(false);
    }

}
