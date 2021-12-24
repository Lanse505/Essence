package com.teamacronymcoders.essence.entity.sheared;

import com.teamacronymcoders.essence.registrate.EssenceEntityRegistrate;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;



public class ShearedChickenEntity extends Chicken {

  public ShearedChickenEntity(EntityType<? extends ShearedChickenEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public Packet<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }

  @Override
  protected ResourceLocation getDefaultLootTable() {
    return EssenceEntityRegistrate.SHEARED_CHICKEN.getId();
  }
}
