package com.teamacronymcoders.essence.command.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.command.argument.EssenceKnowledgeArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KnowledgeCommand implements Command<CommandSourceStack> {

  public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
    return Commands.literal("knowledge")
            .requires(cs -> cs.hasPermission(4))
            .then(Commands.literal("add")
                    .then(Commands.argument("target", EntityArgument.player())
                            .then(Commands.argument("knowledge", EssenceKnowledgeArgumentType.knowledge())
                                    .executes(context -> addPlayerKnowledge(context, EntityArgument.getPlayer(context, "target"), EssenceKnowledgeArgumentType.getKnowledge(context, "knowledge"))
                                    )
                            )
                    )
                    .then(Commands.argument("uuid", StringArgumentType.string())
                            .then(Commands.argument("knowledge", EssenceKnowledgeArgumentType.knowledge())
                                    .executes(context -> addPlayerKnowledge(context, StringArgumentType.getString(context, "uuid"), EssenceKnowledgeArgumentType.getKnowledge(context, "knowledge"))
                                    )
                            )
                    )
            )
            .then(Commands.literal("remove")
                    .then(Commands.argument("target", EntityArgument.player())
                            .then(Commands.argument("knowledge", EssenceKnowledgeArgumentType.knowledge())
                                    .executes(context -> removePlayerKnowledge(context, EntityArgument.getPlayer(context, "target"), EssenceKnowledgeArgumentType.getKnowledge(context, "knowledge"))
                                    )
                            )
                    )
                    .then(Commands.argument("uuid", StringArgumentType.string())
                            .then(Commands.argument("knowledge", EssenceKnowledgeArgumentType.knowledge())
                                    .executes(context -> removePlayerKnowledge(context, StringArgumentType.getString(context, "uuid"), EssenceKnowledgeArgumentType.getKnowledge(context, "knowledge"))
                                    )
                            )
                    )
            )
            .then(Commands.literal("clear")
                    .then(Commands.argument("target", EntityArgument.player())
                            .executes(context -> clearPlayerKnowledge(context, EntityArgument.getPlayer(context, "target"))
                            )
                    )
                    .then(Commands.argument("uuid", StringArgumentType.string())
                            .executes(context -> clearPlayerKnowledge(context, StringArgumentType.getString(context, "uuid"))
                            )
                    )
            )
            .then(Commands.literal("dump")
                    .then(Commands.argument("target", EntityArgument.player())
                            .then(Commands.literal("dumpAll")
                                    .executes(context -> dumpAllPlayerKnowledge(context, EntityArgument.getPlayer(context, "target"))
                                    )
                            )
                            .then(Commands.literal("dumpSome")
                                    .then(Commands.argument("amount", IntegerArgumentType.integer())
                                            .executes(context -> dumpSomePlayerKnowledge(context, EntityArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "amount"))
                                            )
                                    )
                            )
                            .then(Commands.argument("uuid", StringArgumentType.string())
                                    .then(Commands.literal("dumpAll")
                                            .executes(context -> dumpAllPlayerKnowledge(context, StringArgumentType.getString(context, "uuid"))
                                            )
                                    )
                                    .then(Commands.literal("dumpSome")
                                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                    .executes(context -> dumpSomePlayerKnowledge(context, StringArgumentType.getString(context, "uuid"), IntegerArgumentType.getInteger(context, "amount")))
                                            )
                                    )
                            )
                    ));
  }

  public static int addPlayerKnowledge(CommandContext<CommandSourceStack> context, Player playerEntity, Knowledge knowledge) {
    CommandSourceStack source = context.getSource();
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.addKnowledge(playerEntity, knowledge);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.add", new TranslatableComponent(knowledge.getTranslationString()), playerEntity.getName()), true);
    });
    return 1;
  }

  public static int addPlayerKnowledge(CommandContext<CommandSourceStack> context, String playerUUID, Knowledge knowledge) {
    CommandSourceStack source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.addKnowledge(uuid, knowledge);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.add", new TranslatableComponent(knowledge.getTranslationString()), uuid.toString()), true);
    });
    return 1;
  }

  public static int removePlayerKnowledge(CommandContext<CommandSourceStack> context, Player playerEntity, Knowledge knowledge) {
    CommandSourceStack source = context.getSource();
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.removeKnowledge(playerEntity, knowledge);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.remove", new TranslatableComponent(knowledge.getTranslationString()), playerEntity.getName()), true);
    });
    return 1;
  }

  public static int removePlayerKnowledge(CommandContext<CommandSourceStack> context, String playerUUID, Knowledge knowledge) {
    CommandSourceStack source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.removeKnowledge(uuid, knowledge);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.remove", new TranslatableComponent(knowledge.getTranslationString()), uuid.toString()), true);
    });
    return 1;
  }

  public static int clearPlayerKnowledge(CommandContext<CommandSourceStack> context, Player playerEntity) {
    CommandSourceStack source = context.getSource();
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.clearKnowledge(playerEntity.getUUID());
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.clear", playerEntity.getName()), true);
    });
    return 1;
  }

  public static int clearPlayerKnowledge(CommandContext<CommandSourceStack> context, String playerUUID) {
    CommandSourceStack source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.clearKnowledge(uuid);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.clear", uuid.toString()), true);
    });
    return 1;
  }

  public static int dumpAllPlayerKnowledge(CommandContext<CommandSourceStack> context, Player player) {
    CommandSourceStack source = context.getSource();
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      List<Knowledge> knowledges = holder.getKnowledgeAsList(player.getUUID());
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.dump", player.getName()), true);
      knowledges.forEach(knowledge -> {
        source.sendSuccess(new TranslatableComponent("command.essence.generic.dump.knowledge", new TranslatableComponent(knowledge.getTranslationString())), true);
      });
    });
    return 1;
  }

  public static int dumpAllPlayerKnowledge(CommandContext<CommandSourceStack> context, String playerUUID) {
    CommandSourceStack source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    Level level = source.getLevel();
    level.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      List<Knowledge> knowledges = holder.getKnowledgeAsList(uuid);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.dump", uuid.toString()), true);
      knowledges.forEach(knowledge -> {
        source.sendSuccess(new TranslatableComponent("command.essence.generic.dump.knowledge", new TranslatableComponent(knowledge.getTranslationString())), true);
      });
    });
    return 1;
  }

  public static int dumpSomePlayerKnowledge(CommandContext<CommandSourceStack> context, Player player, int amountToDump) {
    CommandSourceStack source = context.getSource();
    Level world = source.getLevel();
    List<Knowledge> knowledges = world.getCapability(EssenceCapability.KNOWLEDGE).map(holder -> holder.getKnowledgeAsList(player.getUUID())).orElse(new ArrayList<>());
    source.sendSuccess(new TranslatableComponent("command.essence.knowledge.dump", player.getName()), true);
    for (int i = 0; i < amountToDump; i++) {
      Knowledge knowledge = knowledges.get(i);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.dump.type"), true);
      source.sendSuccess(new TranslatableComponent(knowledge.getTranslationString()), true);
    }
    return 1;
  }

  public static int dumpSomePlayerKnowledge(CommandContext<CommandSourceStack> context, String playerUUID, int amountToDump) {
    CommandSourceStack source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    Level world = source.getLevel();
    List<Knowledge> knowledges = world.getCapability(EssenceCapability.KNOWLEDGE).map(holder -> holder.getKnowledgeAsList(uuid)).orElse(new ArrayList<>());
    source.sendSuccess(new TranslatableComponent("command.essence.knowledge.dump", uuid.toString()), true);
    for (int i = 0; i < amountToDump; i++) {
      Knowledge knowledge = knowledges.get(i);
      source.sendSuccess(new TranslatableComponent("command.essence.knowledge.dump.type"), true);
      source.sendSuccess(new TranslatableComponent(knowledge.getTranslationString()), true);
    }
    return 1;
  }



    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return 1;
    }
}
