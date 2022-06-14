package com.teamacronymcoders.essence.common.modifier.item.interaction.cascading;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.modifier.item.interaction.ExpanderModifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceBlockHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceWorldHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

public class CascadingModifier extends ItemInteractionModifier {

    private final CascadingType type;

    public CascadingModifier(CascadingType type) {
        super();
        this.type = type;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner, ModifierInstance instance) {
        if (state.m_204336_(this.type.getBlockTag())) {
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
        return super.mineBlock(stack, level, state, pos, miner, instance);
    }

    @Override
    public String getTranslationName() {
        return "com.teamacronymcoders.essenceapi.modifier.essence.cascading";
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        return stack.m_204117_(this.type.getToolTag());
    }


    @Override
    public boolean canApplyTogether(ItemStack stack, IModifier<ItemStack> modifier) {
        return !(modifier instanceof CascadingModifier) && !(modifier instanceof ExpanderModifier);
    }

    @Override
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return Component.translatable(getTranslationName(), Component.translatable("essence.cascading.type." + this.type.getName()));
        }
        return super.getTextComponentName(level);
    }

    @Override
    public List<Component> getRenderedText(ModifierInstance instance) {
        List<Component> textComponents = new ArrayList<>();
        textComponents.add(Component.literal("  ").append(Component.translatable(getTranslationName(), Component.translatable("essence.cascading.type." + this.type.getName()).withStyle(this.type.getFormatting())).withStyle(ChatFormatting.GRAY)));
        return textComponents;
    }

    public CascadingType getCascadingType() {
        return this.type;
    }
}
