package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class EssenceToolItem extends Item {
    public EssenceToolItem(ResourceLocation registryName, Item.Properties properties) {
        super(properties.group(EssenceReferences.TOOL_TAB).defaultMaxDamage(1048).setNoRepair());
        setRegistryName(registryName);
    }
}
