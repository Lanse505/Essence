package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceParticleTypeRegistration {

    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Essence.MODID);

    public static RegistryObject<BasicParticleType> GLUE_BALL_PARTICLE = PARTICLE_TYPE_DEFERRED_REGISTER.register("glue_ball", () -> new BasicParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
