package lanse505.essence.core.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class EssenceCrystal extends BasicItem {

    public EssenceCrystal() {
        super(new Item.Properties().group(EssenceReferences.CORE_TAB));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_crystal"));
    }
}
