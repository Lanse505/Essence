package com.teamacronymcoders.essence.common.item.behaviour;


import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.common.entity.GlueBallEntity;
import com.teamacronymcoders.essence.common.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.common.item.tool.EssenceShear;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class EssenceDispenseBehaviours {
    public static Map<ItemLike, DispenseItemBehavior> dispenserBehaviours = new HashMap<>();

    public static OptionalDispenseItemBehavior shearBehaviour = new OptionalDispenseItemBehavior() {
        @Override
        protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
            this.setSuccess(false);
            return shearComb(source, stack) ? stack : shear(source, stack) ? stack : stack;
        }
    };

    static {
        dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR.get(), shearBehaviour);
        dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR_EMPOWERED.get(), shearBehaviour);
        dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR_SUPREME.get(), shearBehaviour);
        dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR_DIVINE.get(), shearBehaviour);
        dispenserBehaviours.put(EssenceItemRegistrate.GLUE_BALL_ITEM.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack stackIn) {
                return stackIn.getItem() instanceof GlueBallItem ? Util.make(new GlueBallEntity(level, position.x(), position.y(), position.z()), glueBallEntity -> glueBallEntity.setItem(stackIn)) : null;
            }
        });
    }

    public static void init() {
        dispenserBehaviours.forEach(DispenserBlock::registerBehavior);
    }

    private static boolean shear(BlockSource source, ItemStack stack) {
        ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
        Level level = source.getLevel();
        if (instance != null && !level.isClientSide() && stack.getItem() instanceof EssenceShear shear) {
            level.getEntities((Entity) null, getAABB(source, instance), e -> e instanceof LivingEntity && !e.isSpectator()).forEach(entity -> {
                shear.interactLivingEntity(stack, null, (LivingEntity) entity, InteractionHand.MAIN_HAND);
            });
            return true;
        }
        return false;
    }

    private static boolean shearComb(BlockSource source, ItemStack stack) {
        ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
        Level level = source.getLevel();
        if (instance != null) {
            AtomicBoolean sheared = new AtomicBoolean(false);
            BlockPos.betweenClosedStream(getAABB(source, instance)).forEach(pos -> {
                if (!level.isClientSide() && stack.getItem() instanceof EssenceShear) {
                    BlockState state = level.getBlockState(pos);
                    if (state.is(BlockTags.BEEHIVES)) {
                        int j = state.getValue(BeehiveBlock.HONEY_LEVEL);
                        if (j >= 5) {
                            level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                            BeehiveBlock.dropHoneycomb(level, pos);
                            ((BeehiveBlock) state.getBlock()).releaseBeesAndResetHoneyLevel(level, state, pos, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
                            sheared.set(true);
                        }
                    }
                }
            });
            return sheared.get();
        }
        return false;
    }

    public static AABB getAABB(BlockSource source, ModifierInstance instance) {
        Direction dir = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos sourcePos = source.getPos();
        int level = instance.getLevel() + 1;
        if (!dir.getAxis().isVertical()) {
            BlockPos corner1 = sourcePos.relative(dir, 1).relative(dir.getClockWise(), level).relative(Direction.UP, -level);
            BlockPos corner2 = sourcePos.relative(dir, 2 * level + 1).relative(dir.getCounterClockWise(), level).relative(Direction.UP, level);
            return new AABB(corner1, corner2);
        }
        return new AABB(sourcePos, sourcePos);
    }

}
