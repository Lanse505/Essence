package com.teamacronymcoders.essence.api.modifier;

import com.teamacronymcoders.essence.common.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public interface IModifier<T> {

    // Core Modifier Methods

    /**
     * Used to get the Min level of the Modifier.
     *
     * @return Returns the minimum level of the Modifier.
     */
    int getMinLevel(T target);

    /**
     * Used to get the Max level of the Modifier.
     *
     * @return Returns the maximum level of the Modifier.
     */
    int getMaxLevel(T target);

    /**
     * Used to dictate if the modifier counts towards the maximum modifier count of the storage object.
     *
     * @param level The current level of the modifier to check against.
     * @return Returns whether the modifier counts towards the tool maximum modifier slots.
     */
    boolean countsTowardsLimit(T target, int level);

    /**
     * Used to get how many Modifier points the Modifier will take from the storage objects point pool.
     *
     * @param level The current level of the modifier to check against.
     * @return Returns how many Modifier points the modifier currently takes up.
     */
    int getModifierCountValue(T target, int level);

    /**
     * Used to check whether the current level is within the allowed range of levels for the Modifier.
     *
     * @param level The level to check against.
     * @return Returns whether the checked level is within the min-max span of allowed levels.
     */
    default int getLevelInRange(T target, int level) {
        return Math.max(Math.min(level, this.getMaxLevel(target)), this.getMinLevel(target));
    }


    // Conflict Checking Methods

    /**
     * This returns a boolean check against the provided modifier.
     *
     * @param modifier Modifier to check against.
     * @return Returns if this modifier can be applied together with the provided Modifier.
     */
    boolean isCompatibleWith(T target, IModifier<?> modifier);

    /**
     * This returns a boolean check against both Modifiers not just this Modifier.
     *
     * @param modifier Modifier to check against.
     * @return Returns the final value if this can be applied together with the other Modifier.
     */
    default boolean canApplyTogether(T target, IModifier<T> modifier) {
        return this.isCompatibleWith(target, modifier) && modifier.isCompatibleWith(target, this);
    }

    /**
     * Used to check if the modifier can be applied on the provided target object.
     *
     * @param target Target T object.
     * @return Returns whether this modifier can be applied on the provided T object.
     */
    boolean canApplyOnObject(T target);


    // Resolution Methods

    /**
     * Used to handle merging the existing modifier data with new modifier data.
     *
     * @param target      The T target instance.
     * @param originalTag The original ModifierInstance's CompoundTag.
     * @param mergeTag    The mergable CompoundTag.
     */
    void mergeData(T target, CompoundTag originalTag, CompoundTag mergeTag);

    /**
     * Used to handle merging the existing ModifierInstance with the new ModifierInstance
     *
     * @param target           The T target instance.
     * @param originalInstance The original stored ModifierInstance.
     * @param mergeInstance    The mergable ModifierInstance.
     */
    void mergeInstance(T target, ModifierInstance originalInstance, ModifierInstance mergeInstance);

    /**
     * Used to update a modifier whenever the Tag data gets updated.
     *
     * @param data The Instanced Modifier Data.
     */
    void update(CompoundTag data);


    // Translation and Component Methods

    /**
     * Used to get the translatable string value for the modifier name.
     * Example: 'modifier.namespace.path' = 'modifier.essence.soaked'
     *
     * @return Returns the translation id for the Modifier
     */
    default String getTranslationName() {
        final ResourceLocation id = EssenceModifierRegistrate.REGISTRY.get().getKey(this);
        return "modifier." + id.getNamespace() + "." + id.getPath();
    }

    /**
     * Override this and use Level -1 as a dump value!
     *
     * @param level Level of the Modifier
     * @return Returns the formatted TextComponent
     */
    default Component getTextComponentName(int level) {
        if (level == -1) {
            return Component.translatable(getTranslationName()).withStyle(ChatFormatting.GRAY);
        }
        if (level == 1) {
            return Component.literal("  ").append(Component.translatable(getTranslationName()).withStyle(ChatFormatting.GRAY));
        }
        return Component.literal("  ").append(Component.translatable(getTranslationName()).append(" " + EssenceUtilHelper.toRoman(level)).withStyle(ChatFormatting.GRAY));
    }

    /**
     * @return Gets the ITextComponent that should be rendered in it's Information-Box on the ItemStack.
     */
    default List<Component> getRenderedText(ModifierInstance instance) {
        List<Component> textComponents = new ArrayList<>();
        if (instance == null) {
            return textComponents;
        }
        textComponents.add(getTextComponentName(instance.getLevel()));
        return textComponents;
    }
}