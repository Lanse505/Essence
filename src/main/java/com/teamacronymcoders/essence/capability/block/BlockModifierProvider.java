package com.teamacronymcoders.essence.capability.block;

import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class BlockModifierProvider implements ICapabilityProvider, ICapabilitySerializable<ListNBT> {

  private final BlockModifierHolder modifierHolder = new BlockModifierHolder();
  private final LazyOptional<BlockModifierHolder> optional = LazyOptional.of(() -> modifierHolder);

  public BlockModifierProvider () {
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability (@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == EssenceCoreCapability.BLOCK_MODIFIER_HOLDER) {
      return optional.cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public ListNBT serializeNBT () {
    return modifierHolder.serializeNBT();
  }

  @Override
  public void deserializeNBT (ListNBT nbt) {
    modifierHolder.deserializeNBT(nbt);
  }
}
