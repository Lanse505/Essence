package com.teamacronymcoders.essence.api.modifier.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.tool.ModifierInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoreModifier extends Modifier {
    private final List<Multimap<String, AttributeModifier>> modifiers = new ArrayList<>();
    private final int maxLevel;

    /**
     * This specific one is used for a blank CoreModifier, mostly useful for when you want a modifier that doesn't implement an AttributeModifier.
     * This is used by Child-classes so that child-classes don't need to implement AttributeModifier changes.
     */
    public CoreModifier(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * This specific one is used for when you want an quick and dirty AttributeModifier modifier.
     */
    public CoreModifier(IAttribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
        this.maxLevel = maxLevel;
        for (int i = 1; i <= maxLevel; i++) {
            final Multimap<String, AttributeModifier> levelModifiers = HashMultimap.create();
            levelModifiers.put(attribute.getName(), new AttributeModifier(uuid, identifier, amount * i, operation));
            this.modifiers.add(levelModifiers);
        }
    }

    /**
     * @return Gets the Max Level of the Modifier
     */
    @Override
    public int getMaxLevel(ItemStack stack) {
        return this.maxLevel;
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, @Nullable LivingEntity wielder, ModifierInstance instance) {
        return this.modifiers.isEmpty() ? HashMultimap.create() : this.modifiers.get(this.getLevelInRange(instance.getLevel(), stack) - 1);
    }
}
