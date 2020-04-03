package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.serializable.provider.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShearedCowEntity extends CowEntity {

    public ShearedCowEntity(EntityType<? extends ShearedCowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_COW;
    }
}
