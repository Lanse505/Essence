package com.teamacronymcoders.essence.entity;

import com.teamacronymcoders.essence.registrate.EssenceEntityRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceParticleRegistrate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class GlueBallEntity extends ProjectileItemEntity {

  public GlueBallEntity(EntityType<? extends GlueBallEntity> type, World world) {
    super(type, world);
  }

  public GlueBallEntity(World worldIn, LivingEntity throwerIn) {
    super(EssenceEntityRegistrate.GLUE_BALL.get(), throwerIn, worldIn);
  }

  public GlueBallEntity(World worldIn, double x, double y, double z) {
    super(EssenceEntityRegistrate.GLUE_BALL.get(), x, y, z, worldIn);
  }

  @Override
  protected Item getDefaultItem() {
    return EssenceItemRegistrate.GLUE_BALL_ITEM.get();
  }

  @OnlyIn(Dist.CLIENT)
  private IParticleData makeParticle() {
    ItemStack itemstack = this.func_213882_k();
    return (IParticleData) (itemstack.isEmpty() ? EssenceParticleRegistrate.GLUE_BALL_PARTICLE.get() : new ItemParticleData(ParticleTypes.ITEM, itemstack));
  }

  @OnlyIn(Dist.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 3) {
      IParticleData data = this.makeParticle();
      for (int i = 0; i < 8; ++i) {
        this.world.addParticle(data, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
      }
    }
  }

  @Override
  protected void onImpact(RayTraceResult result) {
    if (result.getType() == RayTraceResult.Type.ENTITY) {
      Entity entity = ((EntityRayTraceResult) result).getEntity();
      if (entity instanceof LivingEntity) {
        LivingEntity living = (LivingEntity) entity;
        EffectInstance instance = living.getActivePotionEffect(Effects.SLOWNESS);
        if (instance != null && instance.getAmplifier() < 5) {
          living.removePotionEffect(Effects.SLOWNESS);
          living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, instance.getDuration(), instance.getAmplifier() + 1));
          return;
        }
        living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 600, 1));
      }
    }

    if (!this.world.isRemote) {
      this.world.setEntityState(this, (byte) 3);
      this.remove();
    }
  }

  @Override
  public IPacket<?> createSpawnPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
