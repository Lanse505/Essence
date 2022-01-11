package com.teamacronymcoders.essence.api.modifier.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.api.modifier.Modifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.common.item.tome.experience.TomeOfExperienceItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ItemCoreModifier extends Modifier<ItemStack> {

    private static final Multimap<Attribute, AttributeModifier> DEFAULT_IMMUTABLE_MAP = HashMultimap.create(0, 0);
    private final List<Multimap<Attribute, AttributeModifier>> MODIFIERS = new ArrayList<>();
    private boolean useCustomMap = false;

    public ItemCoreModifier() {
        this(0, 1);
    }

    public ItemCoreModifier(int minLevel) {
        this(minLevel, 1);
    }

    public ItemCoreModifier(int minLevel, int maxLevel) {
        super(minLevel, maxLevel);
        for (int i = 1; i <= maxLevel; i++) {
            final Multimap<Attribute, AttributeModifier> levelModifiers = HashMultimap.create(0, 0);
            MODIFIERS.add(levelModifiers);
        }
    }

    public ItemCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, AttributeModifier.Operation operation) {
        this(attribute, identifier, uuid, amount, 0, 1, operation);
    }

    public ItemCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, int maxLevel, AttributeModifier.Operation operation) {
        this(attribute, identifier, uuid, amount, 0, maxLevel, operation);
    }

    public ItemCoreModifier(Attribute attribute, String identifier, UUID uuid, double amount, int minLevel, int maxLevel, AttributeModifier.Operation operation) {
        super(minLevel, maxLevel);
        this.useCustomMap = true;
        for (int i = 1; i <= maxLevel; i++) {
            final Multimap<Attribute, AttributeModifier> levelModifiers = HashMultimap.create();
            levelModifiers.put(attribute, new AttributeModifier(uuid, identifier, amount * i, operation));
            MODIFIERS.add(levelModifiers);
        }
    }

    /**
     * Used to get the Attribute -> Modifiers list for the Modifier.
     *
     * @param stack    The currently held item.
     * @param wielder  The current wielder of the item.
     * @param instance The current instance object for this modifier.
     * @return Returns the AttributeModifier MultiMap.
     */
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, LivingEntity wielder, ModifierInstance instance) {
        return useCustomMap ? MODIFIERS.get(instance.getLevel()) : DEFAULT_IMMUTABLE_MAP;
    }

    /**
     * Gets the modified max-durability of the item.
     *
     * @param stack    The currently held item.
     * @param instance The current instance object for this modifier.
     * @param base     The base durability of the item item.
     * @return Returns the modified durability.
     */
    public int getModifiedDurability(ItemStack stack, ModifierInstance instance, int base) {
        return 0;
    }

    /**
     * Gets the modified destroy speed of the item.
     *
     * @param stack    The currently held item.
     * @param state    The currently mining BlockState.
     * @param original The original destroy speed.
     * @param instance The current instance object for this modifier.
     * @return Returns the potentially destroy speed value.
     */
    public float getDestroySpeed(ItemStack stack, BlockState state, float original, ModifierInstance instance) {
        return original;
    }

    /**
     * Used to have the modified have an associated Enchantment linked to it.
     *
     * @param stack The currently held item.
     * @return Returns the associated Enchantment.
     */
    @Nullable
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return null;
    }

    ;

    /**
     * Gets the modified fluid capacity for the item.
     * This method is used by some items such as:
     *
     * @param stack        The currently held item.
     * @param baseCapacity The base fluid capacity.
     * @param instance     The current instance object for this modifier.
     * @return Returns the potentially modified fluid capacity.
     * @see TomeOfExperienceItem
     */
    public int getModifiedFluidCapacity(ItemStack stack, int baseCapacity, ModifierInstance instance) {
        return baseCapacity;
    }
}