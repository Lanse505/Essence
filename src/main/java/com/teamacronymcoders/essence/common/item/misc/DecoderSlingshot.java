package com.teamacronymcoders.essence.common.item.misc;

import com.teamacronymcoders.essence.common.entity.SerializedEntityEntity;
import com.teamacronymcoders.essence.common.item.wrench.SerializedEntityItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

import static com.teamacronymcoders.essence.common.item.wrench.SerializedEntityItem.containsEntity;
import static com.teamacronymcoders.essence.common.item.wrench.SerializedEntityItem.getEntityFromNBT;

public class DecoderSlingshot extends ProjectileWeaponItem implements Vanishable {

    public DecoderSlingshot(Properties properties) {
        super(properties);
    }

    // "Yeet" The Method :)
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {
            ItemStack serializedEntity = player.getProjectile(stack);
            int i = this.getUseDuration(stack) - timeCharged;
            i = ForgeEventFactory.onArrowLoose(stack, level, player, i, !serializedEntity.isEmpty());
            if (i < 0) return;
            if (!serializedEntity.isEmpty()) {
                float f = getPowerForTime(i);
                if (!(f < 0.1D)) {
                    if (!level.isClientSide()) {
                        SerializedEntityEntity entity = new SerializedEntityEntity(level, player, serializedEntity.copy());
                        entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, f * 3.0f, 1.0f);
                        stack.hurtAndBreak(1, player, (shooter) -> {
                            shooter.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        level.addFreshEntity(entity);
                        if (stack.getTag() != null || containsEntity(stack)) {
                            LivingEntity serialized = getEntityFromNBT(stack.getTag(), level);
                            if (serialized instanceof Mob mob) {
//                                SoundEvent sound = mob.getAmbientSound();
//                                if (sound != null) {
//                                    level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//                                }
                            }
                        }
                        serializedEntity.shrink(1);
                        if (serializedEntity.isEmpty()) {
                            player.getInventory().removeItem(serializedEntity);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                    }
                }
            }
        }
    }

    public static float getPowerForTime(int charge) {
        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public int getUseDuration(ItemStack stack) {
        return 36000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        ItemStack proj = player.getProjectile(itemstack);
        if (proj.getItem() instanceof ArrowItem) return InteractionResultHolder.fail(itemstack);
        boolean flag = !player.getProjectile(itemstack).isEmpty();

        if (!flag) {
            return InteractionResultHolder.fail(itemstack);
        }
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.getItem() instanceof SerializedEntityItem;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
