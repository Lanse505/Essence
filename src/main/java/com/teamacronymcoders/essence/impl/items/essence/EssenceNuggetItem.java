package com.teamacronymcoders.essence.impl.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.item.Item;

public class EssenceNuggetItem extends BasicItem {

    public EssenceNuggetItem() {
        super(new Item.Properties().group(EssenceReferences.CORE_TAB));
        setRegistryName(EssenceReferences.MODID, "essence_nugget");
    }

}
