package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import com.teamacronymcoders.essence.registrate.EssenceEntityRegistrate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShearedCreeperEntity extends CreeperEntity {

  public ShearedCreeperEntity (EntityType<? extends ShearedCreeperEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  public IPacket<?> createSpawnPacket () {
    return NetworkHooks.getEntitySpawningPacket(this);
  }

  @Override
  protected ResourceLocation getLootTable () {
    return EssenceEntityRegistrate.SHEARED_CREEPER.getId();
  }
}
