package com.teamacronymcoders.essence.item.behaviour;


import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.entity.GlueBallEntity;
import com.teamacronymcoders.essence.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EssenceDispenseBehaviours {
  public static Map<IItemProvider, IDispenseItemBehavior> dispenserBehaviours = new HashMap<>();

  static {
    dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR.get(), new OptionalDispenseBehavior() {
      @Override
      protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        this.setSuccessful(false);
        return shearComb(source, stack) ? stack : shear(source, stack) ? stack : stack;
      }
    });
    dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR_EMPOWERED.get(), new OptionalDispenseBehavior() {
      @Override
      protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        this.setSuccessful(false);
        return shearComb(source, stack) ? stack : shear(source, stack) ? stack : stack;
      }
    });
    dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR_SUPREME.get(), new OptionalDispenseBehavior() {
      @Override
      protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        this.setSuccessful(false);
        return shearComb(source, stack) ? stack : shear(source, stack) ? stack : stack;
      }
    });
    dispenserBehaviours.put(EssenceItemRegistrate.ESSENCE_SHEAR_DIVINE.get(), new OptionalDispenseBehavior() {
      @Override
      protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        this.setSuccessful(false);
        return shearComb(source, stack) ? stack : shear(source, stack) ? stack : stack;
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

  private static boolean shear(IBlockSource source, ItemStack stack) {
    ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
    World world = source.getWorld();
    if (instance != null && !world.isRemote && stack.getItem() instanceof EssenceShear) {
      EssenceShear shear = (EssenceShear) stack.getItem();
      world.getEntitiesInAABBexcluding(null, getAABB(source, instance), e -> e instanceof LivingEntity && !e.isSpectator()).forEach(entity -> {
        shear.itemInteractionForEntity(stack, null, (LivingEntity) entity, Hand.MAIN_HAND);
      });
      return true;
    }
    return false;
  }

  private static boolean shearComb(IBlockSource source, ItemStack stack) {
    ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
    World world = source.getWorld();
    if (instance != null) {
      AtomicBoolean sheared = new AtomicBoolean(false);
      BlockPos.getAllInBox(getAABB(source, instance)).forEach(pos -> {
        if (!world.isRemote && stack.getItem() instanceof EssenceShear) {
          BlockState state = world.getBlockState(pos);
          if (state.isIn(BlockTags.BEEHIVES)) {
            int j = state.get(BeehiveBlock.HONEY_LEVEL);
            if (j >= 5) {
              world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
              BeehiveBlock.dropHoneyComb(world, pos);
              ((BeehiveBlock) state.getBlock()).takeHoney(world, state, pos, null, BeehiveTileEntity.State.BEE_RELEASED);
              sheared.set(true);
            }
          }
        }
      });
      return sheared.get();
    }
    return false;
  }

  public static AxisAlignedBB getAABB(IBlockSource source, ModifierInstance instance) {
    Direction dir = source.getBlockState().get(DispenserBlock.FACING);
    BlockPos sourcePos = source.getBlockPos();
    int level = instance.getLevel() + 1;
    if (!dir.getAxis().isVertical()) {
      BlockPos corner1 = sourcePos.offset(dir, 1).offset(dir.rotateY(), level).offset(Direction.UP, -level);
      BlockPos corner2 = sourcePos.offset(dir, 2 * level + 1).offset(dir.rotateYCCW(), level).offset(Direction.UP, level);
      return new AxisAlignedBB(corner1, corner2);
    }
    return new AxisAlignedBB(sourcePos, sourcePos);
  }

}
