package com.teamacronymcoders.essence.serializable.provider.advancement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.item.essence.EssenceCrystal;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class ExtendableAdvancementProvider extends AdvancementProvider {

    private static final EssenceCrystal defaultIcon = EssenceObjectHolders.ESSENCE_INFUSED_CRYSTAL;

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final DataGenerator generator;
    private String subFolder;

    public ExtendableAdvancementProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    public ExtendableAdvancementProvider(DataGenerator generator, String subFolder) {
        super(generator);
        this.generator = generator;
        this.subFolder = subFolder;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Path outputFolder = this.generator.getOutputFolder();
        Consumer<Advancement> consumer = advancement -> {
            if (subFolder != null && !subFolder.isEmpty()) {
                Path path = outputFolder.resolve("data/" + advancement.getId().getNamespace() + "/advancements/" + subFolder + "/" + advancement.getId().getPath() + ".json");
                try {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), path);
                } catch (IOException e) {
                    Essence.LOGGER.info(e);
                }
            } else {
                Path path = outputFolder.resolve("data/" + advancement.getId().getNamespace() + "/advancements/" + advancement.getId().getPath() + ".json");
                try {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), path);
                } catch (IOException e) {
                    Essence.LOGGER.info(e);
                }
            }
        };
        addAdvancements(consumer);
    }

    protected void addAdvancements(Consumer<Advancement> consumer) {}


    @Override
    public String getName() {
        return "Essence Advancement";
    }

    public static EssenceCrystal getDefaultIcon() {
        return defaultIcon;
    }
}
