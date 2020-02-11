package lanse505.essence.core.items.tools;

import lanse505.essence.api.modifier.ToolStatCoreModifier;
import lanse505.essence.api.modifier.core.Modifier;
import lanse505.essence.utils.EssenceHelpers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceAxe extends AxeItem {
    public EssenceAxe(ResourceLocation resourceLocation) {
        super(ESSENCE, 6.0f, -3.1f, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        int modifiedValue = 0;
        for (final Map.Entry<Modifier, Integer> modifierInfo : EssenceHelpers.getModifiers(stack).entrySet()) {
            if (modifierInfo.getKey() instanceof ToolStatCoreModifier) {
                ToolStatCoreModifier statModifier = (ToolStatCoreModifier) modifierInfo.getKey();
                modifiedValue += statModifier.getModifiedDurability(stack, modifierInfo.getValue(), ESSENCE.getMaxUses());
            }
        }
        return modifiedValue;
    }


}
