package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BreakingGlueballParticleFactory<BasicParticleType extends ParticleOptions> extends BreakingItemParticle {

  public BreakingGlueballParticleFactory(ClientLevel level, double x, double y, double z, ItemStack stack) {
    super(level, x, y, z, stack);
  }

  @OnlyIn(Dist.CLIENT)
  public class GlueBallFactory implements ParticleProvider<BasicParticleType> {
    @Override
    public Particle createParticle(BasicParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return new BreakingGlueballParticleFactory<BasicParticleType>(level, x, y, z, EssenceItemRegistrate.GLUE_BALL_ITEM.asStack());
    }
  }

}
