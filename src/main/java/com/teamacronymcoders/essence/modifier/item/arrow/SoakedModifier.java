package com.teamacronymcoders.essence.modifier.item.arrow;

import com.google.common.collect.Lists;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.util.helper.EssenceBowHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.Optional;

public class SoakedModifier extends ItemArrowCoreModifier {

  public SoakedModifier() {
    super(2);
  }

  @Override
  public void onCollide(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {
    int level = instance.getLevel();
    Optional<ItemStackModifierHolder> holder = bowStack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).resolve();
    Optional<List<MobEffectInstance>> instances = holder.flatMap(itemStackModifierHolder -> itemStackModifierHolder.getModifierInstances().stream().filter(savedInstance -> savedInstance.getModifier() instanceof BrewedModifier).map(correctInstance -> EssenceBowHelper.getEffectInstancesFromNBT(correctInstance.getModifierData())).reduce((effectInstances, effectInstances2) -> {
      effectInstances.addAll(effectInstances2);
      return effectInstances;
    }));
    List<MobEffectInstance> finalInstances = instances.orElseGet(Lists::newArrayList);
    if (level == 1) {
      EssenceBowHelper.onSplashHit(finalInstances, null, modifiableArrowEntity);
    } else if (level == 2) {
      EssenceBowHelper.makeAreaOfEffectCloud(finalInstances, modifiableArrowEntity);
    }
  }

  @Override
  public void alterArrowEntity(ModifiableArrowEntity modifiableArrowEntity, Player shooter, float velocity, ModifierInstance instance) {}

  @Override
  public boolean canApplyOnObject(ItemStack stack) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof BrewedModifier)).orElse(false);
  }
}
