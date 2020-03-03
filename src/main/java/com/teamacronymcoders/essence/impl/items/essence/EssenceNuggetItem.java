package com.teamacronymcoders.essence.impl.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.Item;

public class EssenceNuggetItem extends BasicItem {

    public EssenceNuggetItem() {
        super(new Item.Properties().group(Essence.CORE_TAB));
        setRegistryName(Essence.MODID, "essence_nugget");
    }

}
