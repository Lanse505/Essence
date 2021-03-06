package com.teamacronymcoders.essence.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceBowHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants.BlockFlags;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SetupDevWorldCommand implements Command<CommandSource> {
  private static final SetupDevWorldCommand CMD = new SetupDevWorldCommand();

  public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
    return Commands.literal("setupDev")
            .requires(commandSource -> commandSource.hasPermissionLevel(4))
            .executes(CMD);
  }

  @Override
  public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
    CommandSource source = context.getSource();
    MinecraftServer server = source.getServer();
    GameRules rules = server.getGameRules();
    // Setup all the Game Rules
    rules.get(GameRules.DO_MOB_SPAWNING).set(false, server);
    rules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
    rules.get(GameRules.DO_WEATHER_CYCLE).set(false, server);
    // Setup dev chest for Testing
    ServerPlayerEntity player = source.asPlayer();
    ServerWorld world = source.getWorld();
    BlockPos pos = player.getPosition().offset(player.getAdjustedHorizontalFacing());
    BlockState state = world.getBlockState(pos);
    BlockState newState = Blocks.CHEST.getDefaultState();
    world.setBlockState(pos, newState, BlockFlags.DEFAULT_AND_RERENDER);
    ChestTileEntity chest = (ChestTileEntity) world.getTileEntity(pos);
    List<ItemStack> stacks = getTestStacks();
    if (chest != null) {
      chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(iItemHandler -> {
        for (ItemStack stack : stacks) {
          ItemHandlerHelper.insertItem(iItemHandler, stack, false);
        }
      });
      chest.markDirty();
    }
    // Send a response to the Player
    source.sendFeedback(new TranslationTextComponent("command.essence.setup_dev"), true);
    // Finally set the time to Noon
    return setTime(source, 6000);
  }

  public List<ItemStack> getTestStacks() {
    List<ItemStack> stacks = Lists.newArrayList();
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_AXE_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.EFFICIENCY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.FIERY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.ENCHANTED_MODIFIER::get, 1, null),
            new ModifierInstance(EssenceModifierRegistrate.CASCADING_LUMBER_MODIFIER::get, 1, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_BOW_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_POWER_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.KEEN_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.BREWED_MODIFIER::get, 1, EssenceBowHelper.createEffectInstanceNBT(
                    new EffectInstance(Effects.POISON, 1000, 2, false, false),
                    new EffectInstance(Effects.WITHER, 1000, 2, false, false),
                    new EffectInstance(Effects.GLOWING, 1000, 2, false, false)
            ))))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_HOE_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.EXPANDER_MODIFIER::get, 3, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_OMNITOOL_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.EFFICIENCY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.UNBREAKING_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.CASCADING_LUMBER_MODIFIER::get, 1, null),
            new ModifierInstance(EssenceModifierRegistrate.CASCADING_EXCAVATION_MODIFIER::get, 1, null),
            new ModifierInstance(EssenceModifierRegistrate.CASCADING_VEIN_MODIFIER::get, 1, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_PICKAXE_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.EFFICIENCY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.FIERY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.ENCHANTED_MODIFIER::get, 1, null),
            new ModifierInstance(EssenceModifierRegistrate.CASCADING_VEIN_MODIFIER::get, 1, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_SHEAR_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.RAINBOW_MODIFIER::get, 1, null),
            new ModifierInstance(EssenceModifierRegistrate.ENCHANTED_MODIFIER::get, 1, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_SHOVEL_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.EFFICIENCY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.FIERY_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.ENCHANTED_MODIFIER::get, 1, null),
            new ModifierInstance(EssenceModifierRegistrate.CASCADING_EXCAVATION_MODIFIER::get, 1, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_SWORD_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.UNBREAKING_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER::get, 5, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_SWORD_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.UNBREAKING_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_ARTHROPOD_MODIFIER::get, 5, null)
            ))
    );
    stacks.add(new ItemStack(EssenceItemRegistrate.ESSENCE_SWORD_DIVINE.get(), 1, EssenceItemstackModifierHelpers.getStackNBTForFillGroup(
            new ModifierInstance(EssenceModifierRegistrate.UNBREAKING_MODIFIER::get, 5, null),
            new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_SMITE_MODIFIER::get, 5, null)
            ))
    );
    return stacks;
  }

  public static int setTime(CommandSource source, int time) {
    for (ServerWorld serverworld : source.getServer().getWorlds()) {
      serverworld.setDayTime(time);
    }
    return getDayTime(source.getWorld());
  }

  private static int getDayTime(ServerWorld worldIn) {
    return (int) (worldIn.getDayTime() % 24000L);
  }
}
