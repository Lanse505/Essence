package com.teamacronymcoders.essence.common.entity.sheared;

import com.teamacronymcoders.essence.compat.registrate.EssenceEntityRegistrate;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ShearedCreeperEntity extends Creeper {

    public ShearedCreeperEntity(EntityType<? extends ShearedCreeperEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return EssenceEntityRegistrate.SHEARED_CREEPER.getId();
    }
}
