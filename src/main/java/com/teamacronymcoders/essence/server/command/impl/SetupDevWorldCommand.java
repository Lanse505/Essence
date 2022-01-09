package com.teamacronymcoders.essence.server.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.common.util.helper.EssenceBowHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class SetupDevWorldCommand implements Command<CommandSourceStack> {
    private static final SetupDevWorldCommand CMD = new SetupDevWorldCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("setupDev")
                .requires(commandSource -> commandSource.hasPermission(4))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        MinecraftServer server = source.getServer();
        GameRules rules = server.getGameRules();
        // Setup all the Game Rules
        rules.getRule(GameRules.RULE_DOMOBSPAWNING).set(false, server);
        rules.getRule(GameRules.RULE_DAYLIGHT).set(false, server);
        rules.getRule(GameRules.RULE_WEATHER_CYCLE).set(false, server);
        // Setup dev chest for Testing
        ServerPlayer player = source.getPlayerOrException();
        ServerLevel world = source.getLevel();
        BlockPos pos = player.getOnPos().offset(player.getMotionDirection().getNormal());
        BlockState state = world.getBlockState(pos);
        BlockState newState = Blocks.CHEST.defaultBlockState();
        world.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(pos);
        List<ItemStack> stacks = getTestStacks();
        if (chest != null) {
            chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(iItemHandler -> {
                for (ItemStack stack : stacks) {
                    ItemHandlerHelper.insertItem(iItemHandler, stack, false);
                }
            });
            chest.setChanged();
        }
        // Send a response to the Player
        source.sendSuccess(new TranslatableComponent("command.essence.setup_dev"), true);
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
                        new MobEffectInstance(MobEffects.POISON, 1000, 2, false, false),
                        new MobEffectInstance(MobEffects.WITHER, 1000, 2, false, false),
                        new MobEffectInstance(MobEffects.GLOWING, 1000, 2, false, false)
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

    public static int setTime(CommandSourceStack source, int time) {
        for (ServerLevel serverLevel : source.getServer().getAllLevels()) {
            serverLevel.setDayTime(time);
        }
        return getDayTime(source.getLevel());
    }

    private static int getDayTime(ServerLevel level) {
        return (int) (level.getDayTime() % 24000L);
    }
}
