package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EssenceModifierHelpers {

    public static final IForgeRegistry<Modifier> MODIFIERS = GameRegistry.findRegistry(Modifier.class);

    private static final String TAG_MODIFIERS = "Modifiers";
    private static final String TAG_MODIFIER = "Modifier";
    private static final String TAG_LEVEL = "ModifierLevel";

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
        if (stack.hasTag() && (stack.getTag() != null && stack.getTag().contains(TAG_MODIFIERS))) {
            final ListNBT list = stack.getTag().getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                final CompoundNBT compoundNBT = list.getCompound(i);
                final Modifier modifier = getModifierByName(compoundNBT.getString(TAG_MODIFIER));
                if (modifier != null) {
                    modifiers.put(modifier, compoundNBT.getInt(TAG_LEVEL));
                }
            }
        }
        return modifiers;
    }

    /**
     * This lowers the level of the specified Modifier on the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void increaseModifierLevel(ItemStack stack, Modifier modifier) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        modifiers.replace(modifier, modifiers.get(modifier) + 1);
        setModifiers(stack, modifiers);
    }

    /**
     * This lowers the level of the specified Modifier on the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void lowerModifierLevel(ItemStack stack, Modifier modifier) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        modifiers.replace(modifier, modifiers.get(modifier) - 1);
        setModifiers(stack, modifiers);
    }

    /**
     * This removes the specified Modifier from the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void addModifier(ItemStack stack, Modifier modifier) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        modifiers.put(modifier, 1);
        setModifiers(stack, modifiers);
    }

    /**
     * This removes the specified Modifier from the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifiers The Modifier to remove.
     */
    public static void addModifiers(ItemStack stack, Modifier... modifiers) {
        final Map<Modifier, Integer> modifierMap = getModifiers(stack);
        Arrays.asList(modifiers).forEach(modifier -> modifierMap.put(modifier, 1));
        setModifiers(stack, modifierMap);
    }

    /**
     * This removes the specified Modifier from the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     * @param level The Integer level that the Modifier being applied should have.
     */
    public static void addModifier(ItemStack stack, Modifier modifier, int level) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        modifiers.put(modifier, level);
        setModifiers(stack, modifiers);
    }

    /**
     * This removes the specified Modifier from the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifier The Modifier to remove.
     */
    public static void removeModifier(ItemStack stack, Modifier modifier) {
        final Map<Modifier, Integer> modifiers = getModifiers(stack);
        modifiers.remove(modifier);
        setModifiers(stack, modifiers);
    }

    /**
     * This removes the specified Modifier from the Tool.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifiers The Modifiers to remove.
     */
    public static void removeModifiers(ItemStack stack, Modifier... modifiers) {
        final Map<Modifier, Integer> modifierMap = getModifiers(stack);
        Arrays.asList(modifiers).forEach(modifierMap::remove);
        setModifiers(stack, modifierMap);
    }

    /**
     * Sets the list of Modifiers on the.
     * @param stack The ItemStack holding the Modifiers.
     * @param modifiers The Map of the Modifiers and their Levels on the Tool.
     */
    public static void setModifiers(ItemStack stack, Map<Modifier, Integer> modifiers) {
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
     * DO NOT USE THIS UNLESS YOU KNOW WHAT YOU'RE DOING!
     * I'm looking at you Future Simon >_>
     * @param stack The ItemStack to be cleared of Modifiers
     */
    public static void clearModifiers(ItemStack stack) {
        setModifiers(stack, Collections.emptyMap());
    }

}
