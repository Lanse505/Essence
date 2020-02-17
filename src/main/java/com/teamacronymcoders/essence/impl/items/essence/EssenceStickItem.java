package com.teamacronymcoders.essence.impl.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.util.ResourceLocation;

public class EssenceStickItem extends BasicItem {
    public EssenceStickItem() {
        super(new Properties().group(EssenceReferences.CORE_TAB));
        setRegistryName(new ResourceLocation(EssenceReferences.MODID, "essence_wood_sticks"));
    }
}
