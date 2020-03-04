package com.teamacronymcoders.essence.items.tools.misc;


import com.teamacronymcoders.essence.items.tools.EssenceShear;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.HashMap;
import java.util.Map;

public class EssenceDispenseBehaviours {
    public static Map<IItemProvider, IDispenseItemBehavior> dispenserBehaviours = new HashMap<>();

    @SuppressWarnings("deprecation")
    public static void init() {
        dispenserBehaviours.put(EssenceObjectHolders.ESSENCE_SHEAR, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World world = source.getWorld();
                Direction dir = source.getBlockState().get(DispenserBlock.FACING);
                BlockPos pos = source.getBlockPos();
                BlockPos offset = new BlockPos(
                    new Vec3d(
                        Direction.getFacingFromAxis(
                            Direction.AxisDirection.NEGATIVE,
                            dir.getAxis()
                        ).getDirectionVec()).add(1.0, 1.0, 1.0).scale(1));
                BlockPos start = pos.add(offset);
                BlockPos end = pos.subtract(offset);
                if (!world.isRemote && stack.getItem() instanceof EssenceShear) {
                    this.successful = false;
                    EssenceShear shear = (EssenceShear) stack.getItem();
                    BlockPos.getAllInBox(start, end)
                        .forEach(position -> {
                            for (Entity entity : world.getEntitiesInAABBexcluding((Entity) null, new AxisAlignedBB(pos), entity -> !entity.isSpectator() && entity instanceof IShearable)) {
                                if (entity instanceof LivingEntity) {
                                    shear.itemInteractionForEntity(stack, null, (LivingEntity) entity, Hand.MAIN_HAND);
                                }
                            }
                        });
                }
                return stack;
            }
        });
        dispenserBehaviours.forEach(DispenserBlock::registerDispenseBehavior);
    }

}
