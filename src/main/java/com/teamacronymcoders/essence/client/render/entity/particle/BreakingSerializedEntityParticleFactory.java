package com.teamacronymcoders.essence.client.render.entity.particle;

import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class BreakingSerializedEntityParticleFactory <BasicParticleOptions extends ParticleOptions> extends BreakingItemParticle {

    protected BreakingSerializedEntityParticleFactory(ClientLevel pLevel, double pX, double pY, double pZ, ItemStack pStack) {
        super(pLevel, pX, pY, pZ, pStack);
    }

    @OnlyIn(Dist.CLIENT)
    public class SerializedEntityFactory implements ParticleProvider<BasicParticleOptions> {
        @Nullable
        @Override
        public Particle createParticle(BasicParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new BreakingSerializedEntityParticleFactory<BasicParticleOptions>(level, x, y, z, EssenceItemRegistrate.SERIALIZED_ENTITY.asStack());
        }
    }
}
