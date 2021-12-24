package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

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

  public InteractionResult onItemUse(UseOnContext context, ModifierInstance instance) {
    return InteractionResult.PASS;
  }

  public boolean onHitEntity(ItemStack stack, LivingEntity entity, LivingEntity player, ModifierInstance instance) {
    return false;
  }

  public boolean onBlockDestroyed(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
    return false;
  }

  public void onInventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
  }

  public List<ItemStack> onShearedAltered(ItemStack stack, @Nullable Player player, LivingEntity sheared, InteractionHand hand, List<ItemStack> stackList, ModifierInstance instance) {
    return stackList;
  }

}
