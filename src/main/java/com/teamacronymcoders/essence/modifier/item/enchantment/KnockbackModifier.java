package com.teamacronymcoders.essence.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.item.tool.EssenceBow;
import com.teamacronymcoders.essence.item.tool.EssenceSword;
import com.teamacronymcoders.essence.util.helper.EssenceEnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class KnockbackModifier extends ItemEnchantmentCoreModifier {

  public KnockbackModifier() {
    super(3);
  }

  @Override
  public void onInventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
    EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance);
  }

  @Override
  public Enchantment getLinkedEnchantment(ItemStack stack) {
    return stack.getItem() instanceof EssenceBow ? Enchantments.PUNCH_ARROWS : Enchantments.KNOCKBACK;
  }

  @Override
  public boolean canApplyOnObject(ItemStack stack) {
    return stack.getItem() instanceof EssenceSword || stack.getItem() instanceof EssenceBow;
  }


}
