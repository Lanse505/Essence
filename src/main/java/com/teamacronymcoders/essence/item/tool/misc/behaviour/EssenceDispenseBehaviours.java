package com.teamacronymcoders.essence.item.tool.misc.behaviour;


import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.entity.GlueBallEntity;
import com.teamacronymcoders.essence.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import java.util.*;
import java.util.stream.Stream;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import org.apache.commons.lang3.tuple.Pair;

public class EssenceDispenseBehaviours {
  public static Map<IItemProvider, IDispenseItemBehavior> dispenserBehaviours = new HashMap<>();
  private static final List<Pair<BlockPos, ItemStack>> honeyToShear = new ArrayList<>();
  private static final List<Pair<LivingEntity, ItemStack>> entitiesToShear = new ArrayList<>();

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
    EventManager.create(TickEvent.WorldTickEvent.class, EventManager.Bus.FORGE).filter((event) -> honeyToShear.size() > 0)
            .process(event -> {
              int count = 0;
              Iterator<Pair<BlockPos, ItemStack>> iterator = honeyToShear.iterator();
              while (count < 7 && iterator.hasNext()) {
                count++;
                Pair<BlockPos, ItemStack> storedPair = iterator.next();
                World world = event.world;
                BlockPos pos = storedPair.getKey();
                BlockState state = world.getBlockState(pos);
                if (state.isIn(BlockTags.BEEHIVES)) {
                  int j = state.get(BeehiveBlock.HONEY_LEVEL);
                  if (j >= 5) {
                    world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    BeehiveBlock.dropHoneyComb(world, pos);
                    ((BeehiveBlock) state.getBlock()).takeHoney(world, state, pos, null, BeehiveTileEntity.State.BEE_RELEASED);
                  }
                }
                iterator.remove();
              }
            });
    EventManager.create(TickEvent.WorldTickEvent.class, EventManager.Bus.FORGE).filter((event) -> entitiesToShear.size() > 0)
            .process(event -> {
              int count = 0;
              Iterator<Pair<LivingEntity, ItemStack>> iterator = entitiesToShear.iterator();
              while (count < 5 && iterator.hasNext()) {
                count++;
                Pair<LivingEntity, ItemStack> storedPair = iterator.next();
                LivingEntity entity = storedPair.getKey();
                ItemStack stack = storedPair.getValue();
                EssenceShear shear = (EssenceShear) storedPair.getValue().getItem();
                shear.itemInteractionForEntity(stack, null, entity, Hand.MAIN_HAND);
                iterator.remove();
              }
            });
  }

  private static boolean shear(IBlockSource source, ItemStack stack) {
    ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
    World world = source.getWorld();
    if (instance != null && !world.isRemote && stack.getItem() instanceof EssenceShear) {
      world.getEntitiesInAABBexcluding(null, getAABB(source, instance), e -> e instanceof LivingEntity && !e.isSpectator()).forEach(entity -> entitiesToShear.add(Pair.of((LivingEntity) entity, stack)));
      return true;
    }
    return false;
  }

  private static boolean shearComb(IBlockSource source, ItemStack stack) {
    ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(stack, EssenceModifierRegistrate.EXPANDER_MODIFIER.get());
    World world = source.getWorld();
    if (instance != null) {
      Stream<BlockPos> checkStream = BlockPos.getAllInBox(getAABB(source, instance));
      Stream<BlockPos> sumStream = BlockPos.getAllInBox(getAABB(source, instance));
      if (!world.isRemote && stack.getItem() instanceof EssenceShear && checkStream.anyMatch(pos -> world.getBlockState(pos).isIn(BlockTags.BEEHIVES))) {
        sumStream.forEach(pos -> honeyToShear.add(Pair.of(pos, stack)));
        return true;
      }
    }
    return false;
  }

  public static AxisAlignedBB getAABB(IBlockSource source, ModifierInstance instance) {
    Direction dir = source.getBlockState().get(DispenserBlock.FACING);
    BlockPos sourcePos = source.getBlockPos();
    Vector3i dirVec = dir.getDirectionVec();
    int level = instance.getLevel();
    Vector3d dirVecXYZ = new Vector3d(sourcePos.offset(dir).getX(), source.getY(), source.getZ()).add(new Vector3d(dirVec.getX(), dirVec.getY(), dirVec.getZ())).scale(level + 1);
    Vector3d vec1 = new Vector3d(-level, -level, -level).add(dirVecXYZ).add(new Vector3d(sourcePos.offset(dir).getX(), sourcePos.offset(dir).getY(), sourcePos.offset(dir).getZ())).add(new Vector3d(dirVec.getX(), dirVec.getY(), dirVec.getZ()).scale(level + 1));
    Vector3d vec2 = new Vector3d(level + 1, level + 1, level + 1).subtract(dirVecXYZ).add(new Vector3d(sourcePos.offset(dir).getX(), sourcePos.offset(dir).getY(), sourcePos.offset(dir).getZ())).subtract(new Vector3d(dirVec.getX(), dirVec.getY(), dirVec.getZ()).scale(level + 1));
    return new AxisAlignedBB(vec1, vec2);
  }

}
