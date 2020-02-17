package lanse505.essence.impl.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.item.Item;

public class EssenceNuggetItem extends BasicItem {

    public EssenceNuggetItem() {
        super(new Item.Properties().group(EssenceReferences.CORE_TAB));
        setRegistryName(EssenceReferences.MODID, "essence_nugget");
    }

}
