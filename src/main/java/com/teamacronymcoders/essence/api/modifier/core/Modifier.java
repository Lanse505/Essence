package com.teamacronymcoders.essence.api.modifier.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Modifier extends ForgeRegistryEntry<Modifier> {

    private static final Multimap<String, AttributeModifier> EMPTY_ATTRIBUTE_MAP = HashMultimap.create();
    private ResourceLocation resourceLocation;

    /**
     * @param stack   Essence-Tool ItemStack that provides the AttributeModifiers.
     * @param wielder Wielder of the Essence-Tool.
     * @param level   Modifier-Level.
     * @return Default: Returns the empty attribute map.
     */
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, LivingEntity wielder, int level) {
        return EMPTY_ATTRIBUTE_MAP;
    }

    /**
     * @return Returns the Max Level of the Modifier.
     */
    public int getMaxLevel() {
        return 1;
    }

    /**
     * @param stack The ItemStack that holds the Modifier.
     * @param level Level of Modifier.
     * @return Returns a boolean, equal to if the Modifier should count towards the Max Amount.
     */
    public boolean countsTowardsLimit(ItemStack stack, int level) {
        return true;
    }

    /**
     * @return Returns how many modifiers, this modifier counts towards
     */
    public int getModifierCountValue(ItemStack stack, int level) {
        return 1;
    }

    /**
     * @param level The current level.
     * @return Gets the minimum value, and caps it at 0.
     */
    public int getLevelInRange(int level) {
        return Math.max(Math.min(level, this.getMaxLevel()), 0);
    }

    /**
     * @param modifier Modifier to check against
     * @return Returns if the provided Modifier can be applied with this one.
     */
    public boolean canApplyTogether(Modifier modifier) {return true;}

    /**
     * This returns a boolean check against both Modifiers not just this Modifier.
     * @param modifier Modifier to check against.
     * @return Returns the final value if this can be applied together with the other Modifier.
     */
    public final boolean isCompatibleWith(Modifier modifier) {
        return this.canApplyTogether(modifier) && modifier.canApplyTogether(this);
    }

    /**
     * @return Returns the Translation Key for the Modifier.
     */
    @OnlyIn(Dist.CLIENT)
    public String getTranslationName() {
        final ResourceLocation id = this.getRegistryName();
        return "essence.modifier." + id.getNamespace() + "." + id.getPath();
    }

    /**
     * @return Gets the ITextComponent that should be rendered in it's Information-Box on the ItemStack.
     */
    public ITextComponent getRenderedText() {
        return new TranslationTextComponent(getTranslationName());
    }

}
