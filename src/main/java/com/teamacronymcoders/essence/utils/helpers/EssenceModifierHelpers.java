package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EssenceModifierHelpers {

    public static final IForgeRegistry<Modifier> MODIFIERS = GameRegistry.findRegistry(Modifier.class);

    public static final String TAG_MODIFIERS = "Modifiers";
    public static final String TAG_MODIFIER = "Modifier";
    public static final String TAG_LEVEL = "ModifierLevel";

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
    public static Map<Modifier, Integer> getModifiers(ItemStack stack) {
        final Map<Modifier, Integer> modifiers = new HashMap<>();
        final CompoundNBT compoundNBT = stack.getTag();
        if (compoundNBT != null) {
            final ListNBT list = compoundNBT.getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                final CompoundNBT subCNBT = list.getCompound(i);
                final Modifier modifier = getModifierByName(subCNBT.getString(TAG_MODIFIER));
                if (modifier != null) {
                    modifiers.put(modifier, subCNBT.getInt(TAG_LEVEL));
                }
            }
        }
        return modifiers;
    }

    /**
     * Sets the Converts the Map of Modifiers and Levels to NBT on the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Map of the Modifiers and their Levels on the Tool.
     */
    public static void setModifiersToNBT(ItemStack stack, Map<Modifier, Integer> modifiers) {
        final ListNBT list = new ListNBT();
        for (final Map.Entry<Modifier, Integer> modifierData : modifiers.entrySet()) {
            final CompoundNBT tag = new CompoundNBT();
            tag.putString(TAG_MODIFIER, modifierData.getKey().getRegistryName().toString());
            tag.putInt(TAG_LEVEL, modifierData.getValue());
            list.add(tag);
        }
        stack.getOrCreateTag().put(TAG_MODIFIERS, list);
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to get the level off.
     * @return Returns the level of the modifier on the tool.
     */
    public static int getModifierLevel(ItemStack stack, Modifier modifier) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        return modifiers.getOrDefault(modifier, 0);
    }

    /**
     * This adds the specified Modifier to the Tool.
     *
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     * @param level    The Integer level that the Modifier being applied should have.
     */
    public static void addModifier(ItemStack stack, Modifier modifier, int level) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        modifiers.putIfAbsent(modifier, level);
        setModifiersToNBT(stack, modifiers);
    }

    /**
     * This adds the specified Modifiers to the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifier to remove.
     */
    public static void addModifiers(ItemStack stack, Modifier... modifiers) {
        Stream.of(modifiers).forEach(modifier -> addModifier(stack, modifier, 1));
    }

    /**
     * This removes the specified Modifier from the Tool.
     *
     * @param stack     The ItemStack holding the Modifiers.
     * @param modifiers The Modifiers to remove.
     */
    public static void removeModifiers(ItemStack stack, Modifier... modifiers) {
        final Map<Modifier, Integer> modifierMap = getModifiers(stack);
        Stream.of(modifiers).forEach(modifierMap::remove);
        setModifiersToNBT(stack, modifierMap);
    }

    /**
     * @param stack    The ItemStack holding the Modifiers.
     * @param modifier The Modifier to set.
     * @param level    The Level of the Modifier to set.
     */
    public static void replaceModifierValue(ItemStack stack, Modifier modifier, int level) {
        final Map<Modifier, Integer> modifierMap = getModifiers(stack);
        modifierMap.computeIfPresent(modifier, (modifier1, integer) -> level);
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
    public static void increaseModifierLevel(ItemStack stack, Modifier modifier, int level) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        int oldLevel = modifiers.getOrDefault(modifier, 0);
        modifiers.computeIfPresent(modifier, (modifier1, integer) -> Math.min(oldLevel + level, modifier.getMaxLevel()));
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
    public static void decreaseModifierLevel(ItemStack stack, Modifier modifier, int level) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        int oldLevel = modifiers.getOrDefault(modifier, 0);
        modifiers.computeIfPresent(modifier, (modifier1, integer) -> {
            int newLevel = Math.min(oldLevel - level, modifier.getMinLevel());
            if (newLevel <= 0) {
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
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        return modifiers.keySet().stream().skip(EssenceReferences.random.nextInt(modifiers.size())).findFirst().orElse(null);
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
