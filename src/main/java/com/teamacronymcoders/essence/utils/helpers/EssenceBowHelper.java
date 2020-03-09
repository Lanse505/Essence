package com.teamacronymcoders.essence.utils.helpers;

import com.teamacronymcoders.essence.modifier.arrow.ArrowCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.modifier.arrow.BrewedModifier;
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
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class EssenceBowHelper {

    public static AbstractArrowEntity getArrowEntity(World world, ItemStack bow, ItemStack arrow, PlayerEntity player, float arrowVelocity) {
        final Map<Modifier, Pair<Integer, CompoundNBT>> modifierMap = EssenceModifierHelpers.getModifiers(bow);

        // Flag for if the Bow has Modifiers && has Infinity
        boolean baseCodeCheck = player.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));

        // Checks if the Arrow is a "normal" arrow or a "special" arrow.
        ArrowItem arrowitem = arrow.getItem() instanceof ArrowItem ? (ArrowItem) arrow.getItem() : (ArrowItem) Items.ARROW;
        AbstractArrowEntity abstractArrowEntity = arrowitem.createArrow(world, arrow, player);

        // Iterates through all modifiers, filtering out all ArrowCoreModifier instances and then calling alterArrowEntity for them.
        modifierMap.entrySet().stream()
            .filter(entry -> entry.getKey() instanceof ArrowCoreModifier)
            .map(entry -> Pair.of((ArrowCoreModifier) entry.getKey(), entry.getValue()))
            .forEach(pair -> pair.getKey().alterArrowEntity(abstractArrowEntity, player, arrowVelocity, pair.getRight().getKey()));

        abstractArrowEntity.shoot(player, player.rotationPitch, player.rotationYaw, 0f, arrowVelocity * 3f, 1f);
        if (baseCodeCheck || player.abilities.isCreativeMode && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) {
            abstractArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        return abstractArrowEntity;
    }

    public static void modifyArrowEntityWithEnchantments(AbstractArrowEntity arrowEntity, ItemStack bow) {
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

    public static CompoundNBT createEffectInstanceNBT(EffectInstance... instances) {
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
