package com.teamacronymcoders.essence.api.modifier.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public abstract class ItemCoreModifier extends Modifier<ItemStack> {

    private static final Multimap<String, AttributeModifier> EMPTY_ATTRIBUTE_MAP = HashMultimap.create();

    public ItemCoreModifier() {
        super(ItemStack.class);
    }

    public ItemCoreModifier(int maxLevel) {
        super(ItemStack.class, maxLevel);
    }

    public ItemCoreModifier(int maxLevel, int minLevel) {
        super(ItemStack.class, maxLevel, minLevel);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, LivingEntity wielder, ModifierInstance<ItemStack> instance) {
        return EMPTY_ATTRIBUTE_MAP;
    }

    public int getModifiedDurability(ItemStack stack, ModifierInstance<ItemStack> instance, int base) {
        return 0;
    }
    public float getModifiedEfficiency(ItemStack stack, ModifierInstance<ItemStack> instance, float base) {
        return 0;
    }
    public int getModifiedHarvestLevel(ItemStack stack, ModifierInstance<ItemStack> instance, int base) {
        return 0;
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return false;
    }

    @Override
    public boolean countsTowardsLimit(int level, ItemStack object) {
        return true;
    }

    @Override
    public int getModifierCountValue(int level, ItemStack object) {
        return 1;
    }
}
