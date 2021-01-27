package com.teamacronymcoders.essence.capability.itemstack.wrench;

import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public interface IEntityStorage {
  void setEntity(LivingEntity entity);

  LivingEntity getEntity(CompoundNBT nbt, World world);

  UUID getUUID();
}
