package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShearedCreeperEntity extends CreeperEntity {

    public ShearedCreeperEntity(EntityType<? extends ShearedCreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_CREEPER;
    }
}
