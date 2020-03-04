package com.teamacronymcoders.essence.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.Item;

public class EssenceIngotItem extends BasicItem {

    public EssenceIngotItem() {
        super(new Item.Properties().group(Essence.CORE_TAB));
        setRegistryName(Essence.MODID, "essence_ingot");
    }


}
