package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.modifier.item.arrow.BrewedModifier;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class EssenceBowHelper {

  public static AbstractArrowEntity getArrowEntity (World world, ItemStack bow, ItemStack arrow, PlayerEntity player, float arrowVelocity) {
    final List<ModifierInstance<ItemStack>> instances = bow.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());

    // Flag for if the Bow has Modifiers && has Infinity
    boolean baseCodeCheck = player.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));

    // Checks if the Arrow is a "normal" arrow or a "special" arrow.
    ArrowItem arrowitem = arrow.getItem() instanceof ArrowItem ? (ArrowItem) arrow.getItem() : (ArrowItem) Items.ARROW;
    AbstractArrowEntity abstractArrowEntity = arrowitem.createArrow(world, arrow, player);

    // Iterates through all modifiers, filtering out all ArrowCoreModifier instances and then calling alterArrowEntity for them.
    instances.stream()
            .filter(instance -> instance.getModifier() instanceof ItemArrowCoreModifier)
            .forEach(instance -> ((ItemArrowCoreModifier) instance.getModifier()).alterArrowEntity(abstractArrowEntity, player, arrowVelocity, instance));

    abstractArrowEntity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0f, arrowVelocity * 3f, 1f);
    if (baseCodeCheck || player.abilities.isCreativeMode && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) {
      abstractArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
    }
    return abstractArrowEntity;
  }

  public static void modifyArrowEntityWithEnchantments (AbstractArrowEntity arrowEntity, ItemStack bow) {
    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
    if (j > 0) {
      arrowEntity.setDamage(arrowEntity.getDamage() + (double) j * 0.5D + 0.5D);
    }

    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);
    if (k > 0) {
      arrowEntity.setKnockbackStrength(k);
    }

    int l = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow);
    if (l > 0) {
      arrowEntity.setFire(100 * l);
    }
  }

  public static CompoundNBT createEffectInstanceNBT (EffectInstance... instances) {
    final CompoundNBT nbt = new CompoundNBT();
    final ListNBT list = new ListNBT();
    for (EffectInstance instance : instances) {
      final CompoundNBT effect = new CompoundNBT();
      list.add(instance.write(effect));
    }
    nbt.put(BrewedModifier.TAG_EFFECTS, list);
    return nbt;
  }

}
