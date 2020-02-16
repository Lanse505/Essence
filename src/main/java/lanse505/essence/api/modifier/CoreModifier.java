package lanse505.essence.api.modifier;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lanse505.essence.api.modifier.core.BaseModifier;
import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoreModifier extends BaseModifier {

    private final List<Multimap<String, AttributeModifier>> modifiers = new ArrayList<>();
    private final int maxLevel;

    /**
     * This specific one is used for a blank CoreModifier, mostly useful for when you want a modifier that doesn't implement an AttributeModifier.
     * This is used by Child-classes so that child-classes don't need to implement AttributeModifier changes.
     */
    public CoreModifier(ResourceLocation id, int maxLevel) {
        super(id);
        this.maxLevel = maxLevel;
    }

    /**
     * This specific one is used for a blank CoreModifier, mostly useful for when you want a modifier that doesn't implement an AttributeModifier.
     * This is used by Child-classes so that child-classes don't need to implement AttributeModifier changes.
     * This specific one is used for when you want an quick and dirty attribute modifier.
     */
    public CoreModifier(String id, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), maxLevel);
    }

    /**
     * This specific one is used for when you want an quick and dirty AttributeModifier modifier.
     */
    public CoreModifier(ResourceLocation id, IAttribute attribute, double amount, AttributeModifier.Operation operation, String uuid, int maxLevel) {
        super(id);
        this.maxLevel = maxLevel;
        final ResourceLocation registryName = getRegistryName();
        for (int i  = 1; i <= maxLevel; i++) {
            final Multimap<String, AttributeModifier> levelModifiers = HashMultimap.create();
            levelModifiers.put(attribute.getName(), new AttributeModifier(UUID.fromString(uuid), registryName.getNamespace() + "_" + registryName.getPath(), amount * i, operation));
            this.modifiers.add(levelModifiers);
        }
    }

    /**
     * Returns a CoreModifier-Object, This grabs the currently active mod and inserts it as the domain.
     * This specific one is used for when you want an quick and dirty attribute modifier.
     */
    public CoreModifier(String id, IAttribute attribute, double amount, AttributeModifier.Operation operation, String uuid, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), attribute, amount, operation, uuid, maxLevel);
    }

    /**
     * @return Gets the Max Level of the Modifier
     */
    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * @param stack The stack being modified.
     * @param level The current level of the Modifier.
     * @param base The base value.
     * @return The amount to modify the durability by.
     */
    public int getModifiedDurability(ItemStack stack, int level, int base) {
        return 0;
    }

    /**
     * @param stack The stack being modified.
     * @param level The current level of the Modifier.
     * @param base The base value.
     * @return The amount to modify the efficiency by.
     */
    public float getModifiedEfficiency(ItemStack stack, int level, float base) {
        return 0;
    }

    /**
     * @param stack The stack being modified.
     * @param level The current level of the Modifier.
     * @param base The base value.
     * @return The amount to modify the durability by.
     */
    public int getModifiedHarvestLevel(ItemStack stack, int level, int base) {
        return 0;
    }

    /**
     * @param stack Essence-Tool ItemStack that provides the AttributeModifiers
     * @param wielder Wielder of the Essence-Tool
     * @param level Modifier-Level
     * @return Returns a Multimap with the AttributeModifiers that gets added to the tool.
     */
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, @Nullable LivingEntity wielder, int level) {
        return this.modifiers.get(this.getLevelInRange(level) - 1);
    }

}
