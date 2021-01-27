package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemInteractionCoreModifier extends ItemAttributeModifier {

  public ItemInteractionCoreModifier() {
    super(1);
  }

  public ItemInteractionCoreModifier(int maxLevel) {
    super(maxLevel);
  }

  public ItemInteractionCoreModifier(int maxLevel, int minLevel) {
    super(maxLevel, minLevel);
  }

  public ItemInteractionCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, operation);
  }

  public ItemInteractionCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, maxLevel, operation);
  }

  public ItemInteractionCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, int minLevel, AttributeModifier.Operation operation) {
    super(attribute, identifier, uuid, amount, maxLevel, minLevel, operation);
  }

  public ActionResultType onItemUse(ItemUseContext context, ModifierInstance instance) {
    return ActionResultType.PASS;
  }

  public boolean onHitEntity(ItemStack stack, LivingEntity entity, LivingEntity player, ModifierInstance instance) {
    return false;
  }

  public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
    return false;
  }

  public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
  }

  public List<ItemStack> onShearedAltered(ItemStack stack, @Nullable PlayerEntity player, LivingEntity sheared, Hand hand, List<ItemStack> stackList, ModifierInstance instance) {
    return stackList;
  }

}
