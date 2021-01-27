package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;

public class EssenceParticleRegistrate {

  public static void init() {}

  public static final RegistryEntry<ParticleType<?>> GLUE_BALL_PARTICLE = Essence.ESSENCE_REGISTRATE.simple("glue_ball", ParticleType.class, () -> new BasicParticleType(false));

}
