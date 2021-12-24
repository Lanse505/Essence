package com.teamacronymcoders.essence.capability.itemstack.modifier;

import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemStackModifierProvider implements ICapabilityProvider, ICapabilitySerializable<ListTag> {

  private ItemStackModifierHolder modifierHolder;
  private final LazyOptional<ItemStackModifierHolder> optional = LazyOptional.of(() -> modifierHolder);

  public ItemStackModifierProvider(ItemStack stack) {
    modifierHolder = new ItemStackModifierHolder(stack);
  }

  public ItemStackModifierProvider(ItemStack stack, CompoundTag nbt) {
    modifierHolder = new ItemStackModifierHolder(stack);
    modifierHolder.deserializeNBT(nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND));
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER) {
      return optional.cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public ListTag serializeNBT() {
    return modifierHolder.serializeNBT();
  }

  @Override
  public void deserializeNBT(ListTag nbt) {
    modifierHolder.deserializeNBT(nbt);
  }
}
