package com.teamacronymcoders.essence.common.entity.sheared;

import com.teamacronymcoders.essence.compat.registrate.EssenceEntityRegistrate;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ShearedPigEntity extends Pig {

    public ShearedPigEntity(EntityType<? extends ShearedPigEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return EssenceEntityRegistrate.SHEARED_PIG.getId();
    }
}
