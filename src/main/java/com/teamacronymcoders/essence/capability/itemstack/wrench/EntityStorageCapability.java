package com.teamacronymcoders.essence.capability.itemstack.wrench;

import com.teamacronymcoders.essence.client.render.tesr.itemstack.SerializableMobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityStorageCapability implements IEntityStorage, INBTSerializable<CompoundNBT> {

    private final ItemStack stack;
    private UUID uuid;

    public EntityStorageCapability() {
        stack = ItemStack.EMPTY;
    }

    public EntityStorageCapability(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void setEntity(LivingEntity entity) {
        uuid = entity.getUniqueID();
        SerializableMobRenderer.entityCache.put(uuid, entity);
        this.stack.setTag(serializeNBT(entity));
    }

    @Override
    public LivingEntity getEntity(CompoundNBT nbt, World world) {
        uuid = nbt.getUniqueId("uuid");
        return getEntityFromNBT(nbt, world);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public CompoundNBT serializeNBT(LivingEntity entity) {
        uuid = entity.getUniqueID();
        CompoundNBT nbt = new CompoundNBT();
        nbt.putUniqueId("uuid", uuid);
        String entityID = EntityType.getKey(entity.getType()).toString();
        nbt.putString("entity", entityID);
        entity.writeWithoutTypeId(nbt);
        return nbt;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    public static LivingEntity getEntityFromNBT(CompoundNBT nbt, World world) {
        if (nbt.contains("entity")) {
            EntityType type = EntityType.byKey(nbt.getString("entity")).orElse(null);
            if (type != null) {
                Entity entity = type.create(world);
                if (entity instanceof LivingEntity) {
                    entity.read(nbt);
                    return (LivingEntity) entity;
                }
                return null;
            }
        }
        return null;
    }

    public void deserializeNBT(CompoundNBT nbt, World world) {
        uuid = nbt.getUniqueId("uuid");
        LivingEntity entity = getEntityFromNBT(nbt, world);
        if (entity != null) {
            SerializableMobRenderer.entityCache.invalidate(uuid);
            SerializableMobRenderer.entityCache.put(uuid, entity);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
    }
}
