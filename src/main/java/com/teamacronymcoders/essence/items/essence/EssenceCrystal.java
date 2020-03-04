package com.teamacronymcoders.essence.items.essence;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class EssenceCrystal extends BasicItem {

    public EssenceCrystal() {
        super(new Item.Properties().group(Essence.CORE_TAB));
        setRegistryName(new ResourceLocation(Essence.MODID, "essence_crystal"));
    }
}
