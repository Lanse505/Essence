package com.teamacronymcoders.essence.common.entity;

import com.teamacronymcoders.essence.common.item.wrench.SerializedEntityItem;
import com.teamacronymcoders.essence.compat.registrate.EssenceEntityRegistrate;
import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.compat.registrate.EssenceParticleRegistrate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class SerializedEntityEntity extends ThrowableItemProjectile {

    private static final EntityDataAccessor<ItemStack> SERIALIZED_ENTITY = SynchedEntityData.defineId(SerializedEntityEntity.class, EntityDataSerializers.ITEM_STACK);

    public SerializedEntityEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public SerializedEntityEntity(Level level, LivingEntity throwerIn, ItemStack serializedEntity) {
        super(EssenceEntityRegistrate.SERIALIZED_ENTITY.get(), throwerIn, level);
        this.entityData.set(SERIALIZED_ENTITY, serializedEntity);
    }

    public SerializedEntityEntity(Level level, double x, double y, double z, ItemStack serializedEntity) {
        super(EssenceEntityRegistrate.SERIALIZED_ENTITY.get(), x, y, z, level);
        this.entityData.set(SERIALIZED_ENTITY, serializedEntity);
    }

    public void addReferenceStack(ItemStack stack) {
        this.entityData.set(SERIALIZED_ENTITY, stack);
    }

    public ItemStack getReferenceStack() {
        return this.entityData.get(SERIALIZED_ENTITY);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SERIALIZED_ENTITY, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("SerializedItem", getReferenceStack().save(new CompoundTag()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(SERIALIZED_ENTITY, ItemStack.of(pCompound.getCompound("SerializedItem")));
    }

    @Override
    protected void onHit(HitResult result) {
        ItemStack ref = getReferenceStack();
        if (SerializedEntityItem.containsEntity(ref)) {
            if (result.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityResult = (EntityHitResult) result;
                Entity hitEntity = entityResult.getEntity();
                BlockPos pos = hitEntity.blockPosition();
                if (getReferenceStack().getTag() != null) {
                    Entity entityToPlace = SerializedEntityItem.getEntityFromNBT(ref.getTag(), level);
                    if (entityToPlace != null) {
                        ref.shrink(1);
                        entityToPlace.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                        level.addFreshEntity(entityToPlace);
                    }
                }
            } else if (result.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockResult = (BlockHitResult) result;
                BlockPos pos = blockResult.getBlockPos();
                if (ref.getTag() != null) {
                    Entity entityToPlace = SerializedEntityItem.getEntityFromNBT(ref.getTag(), level);
                    if (entityToPlace != null) {
                        ref.shrink(1);
                        entityToPlace.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                        level.addFreshEntity(entityToPlace);
                    }
                }
            }
        }
        if (!this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, EntityEvent.DEATH);
            this.kill();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return EssenceItemRegistrate.SERIALIZED_ENTITY.get();
    }

    @Override
    public ItemStack getItem() {
        return getReferenceStack();
    }

    @OnlyIn(Dist.CLIENT)
    private ParticleOptions makeParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions) (itemstack.isEmpty() ? EssenceParticleRegistrate.SERIALIZED_ENTITY_PARTICLE.get() : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions data = this.makeParticle();
            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(data, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
