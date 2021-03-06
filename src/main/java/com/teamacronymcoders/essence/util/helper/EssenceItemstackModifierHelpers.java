package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.modifier.item.arrow.SoakedModifier;
import com.teamacronymcoders.essence.modifier.item.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.RainbowModifier;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

@SuppressWarnings("unchecked")
public class EssenceItemstackModifierHelpers {
  public static final String TAG_MODIFIERS = "ModifierInstances";

  /**
   * @param name The ResourceLocation name of the Modifier stored in NBT.
   * @return Returns the Modifier matching the ResourceLocation name from the Modifier Registry.
   */
  public static Modifier getModifierByRegistryName(String name) {
    return EssenceModifierRegistrate.REGISTRY.get().getValue(new ResourceLocation(name));
  }

  public static boolean canApplyModifier(Modifier input, ItemStack stack, Modifier modifier) {
    if (input instanceof ItemCoreModifier) {
      return input.isCompatibleWith(modifier) && ((ItemCoreModifier) input).canApplyOnObject(stack);
    }
    return input.isCompatibleWith(modifier);
  }

  public static boolean canApplyModifiers(Modifier input, ItemStack stack, Modifier[] modifiers) {
    if (input instanceof ItemCoreModifier) {
      return Arrays.stream(modifiers).allMatch(input::isCompatibleWith) && Arrays.stream(modifiers).allMatch(modifier -> ((ItemCoreModifier) modifier).canApplyOnObject(stack));
    }
    return Arrays.stream(modifiers).allMatch(input::isCompatibleWith);
  }

  /**
   * @param stack    The ItemStack holding the Modifiers.
   * @param modifier The Modifier to get the level off.
   * @return Returns the level of the modifier on the tool.
   */
  public static ModifierInstance getModifierInstance(ItemStack stack, Modifier modifier) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (holderLazyOptional.isPresent()) {
      holderLazyOptional.ifPresent(holder -> holder.deserializeNBT(stack.getOrCreateTag().getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND)));
      return holderLazyOptional.map(holder -> Objects.requireNonNull(holder.getModifierInstances().stream().filter(instance -> instance.getModifier() == modifier).findAny().orElse(null))).orElse(null);
    }
    return null;
  }

  public static void addModifier(ItemStack stack, Modifier modifier, int level, CompoundNBT modifierData) {
    final LazyOptional<ItemStackModifierHolder> instances = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (stack.getItem() instanceof IModified && instances.isPresent()) {
      instances.ifPresent(holder -> {
        if (modifier != null && holder.addModifierInstance(true, stack, new ModifierInstance(() -> modifier, level, modifierData))) {
          holder.addModifierInstance(false, stack, new ModifierInstance(() -> modifier, level, modifierData));
          if (modifier instanceof ItemCoreModifier) {
            IModified modified = (IModified) stack.getItem();
            ItemCoreModifier itemCoreModifier = (ItemCoreModifier) modifier;
            modified.decreaseFreeModifiers(itemCoreModifier.getModifierCountValue(level));
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
  public static void addModifiers(ItemStack stack, Modifier... modifiers) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (stack.getItem() instanceof IModified && holderLazyOptional.isPresent()) {
      holderLazyOptional.ifPresent(holder -> {
        List<ModifierInstance> instances = new ArrayList<>(holder.getModifierInstances());
        if (instances.stream().allMatch(instance -> Arrays.stream(modifiers).allMatch(modifier -> instance.getModifier().isCompatibleWith(modifier)))) {
          Arrays.stream(modifiers).filter(modifier -> instances.contains(new ModifierInstance(() -> modifier, 1, null))).forEach(modifier -> addModifier(stack, modifier, 1, null));
        }
      });
    }
  }


  public static void removeModifiers(ItemStack stack, Modifier... modifiersToRemove) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    holderLazyOptional.ifPresent(holder -> {
      holder.getModifierInstances().stream().filter(instance -> Arrays.stream(modifiersToRemove).anyMatch(modifier -> instance.getModifier() == modifier)).forEach(instance -> {
        if (holder.removeModifierInstance(true, stack, instance.getModifier())) {
          holder.removeModifierInstance(false, stack, instance.getModifier());
          stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
        }
      });
    });
  }

  public static void setModifierLevel(ItemStack stack, ModifierInstance replacement) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
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


  public static void increaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int increase) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    holderLazyOptional.ifPresent(holder -> {
      if (holder.levelUpModifier(true, increase, stack, checkInstance)) {
        holder.levelUpModifier(false, increase, stack, checkInstance);
        stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
      }
    });
  }


  public static void decreaseModifierLevel(ItemStack stack, ModifierInstance modifier) {
    decreaseModifierLevel(stack, modifier, 1);
  }

  public static void decreaseModifierLevel(ItemStack stack, ModifierInstance checkInstance, int decrease) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    holderLazyOptional.ifPresent(holder -> {
      if (holder.levelDownModifier(true, decrease, stack, checkInstance)) {
        holder.levelDownModifier(false, decrease, stack, checkInstance);
        stack.getOrCreateTag().put(TAG_MODIFIERS, holder.serializeNBT());
      }
    });
  }

  public static void mergeModifierTags(ItemStack stack, ModifierInstance mergeInstance) {
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
  public static ModifierInstance getRandomModifierInstance(ItemStack stack) {
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

  public static boolean hasRainbowModifier(ItemStack stack) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof RainbowModifier)).orElse(false);
  }

  public static boolean hasEnchantedModifier(ItemStack stack) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof EnchantedModifier)).orElse(false);
  }

  public static boolean hasSoakedModifier(ItemStack stack) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof SoakedModifier)).orElse(false);
  }

  /**
   * @param instances Modifier Instances
   * @return Returns CompoundNBT for Serialization/Deserialization
   * Do note that using this method DOES NOT add or remove from the free modifier amount.
   */
  public static CompoundNBT getStackNBTForFillGroup(ModifierInstance... instances) {
    final CompoundNBT nbt = new CompoundNBT();
    final ListNBT listNBT = new ListNBT();
    for (ModifierInstance instance : instances) {
      listNBT.add(instance.serializeNBT());
    }
    nbt.put(TAG_MODIFIERS, listNBT);
    return nbt;
  }
}
