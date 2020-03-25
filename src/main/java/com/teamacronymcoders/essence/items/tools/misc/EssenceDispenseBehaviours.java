package com.teamacronymcoders.essence.items.tools.misc;


import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.items.tools.EssenceShear;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.helpers.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.utils.registration.EssenceModifierRegistration;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.HashMap;
import java.util.Map;

public class EssenceDispenseBehaviours {
    public static Map<IItemProvider, IDispenseItemBehavior> dispenserBehaviours = new HashMap<>();

    static {
        dispenserBehaviours.put(EssenceObjectHolders.ESSENCE_SHEAR, new OptionalDispenseBehavior() {
            @SuppressWarnings("deprecation")
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                ModifierInstance<?> instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistration.EXPANDER_MODIFIER.get());
                World world = source.getWorld();
                Direction dir = source.getBlockState().get(DispenserBlock.FACING);
                BlockPos sourcePos = source.getBlockPos();
                Vec3i dirVec = dir.getDirectionVec();
                int level = instance.getLevel();
                Vec3d dirVecXYZ = new Vec3d(sourcePos.offset(dir)).add(new Vec3d(dirVec)).scale(level + 1);
                Vec3d vec1 = new Vec3d(-level, -level, -level).add(dirVecXYZ).add(new Vec3d(sourcePos.offset(dir))).add(new Vec3d(dirVec).scale(level + 1));
                Vec3d vec2 = new Vec3d(level + 1, level + 1, level + 1).subtract(dirVecXYZ).add(new Vec3d(sourcePos.offset(dir))).subtract(new Vec3d(dirVec).scale(level + 1));
                if (!world.isRemote && stack.getItem() instanceof EssenceShear) {
                    this.successful = false;
                    EssenceShear shear = (EssenceShear) stack.getItem();
                    world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(vec1, vec2),
                        e -> e instanceof LivingEntity && e instanceof IShearable && !e.isSpectator())
                        .forEach(e -> {
                            shear.itemInteractionForEntity(stack, null, (LivingEntity) e, Hand.MAIN_HAND);
                            Essence.LOGGER.info("Attempted to Shear at " + e.getPosition().toString());
                        });
                }
                return stack;
            }
        });
    }

    public static void init() {
        dispenserBehaviours.forEach(DispenserBlock::registerDispenseBehavior);
    }
}
