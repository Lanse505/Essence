package lanse505.essence.core.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.util.ResourceLocation;

public class EssenceStickItem extends BasicItem {
    public EssenceStickItem() {
        super(new Properties().group(EssenceReferences.CORE_TAB));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_sticks"));
    }
}
