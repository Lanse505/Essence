package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public abstract class ItemAttributeModifier extends ItemCoreModifier {

  private final List<Multimap<Attribute, AttributeModifier>> modifiers = new ArrayList<>();

  public ItemAttributeModifier () {
    this(1);
  }

  public ItemAttributeModifier (int maxLevel) {
    this(maxLevel, 0);
  }

  public ItemAttributeModifier (int maxLevel, int minLevel) {
    super(maxLevel, minLevel);
  }

  /**
   * This specific one is used for when you want and quick and dirty AttributeModifier modifier.
   */
  public ItemAttributeModifier (Attribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
    super(1);
    for (int i = 1; i <= 2; i++) {
      final Multimap<Attribute, AttributeModifier> levelModifiers = HashMultimap.create();
      levelModifiers.put(attribute, new AttributeModifier(uuid, identifier, amount * i, operation));
      this.modifiers.add(levelModifiers);
    }
  }

  public ItemAttributeModifier (Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
    super(maxLevel);
    for (int i = 1; i <= maxLevel; i++) {
      final Multimap<Attribute, AttributeModifier> levelModifiers = HashMultimap.create();
      levelModifiers.put(attribute, new AttributeModifier(uuid, identifier, amount * i, operation));
      this.modifiers.add(levelModifiers);
    }
  }

  public ItemAttributeModifier (Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, int minLevel, AttributeModifier.Operation operation) {
    super(maxLevel, minLevel);
    for (int i = 1; i <= maxLevel; i++) {
      final Multimap<Attribute, AttributeModifier> levelModifiers = HashMultimap.create();
      levelModifiers.put(attribute, new AttributeModifier(uuid, identifier, amount * i, operation));
      this.modifiers.add(levelModifiers);
    }
  }

  @Override
  public Multimap<Attribute, AttributeModifier> getAttributeModifiers (ItemStack stack, @Nullable LivingEntity wielder, ModifierInstance instance) {
    return this.modifiers.isEmpty() ? HashMultimap.create() : this.modifiers.get(Math.max(this.getLevelInRange(instance.getLevel(), stack) - 1, 0));
  }

  @Override
  public int getLevelInRange (int level, ItemStack object) {
    return 0;
  }
}
