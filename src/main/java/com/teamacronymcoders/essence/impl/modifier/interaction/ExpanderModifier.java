package com.teamacronymcoders.essence.impl.modifier.interaction;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.impl.items.tools.EssenceBow;
import com.teamacronymcoders.essence.impl.items.tools.EssenceSword;
import com.teamacronymcoders.essence.utils.helpers.EssenceWorldHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExpanderModifier extends InteractionCoreModifier {

    public ExpanderModifier() {
        super(2);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context, int level) {
        ItemStack stack = context.getItem();
        if (context.getPlayer() != null) {
            PlayerEntity player = context.getPlayer();
            Hand hand = context.getHand();
            BlockPos pos = context.getPos();
            Direction dir = context.getFace();
            BlockPos offset = new BlockPos(new Vec3d(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getUnitVector()).add(1.0, 1.0, 1.0).scale(level));
            BlockPos start = pos.add(offset);
            BlockPos end = pos.subtract(offset);
            BlockPos.getAllInBox(start, end)
                .filter(position -> !position.equals(pos))
                .forEach(position -> {
                    if (stack.getItem() instanceof IModifiedTool) {
                        IModifiedTool modifiedTool = (IModifiedTool) stack.getItem();
                        Essence.LOGGER.info(position);
                        modifiedTool.onItemUseModified(new ItemUseContext(player, hand, new BlockRayTraceResult(new Vec3d(position.getX(), position.getY(), position.getZ()), context.getFace(), position, false)), true);
                    }
                });
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context, level);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {
        Direction dir = world.rayTraceBlocks(new RayTraceContext(miner.getPositionVec(), new Vec3d(pos.getX(), pos.getY(), pos.getZ()), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, miner)).getFace();
        BlockPos offset = new BlockPos(new Vec3d(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getUnitVector()).add(1.0, 1.0, 1.0).scale(level));
        BlockPos start = pos.add(offset);
        BlockPos end = pos.subtract(offset);
        BlockPos.getAllInBox(start, end)
            .filter(position -> !position.equals(pos) && stack.canHarvestBlock(state))
            .forEach(position -> {
                if (miner instanceof PlayerEntity) {
                    if (world.getBlockState(position).canHarvestBlock(world, position, (PlayerEntity) miner)) {
                        EssenceWorldHelper.breakBlock(world, position, true, miner, stack);
                    }
                } else {
                    EssenceWorldHelper.breakBlock(world, position, true, miner, stack);
                }
            });
        return true;
    }

    @Override
    public float getModifiedEfficiency(ItemStack stack, int level, float base) {
        return (float) -(base * 0.275) * level;
    }

    @Override
    public int getModifierCountValue(ItemStack stack, int level) {
        return level;
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return !(modifier instanceof LumberjackModifier);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return !(stack.getItem() instanceof EssenceSword || stack.getItem() instanceof EssenceBow);
    }
}
