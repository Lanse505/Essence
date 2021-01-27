package com.teamacronymcoders.essence.api.modifier.core;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Modifier extends ForgeRegistryEntry<Modifier> implements IModifier {

  private final int maxLevel;
  private final int minLevel;

  public Modifier() {
    this.maxLevel = 1;
    this.minLevel = 0;
  }

  public Modifier(int maxLevel) {
    this.maxLevel = maxLevel;
    this.minLevel = 0;
  }

  public Modifier(int minLevel, int maxLevel) {
    this.minLevel = minLevel;
    this.maxLevel = maxLevel;
  }

  /**
   * @param modifier Modifier to check against
   * @return Returns if the provided Modifier can be applied with this one.
   */
  @Override
  public boolean canApplyTogether(IModifier modifier) {
    return true;
  }

  /**
   * @return Returns the Translation Key for the Modifier.
   */
  @Nonnull
  public String getTranslationName() {
    final ResourceLocation id = this.getRegistryName();
    return "modifier." + id.getNamespace() + "." + id.getPath();
  }

  /**
   * Override this and use Level -1 as a dump value!
   *
   * @param level Level of the Modifier
   * @return Returns the formatted TextComponent
   */
  public ITextComponent getTextComponentName(int level) {
    if (level == -1) {
      return new TranslationTextComponent(getTranslationName()).mergeStyle(TextFormatting.GRAY);
    }
    if (level == 1) {
      return new StringTextComponent("  ").append(new TranslationTextComponent(getTranslationName()).mergeStyle(TextFormatting.GRAY));
    }
    return new StringTextComponent("  ").append(new TranslationTextComponent(getTranslationName()).appendString(" " + EssenceUtilHelper.toRoman(level)).mergeStyle(TextFormatting.GRAY));
  }

  /**
   * @return Gets the ITextComponent that should be rendered in it's Information-Box on the ItemStack.
   */
  public List<ITextComponent> getRenderedText(ModifierInstance instance) {
    List<ITextComponent> textComponents = new ArrayList<>();
    if (instance == null) {
      return textComponents;
    }
    textComponents.add(getTextComponentName(instance.getLevel()));
    return textComponents;
  }

  @Override
  public void update(CompoundNBT compoundNBT) {}

  public abstract boolean countsTowardsLimit(int level);

  public abstract int getModifierCountValue(int level);

  public abstract boolean canApplyOnObject();

  public int getMinLevel() {
    return minLevel;
  }

  public int getMaxLevel() {
    return maxLevel;
  }

  public int getLevelInRange(int level) {
    return Math.max(Math.min(level, this.getMaxLevel()), this.getMinLevel());
  }

}
