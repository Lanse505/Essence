package com.teamacronymcoders.essence.common.modifier.item.interaction.explosive;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EssenceExplosion extends Explosion {

  private final float internalRadius;
  private final boolean hurtPlayer;

  public EssenceExplosion(Entity source, double x, double y, double z, float charge, boolean shouldBreakBlocks, boolean hurtPlayer) {
    super(source.getLevel(), source, x, y, z, Math.min(Math.round(charge / 25), 4.0f), false, shouldBreakBlocks ? BlockInteraction.BREAK : BlockInteraction.NONE);
    this.internalRadius = Math.min(Math.round(charge / 25), 4.0f);
    this.hurtPlayer = hurtPlayer;
  }

  @Override
  public void explode() {
    Level level = this.getExploder().getLevel();
    double x = this.getPosition().x();
    double y = this.getPosition().y();
    double z = this.getPosition().z();

    level.gameEvent(this.source, GameEvent.EXPLODE, new BlockPos(x, y, z));
    Set<BlockPos> set = Sets.newHashSet();
    int i = 16;

    for(int j = 0; j < i; ++j) {
      for(int k = 0; k < i; ++k) {
        for(int l = 0; l < i; ++l) {
          if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
            double d0 = (float)j / 15.0F * 2.0F - 1.0F;
            double d1 = (float)k / 15.0F * 2.0F - 1.0F;
            double d2 = (float)l / 15.0F * 2.0F - 1.0F;
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d0 /= d3;
            d1 /= d3;
            d2 /= d3;
            float f = this.internalRadius * (0.7F + level.random.nextFloat() * 0.6F);
            double d4 = x;
            double d6 = y;
            double d8 = z;

            for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
              BlockPos blockpos = new BlockPos(d4, d6, d8);
              BlockState blockstate = level.getBlockState(blockpos);
              FluidState fluidstate = level.getFluidState(blockpos);
              if (!level.isInWorldBounds(blockpos)) {
                break;
              }

              Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, level, blockpos, blockstate, fluidstate);
              if (optional.isPresent()) {
                f -= (optional.get() + 0.3F) * 0.3F;
              }

              if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, level, blockpos, blockstate, f)) {
                set.add(blockpos);
              }

              d4 += d0 * (double)0.3F;
              d6 += d1 * (double)0.3F;
              d8 += d2 * (double)0.3F;
            }
          }
        }
      }
    }

    this.getToBlow().addAll(set);
    float f2 = this.internalRadius * 2.0F;
    int xMin = Mth.floor(x - (double)f2 - 1.0D);
    int xMax = Mth.floor(x + (double)f2 + 1.0D);
    int yMin = Mth.floor(y - (double)f2 - 1.0D);
    int yMax = Mth.floor(y + (double)f2 + 1.0D);
    int zMin = Mth.floor(z - (double)f2 - 1.0D);
    int zMax = Mth.floor(z + (double)f2 + 1.0D);
    List<Entity> list = level.getEntities(this.getSourceMob(), new AABB(xMin, yMin, zMin, xMax, yMax, zMax));
    ForgeEventFactory.onExplosionDetonate(level, this, list, f2);
    Vec3 vec3 = new Vec3(x, y, z);

    for (Entity entity : list.stream().filter(entity -> !(entity instanceof Player) || !hurtPlayer).toList()) {
      if (!entity.ignoreExplosion() || this.hurtPlayer) {
        double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
        if (d12 <= 1.0D) {
          double d5 = entity.getX() - x;
          double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - y;
          double d9 = entity.getZ() - z;
          double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
          if (d13 != 0.0D) {
            d5 /= d13;
            d7 /= d13;
            d9 /= d13;
            double d14 = getSeenPercent(vec3, entity);
            double d10 = (1.0D - d12) * d14;

            entity.hurt(this.getDamageSource(), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));

            double d11 = d10;
            if (entity instanceof LivingEntity) {
              d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
            }

            entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
            if (entity instanceof Player player) {
              if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                this.hitPlayers.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
              }
            }
          }
        }
      }
    }
  }
}