package com.teamacronymcoders.essence.entity.impl.sheared;

import com.teamacronymcoders.essence.serializable.provider.loottable.EssenceEntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShearedGhastEntity extends GhastEntity {

    public ShearedGhastEntity(EntityType<? extends ShearedGhastEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EssenceEntityLootTables.SHEARED_GHAST;
    }
}
