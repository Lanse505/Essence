package com.teamacronymcoders.essence.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.util.helper.EssenceBowHelper;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.util.LazyOptional;

public class SoakedModifier extends ItemArrowCoreModifier {

  public SoakedModifier() {
    super(2);
  }

  @Override
  public void onCollide(ModifiableArrowEntity modifiableArrowEntity, PlayerEntity shooter, BlockRayTraceResult result, ModifierInstance instance) {
    int level = instance.getLevel();
    if (level == 1) {
      List<EffectInstance> instances = EssenceBowHelper.getEffectInstancesFromNBT(instance.getModifierData());
      EssenceBowHelper.onSplashHit(instances, null, modifiableArrowEntity);
    } else if (level == 2) {
      List<EffectInstance> instances = EssenceBowHelper.getEffectInstancesFromNBT(instance.getModifierData());
      EssenceBowHelper.makeAreaOfEffectCloud(instances, modifiableArrowEntity);
    }
  }

  @Override
  public void alterArrowEntity(ModifiableArrowEntity modifiableArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance instance) {}

  @Override
  public boolean canApplyOnObject(ItemStack stack) {
    final LazyOptional<ItemStackModifierHolder> holderLazyOptional = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    return holderLazyOptional.map(holder -> holder.getModifierInstances().stream().anyMatch(instance -> instance.getModifier() instanceof BrewedModifier)).orElse(false);
  }
}
