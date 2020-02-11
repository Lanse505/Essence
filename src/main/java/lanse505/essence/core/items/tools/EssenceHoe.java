package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static lanse505.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceHoe extends HoeItem {

    public EssenceHoe(ResourceLocation resourceLocation) {
        super(ESSENCE, -1.0F, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }

}
