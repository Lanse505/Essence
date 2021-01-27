package com.teamacronymcoders.essence.command.argument;

import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.command.argument.extendable.EssenceRegistryArgumentType;
import com.teamacronymcoders.essence.registrate.EssenceModifierRegistrate;

public class EssenceModifierArgumentType extends EssenceRegistryArgumentType<Modifier> {

  public EssenceModifierArgumentType() {
    super(EssenceModifierRegistrate.REGISTRY.get());
  }

  public static EssenceModifierArgumentType modifier() {
    return new EssenceModifierArgumentType();
  }

  public static Modifier getModifier(final CommandContext<?> context, final String paramName) {
    return context.getArgument(paramName, Modifier.class);
  }

}
