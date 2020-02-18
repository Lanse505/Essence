package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.api.modifier.EnchantmentCoreModifier;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class EssenceEnchantmentHelper {

    public static void createOrUpdateEnchantment(ItemStack stack, Enchantment enchantment, int level) {
        CompoundNBT stackNBT = stack.getOrCreateTag();
        ListNBT enchantments = stack.getEnchantmentTagList();
        if (!enchantments.isEmpty()) {
            Optional<CompoundNBT> nbtOptional = IntStream.range(0, enchantments.size())
                .mapToObj(enchantments::getCompound)
                .filter(tag -> tag.getString("id").equals(enchantment.getName()) && tag.getInt("lvl") < level).findAny();
            if (nbtOptional.isPresent()) {
                nbtOptional.get().putInt("lvl", level);
            } else {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putString("id", enchantment.getName());
                nbt.putInt("lvl", level);
                enchantments.add(nbt);
            }
        } else {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("id", enchantment.getName());
            nbt.putInt("lvl", level);
            enchantments.add(nbt);
            stackNBT.put("Enchantments", enchantments);
        }
    }

    public static void checkEnchantmentsForRemoval(ItemStack stack) {
        ListNBT enchantments = stack.getEnchantmentTagList();
        if (!enchantments.isEmpty()) {
            Map<String, CompoundNBT> enchantmentIDs = new HashMap<>();
            IntStream.range(0, enchantments.size()).mapToObj(enchantments::getCompound).forEach(tag -> enchantmentIDs.put(tag.getString("id"), tag));
            EssenceHelpers.getModifiers(stack).keySet().stream()
                .filter(integer -> integer instanceof EnchantmentCoreModifier)
                .map(integer -> (EnchantmentCoreModifier) integer)
                .forEach(enchantmentCoreModifier -> enchantmentIDs.remove(enchantmentCoreModifier.getLinkedEnchantment(stack).getName()));
            enchantmentIDs.values().forEach(enchantments::remove);
        }
    }
}
