package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class EssenceSword extends SwordItem {

    public EssenceSword(ResourceLocation resourceLocation) {
        super(EssenceItemTiers.ESSENCE, 3, -2.4F, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }

}
