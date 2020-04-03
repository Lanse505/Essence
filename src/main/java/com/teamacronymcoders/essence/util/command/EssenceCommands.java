package com.teamacronymcoders.essence.util.command;

import com.mojang.brigadier.CommandDispatcher;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.command.impl.ItemStackModifierCommand;
import com.teamacronymcoders.essence.util.command.impl.KnowledgeCommand;
import com.teamacronymcoders.essence.util.command.impl.RegistryCommand;
import com.teamacronymcoders.essence.util.command.impl.SetupDevWorldCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class EssenceCommands {

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
            Commands.literal(Essence.MODID)
                .then(SetupDevWorldCommand.register(dispatcher))
                .then(KnowledgeCommand.register(dispatcher))
                .then(ItemStackModifierCommand.register(dispatcher))
                .then(RegistryCommand.register(dispatcher))
        );
    }

}
