package com.teamacronymcoders.essence.server.command.argument.extendable;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EssenceRegistryArgumentType<T extends IForgeRegistryEntry<T>> implements ArgumentType<T> {
    private final IForgeRegistry<T> registry;
    private final List<String> examples;
    private final DynamicCommandExceptionType exceptionType = new DynamicCommandExceptionType((input) ->
            new TranslatableComponent("command.argument.essence.registry.invalid", input));

    public EssenceRegistryArgumentType() {
        this.registry = null;
        this.examples = new ArrayList<>();
    }

    public EssenceRegistryArgumentType(IForgeRegistry<T> registry) {
        this.registry = registry;
        this.examples = createExamples(registry);
    }

    private static <T extends IForgeRegistryEntry<T>> List<String> createExamples(IForgeRegistry<T> registry) {
        List<String> examples = Lists.newArrayList();
        Iterator<ResourceLocation> iterator = registry.getKeys().iterator();
        int i = 0;
        while (iterator.hasNext() & i < 5) {
            i++;
            examples.add(iterator.next().toString());
        }
        return examples;
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        return Optional.ofNullable(registry.getValue(resourceLocation))
                .orElseThrow(() -> exceptionType.create(resourceLocation));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(registry.getKeys().stream(), builder);
    }

    @Override
    public List<String> getExamples() {
        return examples;
    }
}
