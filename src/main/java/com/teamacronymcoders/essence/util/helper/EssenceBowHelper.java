package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class EssenceBowHelper {

  public static final String TAG_EFFECTS = "Effects";

  public static AbstractArrowEntity getArrowEntity(World world, ItemStack bow, ItemStack arrow, PlayerEntity player, float arrowVelocity) {
    final List<ModifierInstance> instances = bow.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());

    // Flag for if the Bow has Modifiers && has Infinity
    boolean baseCodeCheck = player.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));
    ModifiableArrowEntity modifiableArrowEntity = new ModifiableArrowEntity(world, player, bow, arrow);

    // Iterates through all modifiers, filtering out all ArrowCoreModifier instances and then calling alterArrowEntity for them.
    instances.stream()
            .filter(instance -> instance.getModifier() instanceof ItemArrowCoreModifier)
            .forEach(instance -> ((ItemArrowCoreModifier) instance.getModifier()).alterArrowEntity(modifiableArrowEntity, player, arrowVelocity, instance));

    // func_234612_a_ = setDirectionAndMovement
    modifiableArrowEntity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0f, arrowVelocity * 3f, 1f);
    if (baseCodeCheck || player.abilities.isCreativeMode && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) {
      modifiableArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
    }
    return modifiableArrowEntity;
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
    nbt.put(TAG_EFFECTS, list);
    return nbt;
  }

  public static List<EffectInstance> getEffectInstancesFromNBT(CompoundNBT nbt) {
    List<EffectInstance> instances = new ArrayList<>();
    final ListNBT listNBT = nbt.getList(TAG_EFFECTS, Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < listNBT.size(); i++) {
      final CompoundNBT instanceNBT = listNBT.getCompound(i);
      instances.add(EffectInstance.read(instanceNBT));
    }
    return instances;
  }

  public static void onSplashHit(List<EffectInstance> effects, @Nullable Entity entity, ModifiableArrowEntity arrow) {
    AxisAlignedBB axisalignedbb = arrow.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
    List<LivingEntity> list = arrow.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);
    if (!list.isEmpty()) {
      for (LivingEntity livingentity : list) {
        if (livingentity.canBeHitWithPotion()) {
          double d0 = arrow.getDistanceSq(livingentity);
          if (d0 < 16.0D) {
            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
            if (livingentity == entity) {
              d1 = 1.0D;
            }

            for (EffectInstance effectinstance : effects) {
              Effect effect = effectinstance.getPotion();
              if (effect.isInstant()) {
                effect.affectEntity(arrow, arrow.func_234616_v_(), livingentity, effectinstance.getAmplifier(), d1);
              } else {
                int i = (int) (d1 * (double) effectinstance.getDuration() + 0.5D);
                if (i > 20) {
                  livingentity.addPotionEffect(new EffectInstance(effect, i, effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.doesShowParticles()));
                }
              }
            }
          }
        }
      }
    }
  }

  public static void makeAreaOfEffectCloud(List<EffectInstance> effects, ModifiableArrowEntity arrow) {
    AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(arrow.world, arrow.getPosX(), arrow.getPosY(), arrow.getPosZ());
    Entity entity = arrow.func_234616_v_();
    if (entity instanceof LivingEntity) {
      areaeffectcloudentity.setOwner((LivingEntity) entity);
    }
    areaeffectcloudentity.setRadius(3.0F);
    areaeffectcloudentity.setRadiusOnUse(-0.5F);
    areaeffectcloudentity.setWaitTime(10);
    areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
    for (EffectInstance effectinstance : effects) {
      areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
    }
    areaeffectcloudentity.setColor(arrow.getColor());
    arrow.world.addEntity(areaeffectcloudentity);
  }

}
