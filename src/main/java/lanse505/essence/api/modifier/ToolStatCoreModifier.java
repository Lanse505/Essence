package lanse505.essence.api.modifier;

import lanse505.essence.api.modifier.core.BaseModifier;
import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ToolStatCoreModifier extends BaseModifier {

    private final int maxLevel;

    public ToolStatCoreModifier(ResourceLocation id, int maxLevel) {
        super(id);
        this.maxLevel = maxLevel;
    }

    public ToolStatCoreModifier(String id, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), maxLevel);
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
    public float getModifiedEfficiency(ItemStack stack, int level, int base) {
        return 0;
    }

    /**
     * @param stack The stack being modified.
     * @param level The current level of the Modifier.
     * @param base The base value.
     * @return The amount to modify the attack-damage by.
     */
    public float getModifiedAttackDamage(ItemStack stack, int level, int base) {
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

}
