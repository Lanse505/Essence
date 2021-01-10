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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class KnowledgeCommand implements Command<CommandSource> {

  public static ArgumentBuilder<CommandSource, ?> register (CommandDispatcher<CommandSource> dispatcher) {
    return Commands.literal("knowledge")
            .requires(cs -> cs.hasPermissionLevel(4))
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

  public static int addPlayerKnowledge (CommandContext<CommandSource> context, PlayerEntity playerEntity, Knowledge<?> knowledge) {
    CommandSource source = context.getSource();
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.addKnowledge(playerEntity, knowledge);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.add", new TranslationTextComponent(knowledge.getTranslationString()), playerEntity.getName()), true);
    });
    return 1;
  }

  public static int addPlayerKnowledge (CommandContext<CommandSource> context, String playerUUID, Knowledge<?> knowledge) {
    CommandSource source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.addKnowledge(uuid, knowledge);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.add", new TranslationTextComponent(knowledge.getTranslationString()), uuid.toString()), true);
    });
    return 1;
  }

  public static int removePlayerKnowledge (CommandContext<CommandSource> context, PlayerEntity playerEntity, Knowledge<?> knowledge) {
    CommandSource source = context.getSource();
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.removeKnowledge(playerEntity, knowledge);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.remove", new TranslationTextComponent(knowledge.getTranslationString()), playerEntity.getName()), true);
    });
    return 1;
  }

  public static int removePlayerKnowledge (CommandContext<CommandSource> context, String playerUUID, Knowledge<?> knowledge) {
    CommandSource source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.removeKnowledge(uuid, knowledge);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.remove", new TranslationTextComponent(knowledge.getTranslationString()), uuid.toString()), true);
    });
    return 1;
  }

  public static int clearPlayerKnowledge (CommandContext<CommandSource> context, PlayerEntity playerEntity) {
    CommandSource source = context.getSource();
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.clearKnowledge(playerEntity.getUniqueID());
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.clear", playerEntity.getName()), true);
    });
    return 1;
  }

  public static int clearPlayerKnowledge (CommandContext<CommandSource> context, String playerUUID) {
    CommandSource source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      holder.clearKnowledge(uuid);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.clear", uuid.toString()), true);
    });
    return 1;
  }

  public static int dumpAllPlayerKnowledge (CommandContext<CommandSource> context, PlayerEntity player) {
    CommandSource source = context.getSource();
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      List<Knowledge<?>> knowledges = holder.getKnowledgeAsList(player.getUniqueID());
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.dump", player.getName()), true);
      knowledges.forEach(knowledge -> {
        source.sendFeedback(new TranslationTextComponent("command.essence.generic.dump.knowledge", new TranslationTextComponent(knowledge.getTranslationString()), knowledge.getTypeString()), true);
      });
    });
    return 1;
  }

  public static int dumpAllPlayerKnowledge (CommandContext<CommandSource> context, String playerUUID) {
    CommandSource source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    World world = source.getWorld();
    world.getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
      List<Knowledge<?>> knowledges = holder.getKnowledgeAsList(uuid);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.dump", uuid.toString()), true);
      knowledges.forEach(knowledge -> {
        source.sendFeedback(new TranslationTextComponent("command.essence.generic.dump.knowledge", new TranslationTextComponent(knowledge.getTranslationString()), knowledge.getTypeString()), true);
      });
    });
    return 1;
  }

  public static int dumpSomePlayerKnowledge (CommandContext<CommandSource> context, PlayerEntity player, int amountToDump) {
    CommandSource source = context.getSource();
    World world = source.getWorld();
    List<Knowledge<?>> knowledges = world.getCapability(EssenceCapability.KNOWLEDGE).map(holder -> holder.getKnowledgeAsList(player.getUniqueID())).orElse(new ArrayList<>());
    source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.dump", player.getName()), true);
    for (int i = 0; i < amountToDump; i++) {
      Knowledge<?> knowledge = knowledges.get(i);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.dump.type", knowledge.getTypeString()), true);
      source.sendFeedback(new TranslationTextComponent(knowledge.getTranslationString()), true);
    }
    return 1;
  }

  public static int dumpSomePlayerKnowledge (CommandContext<CommandSource> context, String playerUUID, int amountToDump) {
    CommandSource source = context.getSource();
    UUID uuid = UUID.fromString(playerUUID);
    World world = source.getWorld();
    List<Knowledge<?>> knowledges = world.getCapability(EssenceCapability.KNOWLEDGE).map(holder -> holder.getKnowledgeAsList(uuid)).orElse(new ArrayList<>());
    source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.dump", uuid.toString()), true);
    for (int i = 0; i < amountToDump; i++) {
      Knowledge<?> knowledge = knowledges.get(i);
      source.sendFeedback(new TranslationTextComponent("command.essence.knowledge.dump.type", knowledge.getTypeString()), true);
      source.sendFeedback(new TranslationTextComponent(knowledge.getTranslationString()), true);
    }
    return 1;
  }

  @Override
  public int run (CommandContext<CommandSource> context) throws CommandSyntaxException {
    return 1;
  }
}
