package com.teamacronymcoders.essence.common.util.helper;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.common.capability.itemstack.modifier.ItemStackModifierHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class EssenceEnchantmentHelper {

    private static final String enchantment_list = "Enchantments";
    private static final String id = "id";
    private static final String lvl = "lvl";

    /**
     * Either creates a new enchantment matching the values of "enchantment" and "level.
     * Or grabs the current matching enchantment and updates the level to match the value of "level"
     *
     * @param stack       The ItemStack to Enchant
     * @param enchantment The Enchantment to add
     */
    public static void createOrUpdateEnchantment(ItemStack stack, Enchantment enchantment, ModifierInstance instance, int multiplier) {
        CompoundTag stackNBT = stack.getOrCreateTag();
        ListTag enchantments = stack.getEnchantmentTags();
        int level = instance.getLevel() * multiplier;
        if (!enchantments.isEmpty()) {
            Optional<CompoundTag> nbtOptional = IntStream.range(0, enchantments.size())
                    .mapToObj(enchantments::getCompound)
                    .filter(tag -> tag.getString(id).equals(enchantment.getRegistryName().toString()) && tag.getInt(lvl) <= level).findAny();
            if (nbtOptional.isPresent()) {
                if (nbtOptional.get().getInt(lvl) != level) {
                    nbtOptional.get().putInt(lvl, level);
                }
            } else {
                CompoundTag nbt = new CompoundTag();
                nbt.putString(id, enchantment.getRegistryName().toString());
                nbt.putInt(lvl, level);
                enchantments.add(nbt);
            }
        } else {
            CompoundTag nbt = new CompoundTag();
            nbt.putString(id, enchantment.getRegistryName().toString());
            nbt.putInt(lvl, level);
            enchantments.add(nbt);
            stackNBT.put(enchantment_list, enchantments);
        }
    }

    /**
     * Either creates a new enchantment matching the values of "enchantment" and "level.
     * Or grabs the current matching enchantment and updates the level to match the value of "level"
     *
     * @param stack       The ItemStack to Enchant
     * @param enchantment The Enchantment to add
     */
    public static void createOrUpdateEnchantment(ItemStack stack, Enchantment enchantment, ModifierInstance instance) {
        CompoundTag stackNBT = stack.getOrCreateTag();
        ListTag enchantments = stack.getEnchantmentTags();
        int level = instance.getLevel();
        if (!enchantments.isEmpty()) {
            Optional<CompoundTag> nbtOptional = IntStream.range(0, enchantments.size())
                    .mapToObj(enchantments::getCompound)
                    .filter(tag -> tag.getString(id).equals(enchantment.getRegistryName().toString()) && tag.getInt(lvl) <= level).findAny();
            if (nbtOptional.isPresent()) {
                if (nbtOptional.get().getInt(lvl) != level) {
                    nbtOptional.get().putInt(lvl, level);
                }
            } else {
                CompoundTag nbt = new CompoundTag();
                nbt.putString(id, enchantment.getRegistryName().toString());
                nbt.putInt(lvl, level);
                enchantments.add(nbt);
            }
        } else {
            CompoundTag nbt = new CompoundTag();
            nbt.putString(id, enchantment.getRegistryName().toString());
            nbt.putInt(lvl, level);
            enchantments.add(nbt);
            stackNBT.put(enchantment_list, enchantments);
        }
    }

    /**
     * This takes an ItemStack, checks all the modifiers on it for EnchantmentCoreModifiers.
     * Grabs the Enchantments from the ItemStack, and then filters out any lingering Enchantments from removed Modifiers and removes them.
     *
     * @param stack The ItemStack to check for Enchantments on
     */
    public static void checkEnchantmentsForRemoval(ItemStack stack, LazyOptional<ItemStackModifierHolder> holderLazyOptional) {
        ListTag enchantments = stack.getEnchantmentTags();
        if (!enchantments.isEmpty()) {
            Map<String, CompoundTag> enchantmentIDs = new HashMap<>();
            IntStream.range(0, enchantments.size()).mapToObj(enchantments::getCompound).forEach(tag -> enchantmentIDs.put(tag.getString("id"), tag));
            holderLazyOptional.ifPresent(holder -> holder.getModifierInstances().stream()
                    .filter(instance -> instance.getModifier() instanceof ItemEnchantmentCoreModifier)
                    .forEach(instance -> {
                        Enchantment enchantment = ((ItemEnchantmentCoreModifier) instance.getModifier()).getLinkedEnchantment(stack);
                        if (enchantment != null) {
                            enchantmentIDs.remove(enchantment.getRegistryName().toString());
                        }
                    }));
            enchantmentIDs.values().forEach(enchantments::remove);
        }
    }
}
