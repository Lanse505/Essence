package com.teamacronymcoders.essence.datagen.advancement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

public class ExtendableAdvancementProvider extends AdvancementProvider {

  private static final BasicItem defaultIcon = EssenceItemRegistrate.ESSENCE_CRYSTAL.get();

  private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
  private final DataGenerator generator;

  public ExtendableAdvancementProvider (DataGenerator generator) {
    super(generator);
    this.generator = generator;
  }

  @Override
  public void act (DirectoryCache cache) throws IOException {
    Path outputFolder = this.generator.getOutputFolder();
    Consumer<Advancement> consumer = advancement -> {
      Path path = outputFolder.resolve("data/" + advancement.getId().getNamespace() + "/advancements/" + advancement.getId().getPath() + ".json");
      try {
        IDataProvider.save(GSON, cache, advancement.copy().serialize(), path);
      } catch (IOException e) {
        Essence.LOGGER.info(e);
      }
    };
    addAdvancements(consumer);
  }

  protected void addAdvancements (Consumer<Advancement> consumer) {
  }


  @Override
  public String getName () {
    return "Essence Advancement";
  }

  public static BasicItem getDefaultIcon () {
    return defaultIcon;
  }
}
