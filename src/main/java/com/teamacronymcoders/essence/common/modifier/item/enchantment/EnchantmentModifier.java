package com.teamacronymcoders.essence.common.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EnchantmentModifier extends ItemInteractionModifier {
  private static final String ENCHANTMENTS = "Enchantments";

  private static final Map<Enchantment, BiFunction<ItemStack, Integer, Integer>> LEVEL_MULTIPLIER_MAP = new HashMap<>();

  private boolean updatedEnchantments = false;
  private final List<Enchantment> enchantments = new ArrayList<>();

  public EnchantmentModifier() {
    super();
  }

  @Override
  public boolean isCompatibleWith(ItemStack target, IModifier<?> modifier) {
    return !(modifier instanceof EnchantmentModifier);
  }

  @Override
  public void update(CompoundTag data) {
    if (enchantments.size() > 3) return;
    List<Enchantment> nbtEnchantments = new ArrayList<>();
    final ListTag listTag = data.getList(ENCHANTMENTS, Tag.TAG_STRING);
    for (int i = 0; i < listTag.size(); i++) {
      final String id = listTag.getString(i);
      if (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(id))) {
        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
        nbtEnchantments.add(enchantment);
      }
    }
    if (!enchantments.containsAll(nbtEnchantments)) {
      enchantments.clear();
      enchantments.addAll(nbtEnchantments);
    }
    updatedEnchantments = false;
  }

  @Override
  public void mergeData(ItemStack target, CompoundTag originalTag, CompoundTag mergeTag) {
    if (originalTag.contains(ENCHANTMENTS) && mergeTag.contains(ENCHANTMENTS)) {
      ListTag originalEnchantments = originalTag.getList(ENCHANTMENTS, Tag.TAG_STRING);
      if (originalEnchantments.size() > 3) return;
      ListTag mergeEnchantments = mergeTag.getList(ENCHANTMENTS, Tag.TAG_STRING);
      for (int i = 0; i < mergeEnchantments.size() && originalEnchantments.size() <= 3; i++) {
        String id = mergeEnchantments.getString(i);
        if (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(id))) {
          originalEnchantments.add(NbtOps.INSTANCE.createString(id));
        }
      }
    }
  }

  @Override
  public void mergeInstance(ItemStack target, ModifierInstance originalInstance, ModifierInstance mergeInstance) {
    mergeData(target, originalInstance.getModifierData(), mergeInstance.getModifierData());
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
    if (!updatedEnchantments) {
      for (Enchantment enchantment : enchantments) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, enchantment, getLevelForEnchantment(stack, enchantment, instance.getLevel()));
      }
      updatedEnchantments = true;
    }
  }

  public int getLevelForEnchantment(ItemStack stack, Enchantment enchantment, int modifierLevel) {
    BiFunction<ItemStack, Integer, Integer> func = LEVEL_MULTIPLIER_MAP.getOrDefault(enchantment, (tool, level) -> level);
    return func.apply(stack, modifierLevel);
  }

}
