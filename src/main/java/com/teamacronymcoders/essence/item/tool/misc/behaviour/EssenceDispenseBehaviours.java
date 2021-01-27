package com.teamacronymcoders.essence.item.tool.misc.behaviour;


import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.entity.GlueBallEntity;
import com.teamacronymcoders.essence.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class EssenceDispenseBehaviours {
  public static Map<IItemProvider, IDispenseItemBehavior> dispenserBehaviours = new HashMap<>();

  static {
    dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR.get(), new OptionalDispenseBehavior() {
      @Override
      protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
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
                  e -> e instanceof LivingEntity && !e.isSpectator())
                  .forEach(e -> {
                    shear.itemInteractionForEntity(stack, null, (LivingEntity) e, Hand.MAIN_HAND);
                    Essence.LOGGER.info("Attempted to Shear at " + e.getPosition().toString());
                  });
        }
        return stack;
      }
    });
    dispenserBehaviours.put(EssenceItemRegistrate.GLUE_BALL_ITEM.get(), new ProjectileDispenseBehavior() {
      @Override
      protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
        return stackIn.getItem() instanceof GlueBallItem ? Util.make(new GlueBallEntity(worldIn, position.getX(), position.getY(), position.getZ()), glueBallEntity -> glueBallEntity.setItem(stackIn)) : null;
      }
    });
  }

  public static void init() {
    dispenserBehaviours.forEach(DispenserBlock::registerDispenseBehavior);
  }
}
