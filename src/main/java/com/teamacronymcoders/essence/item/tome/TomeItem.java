package com.teamacronymcoders.essence.item.tome;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.Item;

public class TomeItem extends Item {

  public TomeItem (Properties properties) {
    super(properties);
  }

  public TomeItem () {
    super(new Item.Properties().group(Essence.CORE_TAB).maxStackSize(1).maxDamage(0));
  }


}
