package com.teamacronymcoders.essence.common.modifier.item.arrow;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.BlockHitResult;

public class EndericModifier extends ItemArrowCoreModifier {

    @Override
    public void onCollide(ItemStack bowStack, ModifiableArrowEntity abstractArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {
        for(int i = 0; i < 32; ++i) {
            abstractArrowEntity.getLevel().addParticle(ParticleTypes.PORTAL, abstractArrowEntity.getX(), abstractArrowEntity.getY() + Essence.RANDOM.nextDouble() * 2.0D, abstractArrowEntity.getZ(), Essence.RANDOM.nextGaussian(), 0.0D, Essence.RANDOM.nextGaussian());
        }

        if (!abstractArrowEntity.getLevel().isClientSide() && !abstractArrowEntity.isRemoved()) {
            Entity owner = abstractArrowEntity.getOwner();
            if (owner instanceof ServerPlayer player) {
                if (player.connection.getConnection().isConnected() && player.getLevel() == abstractArrowEntity.getLevel() && !player.isSleeping()) {
                    net.minecraftforge.event.entity.EntityTeleportEvent event = net.minecraftforge.event.ForgeEventFactory.onEntityTeleportCommand(player, abstractArrowEntity.getX(), abstractArrowEntity.getY(), abstractArrowEntity.getZ());
                    if (!event.isCanceled()) {
                        if (Essence.RANDOM.nextFloat() < 0.05F && abstractArrowEntity.getLevel().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                            Endermite mite = EntityType.ENDERMITE.create(abstractArrowEntity.getLevel());
                            if (mite != null) {
                                mite.moveTo(owner.getX(), owner.getY(), owner.getZ(), owner.getYRot(), owner.getXRot());
                                abstractArrowEntity.getLevel().addFreshEntity(mite);
                            }
                        }
                        if (owner.isPassenger()) {
                            player.dismountTo(abstractArrowEntity.getX(), abstractArrowEntity.getY(), abstractArrowEntity.getZ());
                        } else {
                            owner.teleportTo(abstractArrowEntity.getX(), abstractArrowEntity.getY(), abstractArrowEntity.getZ());
                        }

                        owner.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                        owner.resetFallDistance();
                        owner.hurt(DamageSource.FALL, 1);
                    }
                }
            } else if (owner != null){
                owner.teleportTo(abstractArrowEntity.getX(), abstractArrowEntity.getY(), abstractArrowEntity.getZ());
                owner.resetFallDistance();
            }
            abstractArrowEntity.discard();
        }
    }

    @Override
    public void alterArrowEntity(ModifiableArrowEntity abstractArrowEntity, Player shooter, float velocity, ModifierInstance instance) {}
}
