package com.teamacronymcoders.essence.command.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class SetupDevWorldCommand implements Command<CommandSource> {
    private static final SetupDevWorldCommand CMD = new SetupDevWorldCommand();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("setupDev")
            .requires(commandSource -> commandSource.hasPermissionLevel(4))
            .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) {
        CommandSource source = context.getSource();
        MinecraftServer server = source.getServer();
        GameRules rules = server.getGameRules();
        rules.get(GameRules.DO_MOB_SPAWNING).set(false, server);
        rules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
        rules.get(GameRules.DO_WEATHER_CYCLE).set(false, server);
        source.sendFeedback(new TranslationTextComponent("command.essence.setup_dev"), true);
        return setTime(source, 6000);
    }

    public static int setTime(CommandSource source, int time) {
        for (ServerWorld serverworld : source.getServer().getWorlds()) {
            serverworld.setDayTime(time);
        }
        return getDayTime(source.getWorld());
    }

    private static int getDayTime(ServerWorld worldIn) {
        return (int)(worldIn.getDayTime() % 24000L);
    }
}
