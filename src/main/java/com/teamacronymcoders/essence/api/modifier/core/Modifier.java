package com.teamacronymcoders.essence.api.modifier.core;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.common.util.helper.EssenceUtilHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return new TranslatableComponent(getTranslationName()).withStyle(ChatFormatting.GRAY);
        }
        if (level == 1) {
            return new TextComponent("  ").append(new TranslatableComponent(getTranslationName()).withStyle(ChatFormatting.GRAY));
        }
        return new TextComponent("  ").append(new TranslatableComponent(getTranslationName()).append(" " + EssenceUtilHelper.toRoman(level)).withStyle(ChatFormatting.GRAY));
    }

    /**
     * @return Gets the ITextComponent that should be rendered in it's Information-Box on the ItemStack.
     */
    public List<Component> getRenderedText(ModifierInstance instance) {
        List<Component> textComponents = new ArrayList<>();
        if (instance == null) {
            return textComponents;
        }
        textComponents.add(getTextComponentName(instance.getLevel()));
        return textComponents;
    }

    @Override
    public void update(CompoundTag compoundNBT) {
    }

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

    public void mergeTags(CompoundTag original, CompoundTag mergeTag) {}

}
