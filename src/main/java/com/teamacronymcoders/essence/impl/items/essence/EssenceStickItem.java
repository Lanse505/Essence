package com.teamacronymcoders.essence.impl.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.util.ResourceLocation;

public class EssenceStickItem extends BasicItem {
    public EssenceStickItem() {
        super(new Properties().group(Essence.CORE_TAB));
        setRegistryName(new ResourceLocation(Essence.MODID, "essence_wood_sticks"));
    }
}
