package com.teamacronymcoders.essence.utils.helpers;

import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.essence.api.modifier.ArrowCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.Map;

public class EssenceBowHelper {

    public static AbstractArrowEntity getArrowEntity(World world, ItemStack bow, ItemStack arrow, PlayerEntity player, float arrowVelocity) {
        final Map<Modifier, Integer> modifier_map = EssenceModifierHelpers.getModifiers(bow);

        // Flag for if the Bow has Modifiers && has Infinity
        boolean flag  = !modifier_map.isEmpty();
        boolean flag1 = modifier_map.keySet().stream().anyMatch(modifier -> modifier instanceof ArrowCoreModifier);
        boolean flag3 = player.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));

        // Checks if the Arrow is a "normal" arrow or a "special" arrow.
        ArrowItem arrowitem = (ArrowItem) arrow.getItem();
        AbstractArrowEntity abstractArrowEntity;

        // Check the flags, if there are mods and one of them is the "Brewed" modifier.
        if ((flag && flag1) && !(arrowitem.equals(Items.TIPPED_ARROW) || arrowitem.equals(Items.SPECTRAL_ARROW))) {
            abstractArrowEntity = new ArrowEntity(world, player);
            ArrowEntity entity = (ArrowEntity) abstractArrowEntity;
            modifier_map.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof ArrowCoreModifier)
                .map(entry -> Pair.of((ArrowCoreModifier) entry.getKey(), entry.getValue()))
                .forEach(pair -> {
                    pair.getFirst().alterArrowEntity(entity, player, arrowVelocity, pair.getSecond());
                });
            abstractArrowEntity = entity;
        } else {
            abstractArrowEntity = arrowitem.createArrow(world, arrow, player);
        }
        abstractArrowEntity.shoot(player, player.rotationPitch, player.rotationYaw, 0f, arrowVelocity * 3f, 1f);

        if (flag3 || player.abilities.isCreativeMode && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) {
            abstractArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        return abstractArrowEntity;
    }

    public static void modifyArrowEntityWithEnchantments(AbstractArrowEntity arrowEntity, ItemStack bow, float arrowVelocity) {
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
        if (j > 0) {
            arrowEntity.setDamage(arrowEntity.getDamage() + (double)j * 0.5D + 0.5D);
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

}
