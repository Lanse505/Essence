package com.teamacronymcoders.essence.entity.sheared;

import com.teamacronymcoders.essence.registrate.EssenceEntityRegistrate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShearedCowEntity extends CowEntity {

  public ShearedCowEntity(EntityType<? extends ShearedCowEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  public IPacket<?> createSpawnPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }

  @Override
  protected ResourceLocation getLootTable() {
    return EssenceEntityRegistrate.SHEARED_COW.getId();
  }
}
