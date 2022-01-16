package com.teamacronymcoders.essence.common.util.helper;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemArrowModifier;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EssenceBowHelper {

    public static final String TAG_EFFECTS = "Effects";

    public static ModifiableArrowEntity getArrowEntity(Level level, ItemStack bow, ItemStack arrow, Player player, float arrowVelocity) {
        final List<ModifierInstance> instances = bow.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());

        // Flag for if the Bow has Modifiers && has Infinity
        boolean baseCodeCheck = player.getAbilities().instabuild || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));
        ModifiableArrowEntity modifiableArrowEntity = new ModifiableArrowEntity(level, player, bow, arrow);
        // Iterates through all modifiers, filtering out all ArrowCoreModifier instances and then calling alterArrowEntity for them.
        instances.stream()
                .filter(instance -> instance.getModifier().get() instanceof ItemArrowModifier)
                .forEach(instance -> ((ItemArrowModifier) instance.getModifier().get()).alterArrowEntity(modifiableArrowEntity, player, arrowVelocity, instance));

        // func_234612_a_ = setDirectionAndMovement
        modifiableArrowEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, arrowVelocity * 3f, 1f);
        if (baseCodeCheck || player.getAbilities().instabuild && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) {
            modifiableArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        return modifiableArrowEntity;
    }

    public static void modifyArrowEntityWithEnchantments(AbstractArrow arrowEntity, ItemStack bow) {
        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow);
        if (j > 0) {
            arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() + (double) j * 0.5D + 0.5D);
        }

        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, bow);
        if (k > 0) {
            arrowEntity.setKnockback(k);
        }

        int l = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow);
        if (l > 0) {
            arrowEntity.setSecondsOnFire(100 * l);
        }
    }

    public static void alterFinalEntity(Player shooter, ModifiableArrowEntity arrow, ItemStack bow) {
        final List<ModifierInstance> instances = bow.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER)
                .map(IModifierHolder::getModifierInstances)
                .orElse(new ArrayList<>());

        for (ModifierInstance instance : instances) {
            if (instance.getModifier().get() instanceof ItemArrowModifier modifier) {
                modifier.alterFinalEntity(shooter, arrow);
            }
        }
    }

    public static CompoundTag createEffectInstanceNBT(MobEffectInstance... instances) {
        final CompoundTag nbt = new CompoundTag();
        final ListTag list = new ListTag();
        for (MobEffectInstance instance : instances) {
            final CompoundTag effect = new CompoundTag();
            list.add(instance.save(effect));
        }
        nbt.put(TAG_EFFECTS, list);
        return nbt;
    }

    public static List<MobEffectInstance> getEffectInstancesFromNBT(CompoundTag nbt) {
        List<MobEffectInstance> instances = new ArrayList<>();
        final ListTag listNBT = nbt.getList(TAG_EFFECTS, Tag.TAG_COMPOUND);
        for (int i = 0; i < listNBT.size(); i++) {
            final CompoundTag instanceNBT = listNBT.getCompound(i);
            instances.add(MobEffectInstance.load(instanceNBT));
        }
        return instances;
    }

    public static void onSplashHit(List<MobEffectInstance> effects, @Nullable Entity entity, ModifiableArrowEntity arrow) {
        AABB aabb = arrow.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = arrow.level.getEntitiesOfClass(LivingEntity.class, aabb);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                if (livingentity.isAffectedByPotions()) {
                    double d0 = arrow.distanceToSqr(livingentity);
                    if (d0 < 16.0D) {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                        if (livingentity == entity) {
                            d1 = 1.0D;
                        }

                        for (MobEffectInstance effectinstance : effects) {
                            MobEffect effect = effectinstance.getEffect();
                            if (effect.isInstantenous()) {
                                effect.applyInstantenousEffect(arrow, arrow.getOwner(), livingentity, effectinstance.getAmplifier(), d1);
                            } else {
                                int i = (int) (d1 * (double) effectinstance.getDuration() + 0.5D);
                                if (i > 20) {
                                    livingentity.addEffect(new MobEffectInstance(effect, i, effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.isVisible()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void makeAreaOfEffectCloud(List<MobEffectInstance> effects, ModifiableArrowEntity arrow) {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(arrow.level, arrow.getX(), arrow.getY(), arrow.getZ());
        Entity entity = arrow.getOwner();
        if (entity instanceof LivingEntity) {
            areaEffectCloud.setOwner((LivingEntity) entity);
        }
        areaEffectCloud.setRadius(3.0F);
        areaEffectCloud.setRadiusOnUse(-0.5F);
        areaEffectCloud.setWaitTime(10);
        areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());
        for (MobEffectInstance effectinstance : effects) {
            areaEffectCloud.addEffect(new MobEffectInstance(effectinstance));
        }
        areaEffectCloud.setFixedColor(arrow.getColor());
        arrow.level.addFreshEntity(areaEffectCloud);
    }

}
