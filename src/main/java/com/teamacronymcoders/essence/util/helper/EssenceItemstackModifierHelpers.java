package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.modifier.item.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.RainbowModifier;
import com.teamacronymcoders.essence.util.registration.EssenceRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class EssenceItemstackModifierHelpers {
    public static final String TAG_MODIFIERS = "ModifierInstances";

    /**
     * @param name The ResourceLocation name of the Modifier stored in NBT.
     * @return Returns the Modifier matching the ResourceLocation name from the Modifier Registry.
     */
    public static Modifier<?> getModifierByRegistryName(String name) {
        return EssenceRegistries.MODIFIER.getValue(new ResourceLocation(name));
    }

    public static boolean canApplyModifier(Modifier<ItemStack> input, ItemStack stack, Modifier<ItemStack> modifier) {
        if (input instanceof ItemCoreModifier) {
            return input.isCompatibleWith(modifier) && input.canApplyOnObject(stack);
        }
        return input.isCompatibleWith(modifier);
    }

    public static boolean canApplyModifiers(Modifier<ItemStack> input, ItemStack stack, Modifier<ItemStack>[] modifiers) {
        if (input instanceof ItemCoreModifier) {
            return Arrays.stream(modifiers).allMatch(input::isCompatibleWith) && Arrays.stream(modifiers).allMatch(modifier -> modifier.canApplyOnObject(stack));
        }
        return Arrays.stream(modifiers).allMatch(input::isCompatibleWith);
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to get the level off.
     * @return Returns the level of the modifier on the tool.
     */
    public static ModifierInstance<ItemStack> getModifierInstance(ItemStack stack, Modifier<ItemStack> modifier) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> holder.deserializeNBT(stack.getOrCreateTag().getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND)));
            return holderLazyOptional.map(holder -> Objects.requireNonNull(holder.getModifierInstances().stream().filter(instance -> instance.getModifier() == modifier).findAny().orElse(null))).orElse(null);
        }
        return null;
    }

    public static void addModifier(ItemStack stack, Modifier<ItemStack> modifier, int level, CompoundNBT modifierData) {
        final LazyOptional<ItemStackModifierHolder> instances = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModified && ((IModified<ItemStack>) stack.getItem()).getType().isInstance(ItemStack.class) && instances.isPresent()) {
            instances.ifPresent(holder -> {
                if (modifier != null && holder.addModifierInstance(true, stack, new ModifierInstance<>(ItemStack.class, () -> modifier, level, modifierData))) {
                    holder.addModifierInstance(false, stack, new ModifierInstance<>(ItemStack.class, () -> modifier, level, modifierData));
                    if (modifier instanceof ItemCoreModifier) {
                        IModified<ItemStack> modified = (IModified<ItemStack>) stack.getItem();
                        ItemCoreModifier itemCoreModifier = (ItemCoreModifier) modifier;
                        modified.decreaseFreeModifiers(itemCoreModifier.getModifierCountValue(level, stack));
                    }
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
    public static void addModifiers(ItemStack stack, Modifier<ItemStack>... modifiers) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (stack.getItem() instanceof IModified && ((IModified<ItemStack>) stack.getItem()).getType().isInstance(ItemStack.class) && holderLazyOptional.isPresent()) {
            holderLazyOptional.ifPresent(holder -> {
                if (((ModifierHolder<?>) holder).getType() == ItemStack.class) {
                    if (holder.getModifierInstances().get(0).getType() == ItemStack.class) {
                        List<? extends ModifierInstance<?>> unchecked = holder.getModifierInstances();
                        List<ModifierInstance<ItemStack>> instances = new ArrayList<>();
                        for (ModifierInstance<?> instance : unchecked) {
                            if (instance.getType() == ItemStack.class) {
                                instances.add((ModifierInstance<ItemStack>) instance);
                            }
                        }
                        if (instances.stream().allMatch(instance -> Arrays.stream(modifiers).allMatch(modifier -> instance.getModifier().isCompatibleWith(modifier)))) {
                            Arrays.stream(modifiers)
                                .filter(modifier -> instances.contains(new ModifierInstance<>(ItemStack.class, () -> modifier, 1, null)))
                                .forEach(modifier -> addModifier(stack, modifier, 1, null));
                        }
                    }
                }
            });
        }
    }


    public static void removeModifiers(ItemStack stack, Modifier<ItemStack>... modifiersToRemove) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.getModifierInstances().stream().filter(instance -> Arrays.stream(modifiersToRemove).anyMatch(modifier -> instance.getModifier() == modifier)).forEach(instance -> {
                if (holder.removeModifierInstance(true, stack, instance)) {
                    holder.removeModifierInstance(false, stack, instance);
                    stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
                }
            });
        });
    }

    public static void setModifierLevel(ItemStack stack, ModifierInstance<ItemStack> replacement) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.getModifierInstances().stream()
                .filter(modifierInstance -> modifierInstance.getModifier() == replacement.getModifier() && modifierInstance.getModifierData() == replacement.getModifierData())
                .findFirst()
                .ifPresent(modifierInstance -> modifierInstance.setLevel(replacement.getLevel()));
            stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
        });
    }

    public static void increaseModifierLevel(ItemStack stack, ModifierInstance<ItemStack> checkInstance) {
        increaseModifierLevel(stack, checkInstance, 1);
    }


    @SuppressWarnings("unchecked")
    public static void increaseModifierLevel(ItemStack stack, ModifierInstance<ItemStack> checkInstance, int increase) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            if (holder.levelUpModifier(true, stack, increase, checkInstance)) {
                holder.levelUpModifier(false, stack, increase, checkInstance);
                stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
            }
        });
    }


    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance<ItemStack> modifier) {
        decreaseModifierLevel(stack, modifier, 1);
    }


    @SuppressWarnings("unchecked")
    public static void decreaseModifierLevel(ItemStack stack, ModifierInstance<ItemStack> checkInstance, int decrease) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            if (holder.levelDownModifier(true, stack, decrease, checkInstance)) {
                holder.levelDownModifier(false, stack, decrease, checkInstance);
                stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
            }
        });
    }

    public static void mergeModifierTags(ItemStack stack, ModifierInstance<ItemStack> mergeInstance) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(holder -> {
            holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() == mergeInstance.getModifier())
                .findFirst().ifPresent(instance -> {
                CompoundNBT tagOnItem = instance.getModifierData();
                tagOnItem.merge(mergeInstance.getModifierData());
                instance.setModifierData(tagOnItem);
            });
            stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
        });
    }

    /**
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns an random modifier from the modifiers on the ItemStack
     */
    public static ModifierInstance<ItemStack> getRandomModifierInstance(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().skip(Essence.RANDOM.nextInt(holder.getModifierInstances().size())).findFirst()).orElse(null).orElse(null);
    }

    /**
     * DO NOT USE THIS UNLESS YOU KNOW WHAT YOU'RE DOING!
     * I'm looking at you Future Simon >_>
     *
     * @param stack The ItemStack to be cleared of Modifiers
     */
    public static void clearModifiers(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        holderLazyOptional.ifPresent(IModifierHolder::clearModifiers);
    }

    public static boolean hasEnchantedModifier(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof EnchantedModifier)).orElse(false);
    }

    public static boolean hasRainbowModifier(ItemStack stack) {
        final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof RainbowModifier)).orElse(false);
    }

    /**
     * @param instances Modifier Instances
     * @return Returns CompoundNBT for Serialization/Deserialization
     * Do note that using this method DOES NOT add or remove from the free modifier amount.
     */
    public static CompoundNBT getStackNBTForFillGroup(ModifierInstance<ItemStack>... instances) {
        final CompoundNBT nbt = new CompoundNBT();
        final ListNBT listNBT = new ListNBT();
        for (ModifierInstance<ItemStack> instance : instances) {
            listNBT.add(instance.serializeNBT());
        }
        nbt.put(TAG_MODIFIERS, listNBT);
        return nbt;
    }
}
