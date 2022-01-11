package com.teamacronymcoders.essence.server.command.argument;

import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import com.teamacronymcoders.essence.server.command.argument.extendable.EssenceRegistryArgumentType;

public class EssenceModifierArgumentType extends EssenceRegistryArgumentType<IModifier<?>> {

    public EssenceModifierArgumentType() {
        super(EssenceModifierRegistrate.REGISTRY.get());
    }

    public static EssenceModifierArgumentType modifier() {
        return new EssenceModifierArgumentType();
    }

    public static IModifier getModifier(final CommandContext<?> context, final String paramName) {
        return context.getArgument(paramName, IModifier.class);
    }

}
