package com.teamacronymcoders.essence.command.argument;

import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.essence.command.argument.extendable.EssenceEnumArgumentType;
import net.minecraft.util.Hand;

public class EssenceHandArgumentType extends EssenceEnumArgumentType<Hand> {

  public EssenceHandArgumentType () {
    super(Hand.class);
  }

  public static EssenceHandArgumentType hand () {
    return new EssenceHandArgumentType();
  }

  public static Hand getHand (final CommandContext<?> context, final String paramName) {
    return context.getArgument(paramName, Hand.class);
  }
}
