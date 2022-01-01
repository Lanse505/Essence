package com.teamacronymcoders.essence.api.modifier.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public abstract class ItemCoreModifier extends Modifier {

    private static final Multimap<Attribute, AttributeModifier> EMPTY_ATTRIBUTE_MAP = HashMultimap.create();

    public ItemCoreModifier() {
        super();
    }

    public ItemCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    public ItemCoreModifier(int minLevel, int maxLevel) {
        super(minLevel, maxLevel);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, LivingEntity wielder, ModifierInstance instance) {
        return EMPTY_ATTRIBUTE_MAP;
    }

    public int getModifiedDurability(ItemStack stack, ModifierInstance instance, int base) {
        return 0;
    }

    public float getModifiedEfficiency(ItemStack stack, ModifierInstance instance, float base) {
        return 0;
    }

    public int getModifiedHarvestLevel(ItemStack stack, ModifierInstance instance, int base) {
        return 0;
    }

    @Override
    public boolean canApplyOnObject() {
        return false;
    }

    public boolean canApplyOnObject(ItemStack stack) {
        return canApplyOnObject();
    }

    @Override
    public boolean countsTowardsLimit(int level) {
        return true;
    }

    public boolean countsTowardsLimit(int level, ItemStack stack) {
        return countsTowardsLimit(level);
    }

    @Override
    public int getModifierCountValue(int level) {
        return 1;
    }

    public int getModifierCountValue(int level, ItemStack stack) {
        return getModifierCountValue(level);
    }

    public int getMinLevel(ItemStack stack) {
        return getMinLevel();
    }

    public int getMaxLevel(ItemStack stack) {
        return getMaxLevel();
    }
}
