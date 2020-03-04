package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.core.INBTModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
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

    public static final IForgeRegistry<Modifier> MODIFIERS = GameRegistry.findRegistry(Modifier.class);

    public static final String TAG_MODIFIERS = "Modifiers";
    public static final String TAG_MODIFIER = "Modifier";
    public static final String TAG_INFO = "ModifierInfo";
    public static final String TAG_LEVEL = "ModifierLevel";
    public static final String TAG_COMPOUND = "ModifierCompound";

    /**
     * @param name The ResourceLocation name of the Modifier stored in NBT.
     * @return Returns the Modifier matching the ResourceLocation name from the Modifier Registry.
     */
    public static Modifier getModifierByName(String name) {
        return MODIFIERS.getValue(new ResourceLocation(name));
    }

    /**
     * @param stack The ItemStack to grab the Modifiers off.
     * @return Returns a map of all the Modifiers and their respective levels.
     */
    public static Map<Modifier, Pair<Integer, CompoundNBT>> getModifiers(ItemStack stack) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifiers = new HashMap<>();
        final CompoundNBT compoundNBT = stack.getTag();
        if (compoundNBT != null) {
            final ListNBT list = compoundNBT.getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                final CompoundNBT subCNBT = list.getCompound(i);
                final Modifier modifier = getModifierByName(subCNBT.getString(TAG_MODIFIER));
                if (modifier != null) {
                    final CompoundNBT nbt = subCNBT.getCompound(TAG_INFO);
                    if (modifier instanceof INBTModifier) {
                        ((INBTModifier) modifier).update(nbt.getCompound(TAG_COMPOUND));
                    }
                    modifiers.put(modifier, Pair.of(nbt.getInt(TAG_LEVEL), nbt.getCompound(TAG_COMPOUND)));
                }
            }
        }
        return modifiers;
    }

    /**
     * @param modifier_set The Set of Modifiers on the ItemStack.
     * @param modifier     The Modifier to be applied.
     * @return Returns if the Modifiers can be applied together with the Modifiers on the ItemStack.
     */
    public static boolean canApplyModifier(Set<Modifier> modifier_set, ItemStack stack, Modifier modifier) {
        return modifier_set.stream().allMatch(modifier::isCompatibleWith) && modifier.canApplyOnItemStack(stack);
    }

    /**
     * @param modifier_set The Set of Modifiers on the ItemStack.
     * @param modifiers    The Modifiers to be applied.
     * @return Returns if the Modifier can be applied together with the Modifiers on the ItemStack.
     */
    public static boolean canApplyModifiers(Set<Modifier> modifier_set, ItemStack stack, Modifier[] modifiers) {
        return modifier_set.stream().allMatch(modifier -> Arrays.stream(modifiers).allMatch(modifier1 -> modifier.isCompatibleWith(modifier1) && modifier1.canApplyOnItemStack(stack)));
    }

    /**
     * Sets the Converts the Map of Modifiers and Levels to NBT on the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Map of the Modifiers and their Levels on the Tool.
     */
    public static void setModifiersToNBT(ItemStack stack, Map<Modifier, Pair<Integer, CompoundNBT>> modifiers) {
        final ListNBT list = new ListNBT();
        for (final Map.Entry<Modifier, Pair<Integer, CompoundNBT>> modifierData : modifiers.entrySet()) {
            final CompoundNBT tag = new CompoundNBT();
            tag.putString(TAG_MODIFIER, modifierData.getKey().getRegistryName().toString());
                final CompoundNBT info = new CompoundNBT();
                    info.putInt(TAG_LEVEL, modifierData.getValue().getKey());
                    info.put(TAG_COMPOUND, modifierData.getValue().getValue() != null ? modifierData.getValue().getValue() : new CompoundNBT());
                tag.put(TAG_INFO, info);
            list.add(tag);
        }
        stack.getOrCreateTag().put(TAG_MODIFIERS, list);
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to get the level off.
     * @return Returns the level of the modifier on the tool.
     */
    public static Pair<Integer, CompoundNBT> getModifierLevel(ItemStack stack, Modifier modifier) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifiers = getModifiers(stack);
        return modifiers.getOrDefault(modifier, Pair.of(0, null));
    }

    /**
     * This adds the specified Modifier to the Tool.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     * @param info    The Integer level that the Modifier being applied should have.
     */
    public static void addModifier(ItemStack stack, Modifier modifier, Pair<Integer, CompoundNBT> info) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifiers = getModifiers(stack);
        if (stack.getItem() instanceof IModifiedTool && canApplyModifier(modifiers.keySet(), stack, modifier)) {
            modifiers.putIfAbsent(modifier, info);
        }
        setModifiersToNBT(stack, modifiers);
    }

    /**
     * This adds the specified Modifiers to the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifier to remove.
     */
    public static void addModifiers(ItemStack stack, Modifier... modifiers) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifier_map = getModifiers(stack);
        if (stack.getItem() instanceof IModifiedTool && canApplyModifiers(modifier_map.keySet(), stack, modifiers)) {
            Stream.of(modifiers).forEach(modifier -> modifier_map.computeIfAbsent(modifier, key -> Pair.of(1, null)));
        }
        setModifiersToNBT(stack, modifier_map);
    }

    /**
     * This removes the specified Modifier from the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifiers to remove.
     */
    public static void removeModifiers(ItemStack stack, Modifier... modifiers) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifierMap = getModifiers(stack);
        Stream.of(modifiers).forEach(modifierMap::remove);
        setModifiersToNBT(stack, modifierMap);
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to set.
     * @param info     The Level of the Modifier to set.
     */
    public static void replaceModifierValue(ItemStack stack, Modifier modifier, Pair<Integer, CompoundNBT> info) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifierMap = getModifiers(stack);
        modifierMap.computeIfPresent(modifier, (modifier1, integer) -> info);
        setModifiersToNBT(stack, modifierMap);
    }

    /**
     * This lowers the level of the specified Modifier on the Tool.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void increaseModifierLevel(ItemStack stack, Modifier modifier) {
        increaseModifierLevel(stack, modifier, 1);
    }

    /**
     * This lowers the level of the specified Modifier on the Tool.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void increaseModifierLevel(ItemStack stack, Modifier modifier, int increase) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifiers = getModifiers(stack);
        Pair<Integer, CompoundNBT> oldLevel = modifiers.getOrDefault(modifier, Pair.of(0, null));
        modifiers.computeIfPresent(modifier, (modifier1, info) -> Pair.of(Math.min(oldLevel.getKey() + increase, modifier.getMaxLevel(stack)), info.getValue()));
        setModifiersToNBT(stack, modifiers);
    }

    /**
     * This lowers the level of the specified Modifier on the Tool.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void decreaseModifierLevel(ItemStack stack, Modifier modifier) {
        decreaseModifierLevel(stack, modifier, 1);
    }

    /**
     * This lowers the level of the specified Modifier on the Tool.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void decreaseModifierLevel(ItemStack stack, Modifier modifier, int decrease) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifiers = getModifiers(stack);
        Pair<Integer, CompoundNBT> oldLevel = modifiers.getOrDefault(modifier, Pair.of(0, null));
        modifiers.computeIfPresent(modifier, (modifier1, info) -> {
            Pair<Integer, CompoundNBT> newLevel = Pair.of(Math.min(oldLevel.getKey() - decrease, modifier.getMinLevel(stack)), info.getValue());
            if (newLevel.getLeft() <= 0) {
                return null;
            }
            return newLevel;
        });
        setModifiersToNBT(stack, modifiers);
    }

    /**
     * @param stack The ItemStack holding the Modifiers.
     * @return Returns an random modifier from the modifiers on the ItemStack
     */
    public static Modifier getRandomModifier(ItemStack stack) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifiers = getModifiers(stack);
        return modifiers.keySet().stream().skip(Essence.RANDOM.nextInt(modifiers.size())).findFirst().orElse(null);
    }

    /**
     * DO NOT USE THIS UNLESS YOU KNOW WHAT YOU'RE DOING!
     * I'm looking at you Future Simon >_>
     *
     * @param stack The ItemStack to be cleared of Modifiers
     */
    public static void clearModifiers(ItemStack stack) {
        setModifiersToNBT(stack, Collections.emptyMap());
    }
}
