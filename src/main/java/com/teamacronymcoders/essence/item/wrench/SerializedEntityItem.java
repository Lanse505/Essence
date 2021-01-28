package com.teamacronymcoders.essence.item.wrench;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class SerializedEntityItem extends Item {

  public SerializedEntityItem(Properties properties) {
    super(properties);
  }

  @Nullable
  public static LivingEntity getEntityFromNBT(CompoundNBT nbt, World world) {
    if (nbt.contains("entity")) {
      EntityType type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryCreate(nbt.getString("entity")));
      if (type != null) {
        Entity entity = type.create(world);
        if (entity instanceof LivingEntity) {
          entity.read(nbt);
          return (LivingEntity) entity;
        }
        return null;
      }
    }
    return null;
  }

  @SuppressWarnings("ConstantConditions")
  public boolean containsEntity(ItemStack stack) {
    return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    World world = context.getWorld();
    ItemStack stack = context.getItem();
    if ((player != null && player.getEntityWorld().isRemote) || !containsEntity(stack)) {
      return ActionResultType.FAIL;
    }

    LivingEntity entity = getEntityFromNBT(stack.getTag(), world);
    BlockPos spawnPosition = context.getPos().offset(context.getFace());
    if (entity != null) {
      stack.shrink(1);
      entity.setPositionAndRotation(spawnPosition.getX() + 0.5, spawnPosition.getY(), spawnPosition.getZ() + 0.5, 0, 0);
      world.addEntity(entity);
    }
    return ActionResultType.SUCCESS;
  }

}
