package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemArrowModifier;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.common.modifier.item.interaction.explosive.EssenceExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class ExplosiveModifier extends ItemArrowModifier {

  public ExplosiveModifier() {
    super();
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity attacked, LivingEntity attacker, ModifierInstance instance) {
    EssenceExplosion explosion = new EssenceExplosion(attacker, attacked.getX(), attacked.getY(), attacked.getZ(), instance.getModifierData().getFloat("charge"), false, false);
    explosion.explode();
    explosion.finalizeExplosion(true);
    return true;
  }

  @Override
  public void onHitBlock(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {
    BlockPos pos = result.getBlockPos();
    if (!modifiableArrowEntity.getPersistentData().contains("hasExploded")) {
      EssenceExplosion explosion = new EssenceExplosion(modifiableArrowEntity, pos.getX(), pos.getY(), pos.getZ(), instance.getModifierData().getFloat("charge"), true, false);
      explosion.explode();
      explosion.finalizeExplosion(true);
      modifiableArrowEntity.getPersistentData().putBoolean("hasExploded", true);
    }
  }

  @Override
  public void onHitEntity(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, EntityHitResult result, ModifierInstance instance) {
    Entity hitEntity = result.getEntity();
    EssenceExplosion explosion = new EssenceExplosion(modifiableArrowEntity, hitEntity.getX(), hitEntity.getY(), hitEntity.getZ(), instance.getModifierData().getFloat("charge"), false, false);
    explosion.explode();
    explosion.finalizeExplosion(true);
  }

  @Override
  public void mergeData(ItemStack target, CompoundTag originalTag, CompoundTag mergeTag) {
    if (originalTag.contains("charge") && mergeTag.contains("charge")) {
      float originalCharge = originalTag.getFloat("charge");
      float mergeCharge = mergeTag.getFloat("charge");
      originalTag.putFloat("charge", originalCharge + mergeCharge);
    }
  }

  @Override
  public void mergeInstance(ItemStack target, ModifierInstance originalInstance, ModifierInstance mergeInstance) {
    mergeData(target, originalInstance.getModifierData(), mergeInstance.getModifierData());
  }

  @Override
  public List<Component> getRenderedText(ModifierInstance instance) {
    List<Component> components = super.getRenderedText(instance);
    components.add(Component.literal("   ").append(Component.translatable("essence.com.teamacronymcoders.essenceapi.modifier.explosive.charge", instance.getModifierData().getFloat("charge"))));
    return components;
  }

}
