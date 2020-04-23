package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.network.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.*;

public class ShearedChickenEntity extends ChickenEntity {

    public ShearedChickenEntity(EntityType<? extends ShearedChickenEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_CHICKEN;
    }
}
