package com.teamacronymcoders.essence.command.argument.extendable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EssenceEnumArgumentType<E extends Enum<E>> implements ArgumentType<E> {
    private final E[] eVal;
    private final Class<E> eClass;
    private final DynamicCommandExceptionType exceptionType = new DynamicCommandExceptionType((input) ->
        new TranslationTextComponent("command.argument.essence.enum.invalid", input));

    public EssenceEnumArgumentType(Class<E> eVal) {
        this.eVal = eVal.getEnumConstants();
        this.eClass = eVal;
    }

    @Override
    public E parse(StringReader reader) throws CommandSyntaxException {
        String input = read(reader);
        return Optional.of(Enum.valueOf(eClass, input))
            .orElseThrow(() -> exceptionType.create(input));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Arrays.stream(eVal).map(Enum::name), builder);
    }

    public String read(StringReader reader) throws CommandSyntaxException {
        int i = reader.getCursor();

        while (reader.canRead() && isValidCharacter(reader.peek())) {
            reader.skip();
        }

        String s = reader.getString().substring(i, reader.getCursor());

        try {
            return s;
        } catch (ResourceLocationException var4) {
            reader.setCursor(i);
            throw exceptionType.create(reader);
        }
    }

    public static boolean isValidCharacter(char charIn) {
        return charIn != ' ';
    }
}
