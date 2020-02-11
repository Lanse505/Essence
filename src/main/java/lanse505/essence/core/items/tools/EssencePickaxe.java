package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceItemTiers;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ResourceLocation;

public class EssencePickaxe extends PickaxeItem {
    public EssencePickaxe(ResourceLocation resourceLocation) {
        super(EssenceItemTiers.ESSENCE, 1, -2.8f, new Item.Properties().group(EssenceReferences.TOOL_TAB));
        setRegistryName(resourceLocation);
    }
}
