package com.teamacronymcoders.essence.impl.modifier;

import com.teamacronymcoders.essence.api.modifier.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.stream.Stream;

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
            Direction dir = context.getPlayer().getHorizontalFacing();
            BlockState state = context.getWorld().getBlockState(context.getPos());
            BlockPos offset = new BlockPos(new Vec3d(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getUnitVector()).add(1.0, 1.0, 1.0).scale(level));
            BlockPos start = pos.add(offset);
            BlockPos end = pos.subtract(offset);
            BlockPos.getAllInBox(start, end)
                .filter(position -> !position.equals(pos) && stack.canHarvestBlock(state))
                .forEach(position -> {
                    if (stack.getItem() instanceof IModifiedTool) {
                        IModifiedTool modifiedTool = (IModifiedTool) stack.getItem();
                        modifiedTool.onItemUseModified(new ItemUseContext(player, hand, new BlockRayTraceResult(new Vec3d(position.getX(), position.getY(), position.getZ()), player.getHorizontalFacing(), position, true)), true);
                    }
                });
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context, level);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {
        if (stack.getToolTypes().stream().map(toolType -> toolType.equals(ToolType.AXE) || toolType.equals(ToolType.PICKAXE) || toolType.equals(ToolType.SHOVEL)).findAny().orElse(false)) {
            Direction dir = miner.getHorizontalFacing();
            BlockPos offset = new BlockPos(new Vec3d(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getUnitVector()).add(1.0, 1.0, 1.0).scale(level));
            BlockPos start = pos.add(offset);
            BlockPos end = pos.subtract(offset);
            BlockPos.getAllInBox(start, end).filter(position -> !position.equals(pos) && stack.canHarvestBlock(state)).forEach(position -> world.breakBlock(position, true, miner));
            return true;
        }
        return false;
    }
}
