package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceBow;
import com.teamacronymcoders.essence.common.item.tool.EssenceSword;
import com.teamacronymcoders.essence.common.modifier.item.interaction.cascading.CascadingModifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceWorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class ExpanderModifier extends ItemInteractionModifier {

    public ExpanderModifier() {
        super(2);
    }

    @Override
    public InteractionResult useOn(UseOnContext context, ModifierInstance instance) {
        ItemStack stack = context.getItemInHand();
        if (context.getPlayer() != null) {
            Player player = context.getPlayer();
            InteractionHand hand = context.getHand();
            BlockPos pos = context.getClickedPos();
            Direction dir = context.getClickedFace();
            Vec3i vector3i = Direction.get(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getNormal();
            Vec3 vec3 = new Vec3(vector3i.getX(), vector3i.getY(), vector3i.getZ());
            BlockPos offset = new BlockPos(vec3.add(1.0, 1.0, 1.0).scale(instance.getLevel()));
            BlockPos start = pos.offset(offset);
            BlockPos end = pos.subtract(offset);
            if (stack.getItem() instanceof IModifiedItem modifiedTool) {
                modifiedTool.useOnModified(new UseOnContext(player, hand, new BlockHitResult(new Vec3(pos.getX(), pos.getY(), pos.getZ()), context.getClickedFace(), pos, false)), true);
                BlockPos.betweenClosedStream(start, end)
                        .filter(position -> !position.equals(pos))
                        .forEach(position -> modifiedTool.useOnModified(new UseOnContext(player, hand, new BlockHitResult(new Vec3(position.getX(), position.getY(), position.getZ()), context.getClickedFace(), position, false)), true));
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context, instance);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
        Direction dir = level.clip(new ClipContext(miner.getDeltaMovement(), new Vec3(pos.getX(), pos.getY(), pos.getZ()), ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, miner)).getDirection();
        Vec3i vector3i = Direction.get(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getNormal();
        BlockPos offset = new BlockPos(new Vec3(vector3i.getX(), vector3i.getY(), vector3i.getZ()).add(1.0, 1.0, 1.0).scale(instance.getLevel()));
        BlockPos start = pos.offset(offset);
        BlockPos end = pos.subtract(offset);
        BlockPos.betweenClosedStream(start, end)
                .filter(position -> !position.equals(pos) && stack.isCorrectToolForDrops(state))
                .forEach(position -> {
                    if (miner instanceof Player player) {
                        if (player instanceof ServerPlayer serverPlayer) {
                            BlockState foundState = level.getBlockState(position);
                            int exp = ForgeHooks.onBlockBreakEvent(level, serverPlayer.gameMode.getGameModeForPlayer(), serverPlayer, position);
                            if (exp != -1) {
                                Block block = foundState.getBlock();
                                BlockEntity be = EssenceWorldHelper.getBlockEntity(level, pos);
                                boolean removed = foundState.onDestroyedByPlayer(level, position, player, true, level.getFluidState(position));
                                if (removed) {
                                    block.destroy(level, position, foundState);
                                    block.playerDestroy(level, player, position, foundState, be, stack);
                                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                                    if (exp > 0) {
                                        if (!level.isClientSide()) {
                                            block.popExperience((ServerLevel) level, position, exp);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        EssenceWorldHelper.breakBlock(level, position, true, miner, stack);
                    }
                });
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state, float original, ModifierInstance instance) {
        return (float) -(original * 0.275) * instance.getLevel();
    }

    @Override
    public int getModifierCountValue(ItemStack stack, int level) {
        return level;
    }

    @Override
    public boolean canApplyTogether(ItemStack stack, IModifier<ItemStack> modifier) {
        return !(modifier instanceof CascadingModifier);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return !(object.getItem() instanceof EssenceSword || object.getItem() instanceof EssenceBow);
    }

}
