package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ResourceLocation;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceShovel extends ShovelItem {
    public EssenceShovel(ResourceLocation resourceLocation) {
        super(ESSENCE, 1.5f, -3.0f, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }
}
