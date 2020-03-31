package com.teamacronymcoders.essence.util.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EssenceMapRegistryArgumentType<T> implements ArgumentType<T> {

    private final Map<ResourceLocation, T> registry;
    private final List<String> examples;
    private final DynamicCommandExceptionType exceptionType = new DynamicCommandExceptionType((input) ->
        new TranslationTextComponent("command.argument.essence.registry.invalid", input));

    public EssenceMapRegistryArgumentType(Map<ResourceLocation, T> registry) {
        this.registry = registry;
        this.examples = createExamples(registry);
    }

    private static <U> List<String> createExamples(Map<ResourceLocation, U> registry) {
        List<String> examples = Lists.newArrayList();
        Iterator<ResourceLocation> iterator = registry.keySet().iterator();
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
        return Optional.ofNullable(registry.get(resourceLocation))
            .orElseThrow(() -> exceptionType.create(resourceLocation));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.func_212476_a(registry.keySet().stream(), builder);
    }

    @Override
    public List<String> getExamples() {
        return examples;
    }
}
