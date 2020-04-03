package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.datagen.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShearedPigEntity extends PigEntity {

    public ShearedPigEntity(EntityType<? extends ShearedPigEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_PIG;
    }
}
