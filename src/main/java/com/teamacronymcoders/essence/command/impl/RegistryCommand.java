package com.teamacronymcoders.essence.command.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.registrate.EssenceKnowledgeRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;

import java.util.Map;

public class RegistryCommand implements Command<CommandSourceStack> {

  public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
    return Commands.literal("registry")
            .requires(cs -> cs.hasPermission(4))
            .then(Commands.literal("dump")
                    .then(Commands.literal("knowledge")
                            .executes(RegistryCommand::dumpKnowledgeRegistry)
                    )
                    .then(Commands.literal("modifiers")
                            .executes(RegistryCommand::dumpModifierRegistry)
                    )
            );
  }

  public static int dumpKnowledgeRegistry(CommandContext<CommandSourceStack> context) {
    CommandSourceStack source = context.getSource();
    source.sendSuccess(new TranslatableComponent("command.essence.registry.dump.knowledge"), true);
    for (Map.Entry<ResourceKey<Knowledge>, Knowledge> knowledge : EssenceKnowledgeRegistrate.REGISTRY.get().getEntries()) {
      Essence.LOGGER.info(new TranslatableComponent("command.essence.registry.dump.knowledge.type", new TranslatableComponent(knowledge.getValue().getTranslationString()), knowledge.getKey().toString()).getKey());
    }
    return 1;
  }

  public static int dumpModifierRegistry(CommandContext<CommandSourceStack> context) {
    CommandSourceStack source = context.getSource();
    source.sendSuccess(new TranslatableComponent("command.essence.registry.dump.modifier"), true);
    for (Map.Entry<ResourceKey<Modifier>, Modifier> knowledge : EssenceModifierRegistrate.REGISTRY.get().getEntries()) {
      Essence.LOGGER.info(new TranslatableComponent("command.essence.registry.dump.modifier.type", knowledge.getValue(), knowledge.getKey().toString()).getKey());
    }
    return 1;
  }


  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    return 1;
  }
}
