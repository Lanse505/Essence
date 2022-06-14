package com.teamacronymcoders.essenceapi.helper;

import com.teamacronymcoders.essenceapi.modified.rewrite.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essenceapi.modifier.ModifierInstance;
import com.teamacronymcoders.essenceapi.modifier.item.ItemCoreModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class EssenceAPIEnchantmentHelper {

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
                    .filter(tag -> tag.getString(id).equals(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString()) && tag.getInt(lvl) <= level).findAny();
            if (nbtOptional.isPresent()) {
                if (nbtOptional.get().getInt(lvl) != level) {
                    nbtOptional.get().putInt(lvl, level);
                }
            } else {
                CompoundTag nbt = new CompoundTag();
                nbt.putString(id, ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
                nbt.putInt(lvl, level);
                enchantments.add(nbt);
            }
        } else {
            CompoundTag nbt = new CompoundTag();
            nbt.putString(id, ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
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
    public static void createOrUpdateEnchantment(ItemStack stack, Enchantment enchantment, int level) {
        CompoundTag stackNBT = stack.getOrCreateTag();
        ListTag enchantments = stack.getEnchantmentTags();
        if (!enchantments.isEmpty()) {
            Optional<CompoundTag> nbtOptional = IntStream.range(0, enchantments.size())
                    .mapToObj(enchantments::getCompound)
                    .filter(tag -> tag.getString(id).equals(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString()) && tag.getInt(lvl) <= level).findAny();
            if (nbtOptional.isPresent()) {
                if (nbtOptional.get().getInt(lvl) != level) {
                    nbtOptional.get().putInt(lvl, level);
                }
            } else {
                CompoundTag nbt = new CompoundTag();
                nbt.putString(id, ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
                nbt.putInt(lvl, level);
                enchantments.add(nbt);
            }
        } else {
            CompoundTag nbt = new CompoundTag();
            nbt.putString(id, ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
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
                    .filter(instance -> instance.getModifier() instanceof ItemCoreModifier)
                    .forEach(instance -> {
                        Enchantment enchantment = ((ItemCoreModifier) instance.getModifier().get()).getLinkedEnchantment(stack);
                        if (enchantment != null) {
                            enchantmentIDs.remove(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
                        }
                    }));
            enchantmentIDs.values().forEach(enchantments::remove);
        }
    }
}
