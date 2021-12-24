package com.teamacronymcoders.essence.modifier.item.interaction.cascading;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.ExpanderModifier;
import com.teamacronymcoders.essence.util.helper.EssenceBlockHelper;
import com.teamacronymcoders.essence.util.helper.EssenceWorldHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class CascadingModifier extends ItemInteractionCoreModifier {

  private final CascadingType type;

  public CascadingModifier(CascadingType type) {
    super();
    this.type = type;
  }

  @Override
  public boolean onBlockDestroyed(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
    if (state.is(this.type.getBlockTag())) {
      if (miner instanceof Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
          List<BlockPos> found = EssenceBlockHelper.findPositions(state, pos, level, this.type);
          for (BlockPos foundPos : found) {
            if (pos.equals(foundPos)) {
              continue;
            }
            BlockState foundState = level.getBlockState(foundPos);
            int exp = ForgeHooks.onBlockBreakEvent(level, serverPlayer.gameMode.getGameModeForPlayer(), serverPlayer, foundPos);
            if (exp == -1) {
              // If we can't actually break the block continue (this allows mods to stop us from vein mining into protected land)
              continue;
            }
            Block block = foundState.getBlock();
            BlockEntity tile = EssenceWorldHelper.getBlockEntity(level, pos);
            boolean removed = foundState.onDestroyedByPlayer(level, foundPos, player, true, level.getFluidState(foundPos));
            if (removed) {
              block.destroy(level, foundPos, foundState);
              block.playerDestroy(level, player, foundPos, foundState, tile, stack);
              player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
              if (exp > 0) {
                if (!level.isClientSide()) {
                  block.popExperience((ServerLevel) level, foundPos, exp);
                }
              }
            }
          }
        }
      } else {
        List<BlockPos> found = EssenceBlockHelper.findPositions(state, pos, level, this.type);
        for (BlockPos foundPos : found) {
          if (!pos.equals(foundPos)) {
            EssenceWorldHelper.breakBlock(level, foundPos, true, miner, stack);
          }
        }
      }
    }
    return super.onBlockDestroyed(stack, level, state, pos, miner, instance);
  }

  @Override
  public String getTranslationName() {
    return "modifier.essence.cascading";
  }

  @Override
  public boolean canApplyOnObject(ItemStack stack) {
    return stack.is(this.type.getToolTag());
  }


  @Override
  public boolean canApplyTogether(IModifier modifier) {
    return !(modifier instanceof CascadingModifier) && !(modifier instanceof ExpanderModifier);
  }

  @Override
  public Component getTextComponentName(int level) {
    if (level == -1) {
      return new TranslatableComponent(getTranslationName(), new TranslatableComponent("essence.cascading.type." + this.type.getName()));
    }
    return super.getTextComponentName(level);
  }

  @Override
  public List<Component> getRenderedText(ModifierInstance instance) {
    List<Component> textComponents = new ArrayList<>();
    textComponents.add(new TextComponent("  ").append(new TranslatableComponent(getTranslationName(), new TranslatableComponent("essence.cascading.type." + this.type.getName()).withStyle(this.type.getFormatting())).withStyle(ChatFormatting.GRAY)));
    return textComponents;
  }

  public CascadingType getCascadingType() {
    return this.type;
  }
}
