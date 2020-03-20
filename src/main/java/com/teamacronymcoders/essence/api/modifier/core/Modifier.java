package com.teamacronymcoders.essence.api.modifier.core;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public abstract class Modifier<T> extends ForgeRegistryEntry<Modifier<?>> implements IModifier {

    private final Class<T> type;
    private final int maxLevel;
    private int minLevel;

    public Modifier(Class<T> type) {
        this.type = type;
        this.maxLevel = 1;
        this.minLevel = 0;
    }

    public Modifier(Class<T> type, int maxLevel) {
        this.type = type;
        this.maxLevel = maxLevel;
        this.minLevel = 0;
    }

    public Modifier(Class<T> type, int maxLevel, int minLevel) {
        this.type = type;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
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
    @OnlyIn(Dist.CLIENT)
    public String getTranslationName() {
        final ResourceLocation id = this.getRegistryName();
        return "modifier." + id.getNamespace() + "." + id.getPath();
    }

    /**
     * @return Gets the ITextComponent that should be rendered in it's Information-Box on the ItemStack.
     */
    public List<ITextComponent> getRenderedText(ModifierInstance instance) {
        List<ITextComponent> textComponents = new ArrayList<>();
        if (instance == null) {
            return textComponents;
        }

        if (instance.getLevel() == 1) {
            textComponents.add(new TranslationTextComponent(getTranslationName()).applyTextStyle(TextFormatting.GRAY));
            return textComponents;
        }

        textComponents.add(new TranslationTextComponent(getTranslationName()).appendText(" " + EssenceUtilHelper.toRoman(instance.getLevel())).applyTextStyle(TextFormatting.GRAY));
        return textComponents;
    }

    @Override
    public void update(CompoundNBT compoundNBT) {}

    public abstract boolean countsTowardsLimit(int level, T object);
    public abstract int getModifierCountValue(int level, T object);

    public abstract boolean canApplyOnObject(T object);



    public int getMinLevel(T object) {
        return minLevel;
    }

    public int getMaxLevel(T object) {
        return maxLevel;
    }

    public int getLevelInRange(int level, T object) {
        return Math.max(Math.min(level, this.getMaxLevel(object)), this.getMinLevel(object));
    };

    public Class<T> getType() {
        return type;
    };
}
