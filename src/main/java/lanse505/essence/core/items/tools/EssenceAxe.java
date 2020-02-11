package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceAxe extends AxeItem {
    public EssenceAxe(ResourceLocation resourceLocation) {
        super(ESSENCE, 6.0f, -3.1f, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }
}
