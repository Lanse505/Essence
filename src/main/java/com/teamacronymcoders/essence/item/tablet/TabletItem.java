package com.teamacronymcoders.essence.item.tablet;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.Item;

public class TabletItem extends Item {

    public TabletItem() {
        super(new Item.Properties().group(Essence.CORE_TAB).maxStackSize(1).maxDamage(0));
    }


}
