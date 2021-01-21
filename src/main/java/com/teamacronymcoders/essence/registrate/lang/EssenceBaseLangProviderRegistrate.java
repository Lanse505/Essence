package com.teamacronymcoders.essence.registrate.lang;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.registrate.EssenceLangRegistrate;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateProvider;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.StringUtils;

public class EssenceBaseLangProviderRegistrate extends LanguageProvider implements RegistrateProvider {
  private final AbstractRegistrate<?> owner;
  private final EssenceBaseLangProviderRegistrate.AccessibleLanguageProvider upsideDown;
  private static final String NORMAL_CHARS = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'";
  private static final String UPSIDE_DOWN_CHARS = "ɐqɔpǝɟbɥıظʞןɯuuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIſʞꞀWNOԀὉᴚS⟘∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86‾'؛˙¿¡/\\,";

  public EssenceBaseLangProviderRegistrate(AbstractRegistrate<?> owner, DataGenerator gen, String locale) {
    super(gen, owner.getModid(), locale);
    this.owner = owner;
    this.upsideDown = new EssenceBaseLangProviderRegistrate.AccessibleLanguageProvider(gen, owner.getModid(), "en_ud");
  }

  public LogicalSide getSide() {
    return LogicalSide.CLIENT;
  }

  public String getName() {
    return "Essence Extended Lang (en_us/en_ud)";
  }

  protected void addTranslations() {
    this.owner.genData(EssenceLangRegistrate.EN_US_UD, this);
  }

  public static final String toEnglishName(String internalName) {
    return (String) Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
  }

  public String getAutomaticName(NonNullSupplier<? extends IForgeRegistryEntry<?>> sup) {
    return toEnglishName(((IForgeRegistryEntry)sup.get()).getRegistryName().getPath());
  }

  public void addBlock(NonNullSupplier<? extends Block> block) {
    this.addBlock(block, this.getAutomaticName(block));
  }

  public void addBlockWithTooltip(NonNullSupplier<? extends Block> block, String tooltip) {
    this.addBlock(block);
    this.addTooltip(block, tooltip);
  }

  public void addBlockWithTooltip(NonNullSupplier<? extends Block> block, String name, String tooltip) {
    this.addBlock(block, name);
    this.addTooltip(block, tooltip);
  }

  public void addItem(NonNullSupplier<? extends Item> item) {
    this.addItem(item, this.getAutomaticName(item));
  }

  public void addItemWithTooltip(NonNullSupplier<? extends Item> block, String name, List<String> tooltip) {
    this.addItem(block, name);
    this.addTooltip(block, tooltip);
  }

  public void addTooltip(NonNullSupplier<? extends IItemProvider> item, String tooltip) {
    this.add(((IItemProvider)item.get()).asItem().getTranslationKey() + ".desc", tooltip);
  }

  public void addTooltip(NonNullSupplier<? extends IItemProvider> item, List<String> tooltip) {
    for(int i = 0; i < tooltip.size(); ++i) {
      this.add(((IItemProvider)item.get()).asItem().getTranslationKey() + ".desc." + i, (String)tooltip.get(i));
    }

  }

  public void add(ItemGroup group, String name) {
    this.add(((TranslationTextComponent)group.getGroupName()).getKey(), name);
  }

  public void addEntityType(NonNullSupplier<? extends EntityType<?>> entity) {
    this.addEntityType(entity, this.getAutomaticName(entity));
  }

  private String toUpsideDown(String normal) {
    char[] ud = new char[normal.length()];

    for(int i = 0; i < normal.length(); ++i) {
      char c = normal.charAt(i);
      if (c != '%') {
        int lookup = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'".indexOf(c);
        if (lookup >= 0) {
          c = "ɐqɔpǝɟbɥıظʞןɯuuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIſʞꞀWNOԀὉᴚS⟘∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86‾'؛˙¿¡/\\,".charAt(lookup);
        }

        ud[normal.length() - 1 - i] = c;
      } else {
        String fmtArg;
        for(fmtArg = ""; Character.isDigit(c) || c == '%' || c == '$' || c == 's' || c == 'd'; c = i == normal.length() ? 0 : normal.charAt(i)) {
          fmtArg = fmtArg + c;
          ++i;
        }

        --i;

        for(int j = 0; j < fmtArg.length(); ++j) {
          ud[normal.length() - 1 - i + j] = fmtArg.charAt(j);
        }
      }
    }

    return new String(ud);
  }

  /**
   * Helper function to add advancements to the lang generator
   *
   * @param advancement The advancement for localizations to be added
   * @param title       The title of the advancement
   * @param description The description of the advancement
   */
  public void addAdvancement(Advancement advancement, String title, String description) {
    final DisplayInfo display = advancement.getDisplay();
    add(display.getTitle().getString(), title);
    add(display.getDescription().getString(), description);
  }

  public void addModifier(Modifier<?> modifier, String translation) {
    add(modifier.getTranslationName(), translation);
  }

  public void add(String key, String value) {
    super.add(key, value);
    this.upsideDown.add(key, this.toUpsideDown(value));
  }

  public void act(DirectoryCache cache) throws IOException {
    super.act(cache);
    this.upsideDown.act(cache);
  }

  static {
    if ("abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'".length() != "ɐqɔpǝɟbɥıظʞןɯuuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIſʞꞀWNOԀὉᴚS⟘∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86‾'؛˙¿¡/\\,".length()) {
      throw new AssertionError("Char maps do not match in length!");
    }
  }

  private static class AccessibleLanguageProvider extends LanguageProvider {
    public AccessibleLanguageProvider(DataGenerator gen, String modid, String locale) {
      super(gen, modid, locale);
    }

    public void add(@Nullable String key, @Nullable String value) {
      super.add(key, value);
    }

    protected void addTranslations() {
    }
  }
}
