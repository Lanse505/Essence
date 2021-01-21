package com.teamacronymcoders.essence.entity.render;

import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import net.minecraft.client.particle.BreakingParticle;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BreakingGlueballParticleFactory<BasicParticleType extends IParticleData> extends BreakingParticle {

  public BreakingGlueballParticleFactory (ClientWorld world, double x, double y, double z, ItemStack stack) {
    super(world, x, y, z, stack);
  }

  @OnlyIn(Dist.CLIENT)
  public class GlueBallFactory implements IParticleFactory<BasicParticleType> {
    @Override
    public Particle makeParticle (BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return new BreakingGlueballParticleFactory<BasicParticleType>(world, x, y, z, EssenceItemRegistrate.GLUE_BALL_ITEM.asStack());
    }
  }

}
