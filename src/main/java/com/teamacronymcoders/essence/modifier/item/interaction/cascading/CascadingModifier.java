package com.teamacronymcoders.essence.modifier.item.interaction.cascading;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.ExpanderModifier;
import com.teamacronymcoders.essence.util.helper.EssenceBlockHelper;
import com.teamacronymcoders.essence.util.helper.EssenceWorldHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

public class CascadingModifier extends ItemInteractionCoreModifier {

  private final CascadingType type;

  public CascadingModifier(CascadingType type) {
    super();
    this.type = type;
  }

  @Override
  public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
    if (state.getBlock().isIn(this.type.getBlockTag())) {
      if (miner instanceof PlayerEntity) {
        PlayerEntity player = (PlayerEntity) miner;
        if (player instanceof ServerPlayerEntity) {
          ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
          List<BlockPos> found = EssenceBlockHelper.findPositions(state, pos, world, this.type);
          for (BlockPos foundPos : found) {
            if (pos.equals(foundPos)) {
              continue;
            }
            BlockState foundState = world.getBlockState(foundPos);
            int exp = ForgeHooks.onBlockBreakEvent(world, serverPlayer.interactionManager.getGameType(), serverPlayer, foundPos);
            if (exp == -1) {
              // If we can't actually break the block continue (this allows mods to stop us from vein mining into protected land)
              continue;
            }
            Block block = foundState.getBlock();
            TileEntity tile = EssenceWorldHelper.getTileEntity(world, pos);
            boolean removed = foundState.removedByPlayer(world, foundPos, player, true, world.getFluidState(foundPos));
            if (removed) {
              block.onPlayerDestroy(world, foundPos, foundState);
              block.harvestBlock(world, player, foundPos, foundState, tile, stack);
              player.addStat(Stats.ITEM_USED.get(stack.getItem()));
              if (exp > 0) {
                if (!world.isRemote) {
                  block.dropXpOnBlockBreak((ServerWorld) world, foundPos, exp);
                }
              }
            }
          }
        }
      } else {
        List<BlockPos> found = EssenceBlockHelper.findPositions(state, pos, world, this.type);
        for (BlockPos foundPos : found) {
          if (!pos.equals(foundPos)) {
            EssenceWorldHelper.breakBlock(world, foundPos, true, miner, stack);
          }
        }
      }
    }
    return super.onBlockDestroyed(stack, world, state, pos, miner, instance);
  }

  @Override
  public String getTranslationName() {
    return "modifier.essence.cascading";
  }

  @Override
  public boolean canApplyOnObject(ItemStack stack) {
    return stack.getItem().isIn(this.type.getToolTag());
  }


  @Override
  public boolean canApplyTogether(IModifier modifier) {
    return !(modifier instanceof CascadingModifier) && !(modifier instanceof ExpanderModifier);
  }

  @Override
  public ITextComponent getTextComponentName(int level) {
    if (level == -1) {
      return new TranslationTextComponent(getTranslationName(), new TranslationTextComponent("essence.cascading.type." + this.type.getName()));
    }
    return super.getTextComponentName(level);
  }

  @Override
  public List<ITextComponent> getRenderedText(ModifierInstance instance) {
    List<ITextComponent> textComponents = new ArrayList<>();
    textComponents.add(new StringTextComponent("  ").append(new TranslationTextComponent(getTranslationName(), new TranslationTextComponent("essence.cascading.type." + this.type.getName()).mergeStyle(this.type.getFormatting())).mergeStyle(TextFormatting.GRAY)));
    return textComponents;
  }

  public CascadingType getCascadingType() {
    return this.type;
  }
}
