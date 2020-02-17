package com.teamacronymcoders.essence.impl.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.item.Item;

public class EssenceIngotItem extends BasicItem {

    public EssenceIngotItem() {
        super(new Item.Properties().group(EssenceReferences.CORE_TAB));
        setRegistryName(EssenceReferences.MODID, "essence_ingot");
    }


}
