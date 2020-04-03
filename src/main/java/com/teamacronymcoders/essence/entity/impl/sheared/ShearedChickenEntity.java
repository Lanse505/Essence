package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShearedChickenEntity extends ChickenEntity {

    public ShearedChickenEntity(EntityType<? extends ShearedChickenEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_CHICKEN;
    }
}
