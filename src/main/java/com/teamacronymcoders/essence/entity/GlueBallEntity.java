package com.teamacronymcoders.essence.entity;

import com.teamacronymcoders.essence.registrate.EssenceEntityRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceParticleRegistrate;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class GlueBallEntity extends ThrowableItemProjectile {

  public GlueBallEntity(EntityType<? extends GlueBallEntity> type, Level level) {
    super(type, level);
  }

  public GlueBallEntity(Level level, LivingEntity throwerIn) {
    super(EssenceEntityRegistrate.GLUE_BALL.get(), throwerIn, level);
  }

  public GlueBallEntity(Level level, double x, double y, double z) {
    super(EssenceEntityRegistrate.GLUE_BALL.get(), x, y, z, level);
  }

  @Override
  protected Item getDefaultItem() {
    return EssenceItemRegistrate.GLUE_BALL_ITEM.get();
  }

  @OnlyIn(Dist.CLIENT)
  private ParticleOptions makeParticle() {
    ItemStack itemstack = this.getItemRaw();
    return (ParticleOptions) (itemstack.isEmpty() ? EssenceParticleRegistrate.GLUE_BALL_PARTICLE.get() : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
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
  protected void onHit(HitResult result) {
    if (result.getType() == HitResult.Type.ENTITY) {
      Entity entity = ((EntityHitResult) result).getEntity();
      if (entity instanceof LivingEntity living) {
        MobEffectInstance instance = living.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        if (instance != null && instance.getAmplifier() < 5) {
          living.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
          living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, instance.getDuration(), instance.getAmplifier() + 1));
          return;
        }
        living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 1));
      }
    }

    if (!this.level.isClientSide()) {
      this.level.broadcastEntityEvent(this, (byte) 3);
      this.kill();
    }
  }

  @Override
  public Packet<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
