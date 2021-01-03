package com.teamacronymcoders.essence.item.tool.misc.behaviour;


import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.registration.EssenceModifierRegistration;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;

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
                Vector3i dirVec = dir.getDirectionVec();
                int level = instance.getLevel();
                Vector3d dirVecXYZ = new Vector3d(sourcePos.offset(dir).getX(), source.getY(), source.getZ()).add(new Vector3d(dirVec.getX(), dirVec.getY(), dirVec.getZ())).scale(level + 1);
                Vector3d vec1 = new Vector3d(-level, -level, -level).add(dirVecXYZ).add(new Vector3d(sourcePos.offset(dir).getX(), sourcePos.offset(dir).getY(), sourcePos.offset(dir).getZ())).add(new Vector3d(dirVec.getX(), dirVec.getY(), dirVec.getZ()).scale(level + 1));
                Vector3d vec2 = new Vector3d(level + 1, level + 1, level + 1).subtract(dirVecXYZ).add(new Vector3d(sourcePos.offset(dir).getX(), sourcePos.offset(dir).getY(), sourcePos.offset(dir).getZ())).subtract(new Vector3d(dirVec.getX(), dirVec.getY(), dirVec.getZ()).scale(level + 1));
                if (!world.isRemote && stack.getItem() instanceof EssenceShear) {
                    this.setSuccessful(false);
                    EssenceShear shear = (EssenceShear) stack.getItem();
                    world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(vec1, vec2),
                        e -> e instanceof LivingEntity && e instanceof IForgeShearable && !e.isSpectator())
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
