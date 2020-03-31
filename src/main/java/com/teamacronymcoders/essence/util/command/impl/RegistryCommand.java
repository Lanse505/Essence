package com.teamacronymcoders.essence.util.command.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.util.registration.EssenceRegistries;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

public class RegistryCommand implements Command<CommandSource> {

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("registry")
            .requires(cs -> cs.hasPermissionLevel(4))
            .then(Commands.literal("dump")
                    .then(Commands.literal("knowledge")
                            .executes(RegistryCommand::dumpKnowledgeRegistry)
                        )
                    .then(Commands.literal("modifiers")
                            .executes(RegistryCommand::dumpModifierRegistry)
                        )
            );
        }

    public static int dumpKnowledgeRegistry(CommandContext<CommandSource> context) {
        CommandSource source = context.getSource();
        source.sendFeedback(new TranslationTextComponent("command.essence.registry.dump.knowledge"), true);
        for (Map.Entry<ResourceLocation, Knowledge<?>> knowledge : EssenceRegistries.KNOWLEDGE.getEntries()) {
            Essence.LOGGER.info(new TranslationTextComponent("command.essence.registry.dump.knowledge.type", new TranslationTextComponent(knowledge.getValue().getTranslationString()), knowledge.getValue().getTypeString(), knowledge.getKey().toString()).getUnformattedComponentText());
        }
        return 1;
    }

    public static int dumpModifierRegistry(CommandContext<CommandSource> context) {
        CommandSource source = context.getSource();
        source.sendFeedback(new TranslationTextComponent("command.essence.registry.dump.modifier"), true);
        for (Map.Entry<ResourceLocation, Modifier<?>> knowledge : EssenceRegistries.MODIFIER.getEntries()) {
            Essence.LOGGER.info(new TranslationTextComponent("command.essence.registry.dump.modifier.type", knowledge.getValue().getType().getSimpleName(), knowledge.getKey().toString()).getUnformattedComponentText());
        }
        return 1;
    }


    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return 1;
    }
}
