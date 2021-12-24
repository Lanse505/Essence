package com.teamacronymcoders.essence.command;

import com.mojang.brigadier.CommandDispatcher;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.command.impl.ItemStackModifierCommand;
import com.teamacronymcoders.essence.command.impl.KnowledgeCommand;
import com.teamacronymcoders.essence.command.impl.RegistryCommand;
import com.teamacronymcoders.essence.command.impl.SetupDevWorldCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class EssenceCommands {

  public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
    dispatcher.register(
            Commands.literal(Essence.MOD_ID)
                    .then(SetupDevWorldCommand.register(dispatcher))
                    .then(KnowledgeCommand.register(dispatcher))
                    .then(ItemStackModifierCommand.register(dispatcher))
                    .then(RegistryCommand.register(dispatcher))
    );
  }

}
