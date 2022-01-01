package com.teamacronymcoders.essence.server.command.argument;

import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.essence.server.command.argument.extendable.EssenceEnumArgumentType;
import net.minecraft.world.InteractionHand;

public class EssenceHandArgumentType extends EssenceEnumArgumentType<InteractionHand> {

    public EssenceHandArgumentType() {
        super(InteractionHand.class);
    }

    public static EssenceHandArgumentType hand() {
        return new EssenceHandArgumentType();
    }

    public static InteractionHand getHand(final CommandContext<?> context, final String paramName) {
        return context.getArgument(paramName, InteractionHand.class);
    }
}
