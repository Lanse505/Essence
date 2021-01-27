package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.IModifierHolder;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.registrate.EssenceLootTableRegistrate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

public class EssenceShearingHelper {

  public static ActionResultType handleIShearableEntity(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand) {
    if (sheared instanceof IForgeShearable) {
      IForgeShearable target = (IForgeShearable) sheared;
      BlockPos pos = new BlockPos(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
      if (target.isShearable(stack, sheared.world, pos)) {
        List<ItemStack> dropList = sheared instanceof SheepEntity ? handleShearingSheep((SheepEntity) sheared, stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)) : target.onSheared(player, stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

        // Gathers a list of Entries with InteractionCoreModifiers that also return a different value than the default
        List<? extends ModifierInstance> unchecked = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(IModifierHolder::getModifierInstances).orElse(new ArrayList<>());

        // Loops over and Gathers the final modified list
        for (ModifierInstance instance : unchecked) {
          if (instance.getModifier() instanceof ItemInteractionCoreModifier) {
            ItemInteractionCoreModifier interactionCoreModifier = (ItemInteractionCoreModifier) instance.getModifier();
            dropList = interactionCoreModifier.onShearedAltered(stack, player, sheared, hand, dropList, instance);
          }
        }

        // Handle dropping the final list of ItemStacks
        dropList.forEach(s -> {
          ItemEntity entity = sheared.entityDropItem(s);
          entity.setMotion(entity.getMotion().add(
                  (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                  Essence.RANDOM.nextFloat() * 0.05F,
                  (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
        });

        // Damage the ItemStack
        stack.damageItem(1, sheared, entity -> entity.sendBreakAnimation(hand));
        return ActionResultType.SUCCESS;
      }
    }
    return ActionResultType.FAIL;
  }

  private static List<ItemStack> handleShearingSheep(SheepEntity sheep, ItemStack item, IWorld world, BlockPos pos, int fortune) {
    List<ItemStack> ret = new ArrayList<>();
    if (!sheep.world.isRemote) {
      sheep.setSheared(true);
      int i = EssenceUtilHelper.nextIntInclusive(1 + fortune, 4 + fortune);
      for (int j = 0; j < i; ++j) {
        ret.add(new ItemStack(SheepEntity.WOOL_BY_COLOR.get(sheep.getFleeceColor())));
      }
    }
    sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    return ret;
  }

  public static ActionResultType handleHardcodedEntity(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand) {
    if (sheared instanceof HorseEntity) {
      List<ItemStack> loot = new ArrayList<>();
      if (!player.getEntityWorld().isRemote()) {
        LootTableManager manager = player.world.getServer().getLootTableManager();
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        LootContext context = new LootContext.Builder(world).withParameter(LootParameters.THIS_ENTITY, sheared).withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.GENERIC).withParameter(LootParameters.field_237457_g_, sheared.getPositionVec()).withLuck(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)).withSeed(world.getSeed()).build(LootParameterSets.ENTITY);
        loot = manager.getLootTableFromLocation(EssenceLootTableRegistrate.SHEARING_HORSE).generate(context);
      }
      // Handle dropping the final list of ItemStacks
      loot.forEach(s -> {
        ItemEntity entity = sheared.entityDropItem(s);
        entity.setMotion(entity.getMotion().add(
                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                Essence.RANDOM.nextFloat() * 0.05F,
                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
      });
      // Damage the ItemStack
      stack.damageItem(1, sheared, entity -> entity.sendBreakAnimation(hand));

      ZombieHorseEntity zombie = new ZombieHorseEntity(EntityType.ZOMBIE_HORSE, sheared.world);
      zombie.setPosition(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
      zombie.getEntityWorld().addEntity(zombie);
      sheared.remove();

    } else if (sheared instanceof ZombieHorseEntity) {
      List<ItemStack> loot = new ArrayList<>();
      if (!player.getEntityWorld().isRemote()) {
        LootTableManager manager = player.world.getServer().getLootTableManager();
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        LootContext context = new LootContext.Builder(world).withParameter(LootParameters.THIS_ENTITY, sheared).withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.GENERIC).withParameter(LootParameters.field_237457_g_, sheared.getPositionVec()).withLuck(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)).withSeed(world.getSeed()).build(LootParameterSets.ENTITY);
        loot = manager.getLootTableFromLocation(EssenceLootTableRegistrate.SHEARING_ZOMBIE_HORSE).generate(context);
      }
      // Handle dropping the final list of ItemStacks
      loot.forEach(s -> {
        ItemEntity entity = sheared.entityDropItem(s);
        entity.setMotion(entity.getMotion().add(
                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                Essence.RANDOM.nextFloat() * 0.05F,
                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
      });
      // Damage the ItemStack
      stack.damageItem(1, sheared, entity -> entity.sendBreakAnimation(hand));

      SkeletonHorseEntity skeleton = new SkeletonHorseEntity(EntityType.SKELETON_HORSE, sheared.world);
      skeleton.setPosition(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
      skeleton.getEntityWorld().addEntity(skeleton);
      sheared.remove();

    } else if (sheared instanceof ZombieEntity) {
      List<ItemStack> loot = new ArrayList<>();
      if (!player.getEntityWorld().isRemote()) {
        LootTableManager manager = player.world.getServer().getLootTableManager();
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        LootContext context = new LootContext.Builder(world).withParameter(LootParameters.THIS_ENTITY, sheared).withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.GENERIC).withParameter(LootParameters.field_237457_g_, sheared.getPositionVec()).withLuck(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)).withSeed(world.getSeed()).build(LootParameterSets.ENTITY);
        loot = manager.getLootTableFromLocation(EssenceLootTableRegistrate.SHEARING_ZOMBIE).generate(context);
      }
      // Handle dropping the final list of ItemStacks
      loot.forEach(s -> {
        ItemEntity entity = sheared.entityDropItem(s);
        entity.setMotion(entity.getMotion().add(
                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                Essence.RANDOM.nextFloat() * 0.05F,
                (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
      });
      // Damage the ItemStack
      stack.damageItem(1, sheared, entity -> entity.sendBreakAnimation(hand));

      SkeletonEntity skeleton = new SkeletonEntity(EntityType.SKELETON, sheared.world);
      skeleton.setPosition(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
      skeleton.getEntityWorld().addEntity(skeleton);
      sheared.remove();
    }

    return ActionResultType.FAIL;
  }
}
