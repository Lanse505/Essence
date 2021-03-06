package com.teamacronymcoders.essence.command.argument;

import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.command.argument.extendable.EssenceRegistryArgumentType;
import com.teamacronymcoders.essence.registrate.EssenceKnowledgeRegistrate;

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
