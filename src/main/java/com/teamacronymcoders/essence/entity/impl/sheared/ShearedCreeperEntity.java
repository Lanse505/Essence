package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.network.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.*;

public class ShearedCreeperEntity extends CreeperEntity {

    public ShearedCreeperEntity(EntityType<? extends ShearedCreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_CREEPER;
    }
}
