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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class ItemStackModifierCommand implements Command<CommandSource> {

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("modifierItemStack")
            .requires(cs -> {
                try {
                    return cs.hasPermissionLevel(4) && cs.assertIsEntity() instanceof PlayerEntity;
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                    return false;
                }
            })
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
                        .executes(context -> removeModifierToTool(context, EssenceHandArgumentType.getHand(context, "hand"), EssenceModifierArgumentType.getModifier(context, "modifier"), 1, ""))
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

    @SuppressWarnings({"unchecked"})
    public static int addModifierToTool(CommandContext<CommandSource> context, Hand hand, Modifier<?> modifier, int level, String nbt) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        ServerPlayerEntity playerEntity = source.asPlayer();
        CompoundNBT compound = nbt.equals("") ? new CompoundNBT() : JsonToNBT.getTagFromJson(nbt);
        ItemStack stack = playerEntity.getHeldItem(hand);
        LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (!stack.isEmpty() && holder.isPresent() && modifier.getType().isAssignableFrom(ItemStack.class)) {
            ModifierInstance<ItemStack> instance = new ModifierInstance<>(ItemStack.class, () -> (Modifier<ItemStack>) modifier, level, compound);
            holder.ifPresent(presentHolder -> {
                presentHolder.addModifierInstance(false, stack, instance);
                stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, presentHolder.serializeNBT());
                presentHolder.deserializeNBT(stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
                source.sendFeedback(new TranslationTextComponent("command.essence.modifier.itemstack.add", modifier.getTextComponentName(-1), hand.name()), true);
            });
            return 1;
        }
        return 0;
    }

    @SuppressWarnings({"unchecked"})
    public static int removeModifierToTool(CommandContext<CommandSource> context, Hand hand, Modifier<?> modifier, int level, String nbt) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        ServerPlayerEntity playerEntity = source.asPlayer();
        ItemStack stack = playerEntity.getHeldItem(hand);
        CompoundNBT compound = nbt.equals("") ? new CompoundNBT() : JsonToNBT.getTagFromJson(nbt);
        LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (holder.isPresent() && modifier.getType().isAssignableFrom(ItemStack.class)) {
            ModifierInstance<ItemStack> instance = new ModifierInstance<>(ItemStack.class, () -> (Modifier<ItemStack>) modifier, level, compound);
            holder.ifPresent(presentHolder -> {
                presentHolder.removeModifierInstance(false, stack, instance);
                stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, presentHolder.serializeNBT());
                presentHolder.deserializeNBT(stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
                source.sendFeedback(new TranslationTextComponent("command.essence.modifier.itemstack.remove", modifier.getTextComponentName(-1), hand.name()), true);
            });
            return 1;
        }
        return 0;
    }

    public static int mergeModifierTags(CommandContext<CommandSource> context, Hand hand, Modifier<?> modifier, String nbt) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        ServerPlayerEntity playerEntity = source.asPlayer();
        ItemStack stack = playerEntity.getHeldItem(hand);
        CompoundNBT compound = nbt.equals("") ? new CompoundNBT() : JsonToNBT.getTagFromJson(nbt);
        LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (holder.isPresent() && modifier.getType().isAssignableFrom(ItemStack.class)) {
            holder.ifPresent(presentHolder -> {
                List<ModifierInstance<ItemStack>> instances = presentHolder.getModifierInstances();
                instances.stream()
                    .filter(instance -> instance.getModifier().equals(modifier))
                    .findFirst()
                    .ifPresent(instance -> {
                        CompoundNBT modifiedCompound = instance.getModifierData();
                        modifiedCompound.merge(compound);
                        instance.setModifierData(modifiedCompound);
                    });
                source.sendFeedback(new TranslationTextComponent("command.essence.modifier.itemstack.merge", modifier.getTextComponentName(-1)), true);
            });
        }
        return 0;
    }

    @SuppressWarnings({"unchecked"})
    public static int alterLevelModifierOnTool(CommandContext<CommandSource> context, Hand hand, Modifier<?> modifier, int alter) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        ServerPlayerEntity playerEntity = source.asPlayer();
        ItemStack stack = playerEntity.getHeldItem(hand);
        LazyOptional<ItemStackModifierHolder> holder = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        if (holder.isPresent() && modifier.getType().isAssignableFrom(ItemStack.class)) {
            holder.ifPresent(presentHolder -> {
                if (alter > 0) {
                    presentHolder.levelUpModifier(false, stack, alter, (Modifier<ItemStack>) modifier);
                } else if (alter < 0) {
                    presentHolder.levelDownModifier(false, stack, alter, (Modifier<ItemStack>) modifier);
                }
                stack.getOrCreateTag().put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, presentHolder.serializeNBT());
                presentHolder.deserializeNBT(stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND));
                source.sendFeedback(new TranslationTextComponent("command.essence.modifier.itemstack.level_up", modifier.getTextComponentName(-1)), true);
            });
            return 1;
        }
        return 0;
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return 1;
    }
}
