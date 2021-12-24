package com.teamacronymcoders.essence.command.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.command.argument.EssenceHandArgumentType;
import com.teamacronymcoders.essence.command.argument.EssenceModifierArgumentType;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class ItemStackModifierCommand implements Command<CommandSourceStack> {

  public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
    return Commands.literal("modifierItemStack")
            .requires(cs -> cs.hasPermission(4) && cs.getEntity() instanceof Player)
            .then(Commands.literal("add")
                    .then(Commands.argument("hand", EssenceHandArgumentType.hand())
                            .then(Commands.argument("modifier", EssenceModifierArgumentType.modifier())
                                    .then(Commands.argument("level", IntegerArgumentType.integer())
                                            .then(Commands.argument("nbt", StringArgumentType.string())
                                                    .executes(context -> addModifierToTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), IntegerArgumentType.getInteger(context, "level"), StringArgumentType.getString(context, "nbt")))
                                            )
                                            .executes(context -> addModifierToTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), IntegerArgumentType.getInteger(context, "level"), ""))
                                    )
                                    .executes(context -> addModifierToTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), 1, ""))
                            )
                    )
            )
            .then(Commands.literal("remove")
                    .then(Commands.argument("hand", EssenceHandArgumentType.hand())
                            .then(Commands.argument("modifier", EssenceModifierArgumentType.modifier())
                                    .executes(context -> removeModifierFromTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), 1, ""))
                            )
                    ))
            .then(Commands.literal("merge")
                    .then(Commands.argument("hand", EssenceHandArgumentType.hand())
                            .then(Commands.argument("modifier", EssenceModifierArgumentType.modifier())
                                    .then(Commands.argument("nbt", StringArgumentType.string())
                                            .executes(context -> mergeModifierTags(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), StringArgumentType.getString(context, "nbt")))
                                    )
                            )
                    )
            )
            .then(Commands.literal("alter-level")
                    .then(Commands.argument("hand", EssenceHandArgumentType.hand())
                            .then(Commands.argument("modifier", EssenceModifierArgumentType.modifier())
                                    .then(Commands.argument("increment", IntegerArgumentType.integer())
                                            .executes(context -> alterLevelModifierOnTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), IntegerArgumentType.getInteger(context, "increment")))
                                    )
                                    .executes(context -> alterLevelModifierOnTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), 1))
                            )
                    )
            );
  }

  public static int addModifierToTool(CommandContext<CommandSourceStack> context, InteractionHand hand, Modifier modifier, int level, String nbt) throws CommandSyntaxException {
    CommandSourceStack source = context.getSource();
    ServerPlayer playerEntity = source.getPlayerOrException();
    CompoundTag compound = nbt.equals("") ? new CompoundTag() : TagParser.parseTag(nbt);
    ItemStack stack = playerEntity.getItemInHand(hand);
    LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (!stack.isEmpty() && holder.isPresent()) {
      ModifierInstance instance = new ModifierInstance(() -> modifier, level, compound);
      holder.ifPresent(presentHolder -> {
        presentHolder.addModifierInstance(false, stack, instance);
        stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, presentHolder.serializeNBT());
        presentHolder.deserializeNBT(stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND));
        source.sendSuccess(new TranslatableComponent("command.essence.modifier.itemstack.add", modifier.getTextComponentName(-1), hand.name()), true);
      });
      return 1;
    }
    return 0;
  }

  public static int removeModifierFromTool(CommandContext<CommandSourceStack> context, InteractionHand hand, Modifier modifier, int level, String nbt) throws CommandSyntaxException {
      CommandSourceStack source = context.getSource();
    ServerPlayer playerEntity = source.getPlayerOrException();
    ItemStack stack = playerEntity.getItemInHand(hand);
    CompoundTag compound = nbt.equals("") ? new CompoundTag() : TagParser.parseTag(nbt);
    LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (holder.isPresent()) {
      ModifierInstance instance = new ModifierInstance(() -> modifier, level, compound);
      holder.ifPresent(presentHolder -> {
        presentHolder.removeModifierInstance(false, stack, instance.getModifier());
        stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, presentHolder.serializeNBT());
        presentHolder.deserializeNBT(stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND));
        source.sendSuccess(new TranslatableComponent("command.essence.modifier.itemstack.remove", modifier.getTextComponentName(-1), hand.name()), true);
      });
      return 1;
    }
    return 0;
  }

  public static int mergeModifierTags(CommandContext<CommandSourceStack> context, InteractionHand hand, Modifier modifier, String nbt) throws CommandSyntaxException {
    CommandSourceStack source = context.getSource();
    ServerPlayer playerEntity = source.getPlayerOrException();
    ItemStack stack = playerEntity.getItemInHand(hand);
    CompoundTag compound = nbt.equals("") ? new CompoundTag() : TagParser.parseTag(nbt);
    LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (holder.isPresent()) {
      holder.ifPresent(presentHolder -> {
        List<ModifierInstance> instances = presentHolder.getModifierInstances();
        instances.stream()
                .filter(instance -> instance.getModifier().equals(modifier))
                .findFirst()
                .ifPresent(instance -> {
                  CompoundTag modifiedCompound = instance.getModifierData();
                  modifiedCompound.merge(compound);
                  instance.setModifierData(modifiedCompound);
                });
        source.sendSuccess(new TranslatableComponent("command.essence.modifier.itemstack.merge", modifier.getTextComponentName(-1)), true);
      });
    }
    return 0;
  }

  public static int alterLevelModifierOnTool(CommandContext<CommandSourceStack> context, InteractionHand hand, Modifier modifier, int alter) throws CommandSyntaxException {
    CommandSourceStack source = context.getSource();
    ServerPlayer playerEntity = source.getPlayerOrException();
    ItemStack stack = playerEntity.getItemInHand(hand);
    LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    if (holder.isPresent()) {
      holder.ifPresent(presentHolder -> {
        if (alter > 0) {
          presentHolder.levelUpModifier(false, alter, stack, modifier);
        } else if (alter < 0) {
          presentHolder.levelDownModifier(false, alter, stack, modifier);
        }
        stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, presentHolder.serializeNBT());
        presentHolder.deserializeNBT(stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND));
        source.sendSuccess(new TranslatableComponent("command.essence.modifier.itemstack.level_up", modifier.getTextComponentName(-1)), true);
      });
      return 1;
    }
    return 0;
  }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return 1;
    }
}
