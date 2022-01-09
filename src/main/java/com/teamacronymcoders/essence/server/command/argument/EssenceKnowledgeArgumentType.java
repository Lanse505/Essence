package com.teamacronymcoders.essence.server.command.argument;

import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.compat.registrate.EssenceKnowledgeRegistrate;
import com.teamacronymcoders.essence.server.command.argument.extendable.EssenceRegistryArgumentType;

public class EssenceKnowledgeArgumentType extends EssenceRegistryArgumentType<Knowledge> {

    public EssenceKnowledgeArgumentType() {
        super(EssenceKnowledgeRegistrate.REGISTRY.get());
    }

    public static EssenceKnowledgeArgumentType knowledge() {
        return new EssenceKnowledgeArgumentType();
    }

    public static Knowledge getKnowledge(final CommandContext<?> context, final String paramName) {
        return context.getArgument(paramName, Knowledge.class);
    }
}
