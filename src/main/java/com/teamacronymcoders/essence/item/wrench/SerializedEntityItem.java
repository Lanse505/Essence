package com.teamacronymcoders.essence.item.wrench;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.wrench.EntityStorageProvider;
import com.teamacronymcoders.essence.client.render.tesr.itemstack.SerializableMobRenderer;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class SerializedEntityItem extends Item {

  public SerializedEntityItem (Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt) {
    return new EntityStorageProvider(stack);
  }

  @Override
  public ActionResultType onItemUse (ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    World world = context.getWorld();
    ItemStack stack = context.getItem();
    if ((player != null && player.getEntityWorld().isRemote) || !containsEntity(stack)) {
      return ActionResultType.FAIL;
    }
    return stack.getCapability(EssenceCoreCapability.ENTITY_STORAGE).map(storage -> {
      LivingEntity entity = storage.getEntity(stack.getTag(), world);
      BlockPos spawnPosition = context.getPos().offset(context.getFace());
      if (entity != null) {
        Essence.LOGGER.info(stack.getTag());
        stack.shrink(1);
        entity.setPositionAndRotation(spawnPosition.getX() + 0.5, spawnPosition.getY(), spawnPosition.getZ() + 0.5, 0, 0);
        world.addEntity(entity);
        return ActionResultType.SUCCESS;
      }
      return ActionResultType.PASS;
    }).orElse(ActionResultType.PASS);
  }

  @SuppressWarnings("ConstantConditions")
  public boolean containsEntity (ItemStack stack) {
    return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
  }

}
